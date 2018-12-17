package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.newsArticles.*;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.NewsService;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleRepository;
import com.eulersbridge.iEngage.database.repository.NodeRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class NewsEventHandler implements NewsService {
  private static Logger LOG = LoggerFactory.getLogger(NewsEventHandler.class);

  private NodeRepository nodeRepository;
  private UserRepository userRepository;
  private NewsArticleRepository newsRepo;
  private InstitutionRepository instRepo;

  @Autowired
  public NewsEventHandler(NewsArticleRepository newsRepo, UserRepository userRepository, InstitutionRepository instRepo, NodeRepository nodeRepository) {
    this.newsRepo = newsRepo;
    this.userRepository = userRepository;
    this.instRepo = instRepo;
    this.nodeRepository= nodeRepository;
  }

  @Override
  public NewsArticleCreatedEvent createNewsArticle(
    CreateNewsArticleEvent createNewsArticleEvent) {
    NewsArticleDetails nADs = (NewsArticleDetails) createNewsArticleEvent.getDetails();
    NewsArticle na = NewsArticle.fromNewsArticleDetails(nADs);

    if (LOG.isDebugEnabled())
      LOG.debug("Finding institution with id = " + nADs.getInstitutionId());
    NewsFeed nf = instRepo.findNewsFeedByInstitutionId(nADs.getInstitutionId());
    if (LOG.isDebugEnabled()) LOG.debug("news feed - " + nf);
    if (LOG.isDebugEnabled())
      LOG.debug("Finding user with email = " + nADs.getCreatorEmail());
    User creator = userRepository.findByEmail(nADs.getCreatorEmail(), 0);
    if (LOG.isDebugEnabled()) LOG.debug("User Details :" + creator);
    NewsArticleCreatedEvent nACE;
    if ((creator != null) && (nf != null)) {
//      na.setCreator(creator.toNode());
//      na.setNewsFeed(nf.toNode());
      NewsArticle result = newsRepo.save(na, 0);
      nodeRepository.createdBy(result.getNodeId(), creator.getNodeId());
      nodeRepository.hasNews(nf.getNodeId(), result.getNodeId());

      result = newsRepo.findById(result.getNodeId()).orElse(null);
      nACE = new NewsArticleCreatedEvent(result.getNodeId(), result.toNewsArticleDetails());
    } else {
      if (null == creator) {
        nACE = NewsArticleCreatedEvent.creatorNotFound();
      } else {
        nACE = NewsArticleCreatedEvent.institutionNotFound();
      }
    }
    return nACE;
  }

  @Override
  public RequestHandledEvent requestReadNewsArticle(Long articleId, String userEmail) {
    NewsArticle article = newsRepo.readOne(articleId, userEmail);
    if (article == null)
      return RequestHandledEvent.targetNotFound();

    return new RequestHandledEvent(com.eulersbridge.iEngage.rest.domain.NewsArticle.fromNewsArticleDetails(article.toNewsArticleDetails()));
  }

  @Override
  public UpdatedEvent updateNewsArticle(
    UpdateNewsArticleEvent updateNewsArticleEvent) {
    NewsArticleDetails nADs = (NewsArticleDetails) updateNewsArticleEvent.getDetails();
    NewsArticle na = NewsArticle.fromNewsArticleDetails(nADs);
    NewsArticle result = newsRepo.save(na, 0);
    NewsArticleUpdatedEvent nACE = new NewsArticleUpdatedEvent(result.getNodeId(), result.toNewsArticleDetails());
    return nACE;
  }

  @Override
  public DeletedEvent deleteNewsArticle(DeleteNewsArticleEvent deleteNewsArticleEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deleteNewsArticle newsarticleEvent = " + deleteNewsArticleEvent);
    DeletedEvent nade;
    Long newsArticleId = deleteNewsArticleEvent.getNodeId();
    if (LOG.isDebugEnabled())
      LOG.debug("deleteNewsArticle(" + newsArticleId + ")");
    if (newsRepo.existsById(newsArticleId)) {
      newsRepo.deleteById(newsArticleId);
      nade = new NewsArticleDeletedEvent(newsArticleId);
    } else {
      nade = NewsArticleDeletedEvent.notFound(newsArticleId);
    }
    return nade;
  }

  @Override
  public NewsArticlesReadEvent readNewsArticles(
    ReadAllEvent readNewsArticlesEvent, Direction sortDirection, int pageNumber, int pageSize) {
    Long institutionId = readNewsArticlesEvent.getParentId();
    Page<NewsArticle> articles = null;
    ArrayList<NewsArticleDetails> dets = new ArrayList<>();
    NewsArticlesReadEvent nare = null;

    if (LOG.isDebugEnabled()) LOG.debug("InstitutionId " + institutionId);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection, "a.date");
    articles = newsRepo.findByInstitutionId(institutionId, pageable);
//    articles = pages.map(newsArticleData -> newsArticleData.getNewsArticle());
    if (articles != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + articles.getTotalElements() + " total pages =" + articles.getTotalPages());
      Iterator<NewsArticle> iter = articles.iterator();
      while (iter.hasNext()) {
        NewsArticle na = iter.next();
        // FIXME: Should read institution ID from db instead from request.
        NewsFeed newsFeed = new NewsFeed();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getTitle());
        NewsArticleDetails det = na.toNewsArticleDetails();
        det.setInstitutionId(readNewsArticlesEvent.getParentId());
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found instId.
        Institution inst = instRepo.findById(institutionId).get();
        if ((null == inst) ||
          ((null == inst.getName()) || ((null == inst.getCampus()) && (null == inst.getState()) && (null == inst.getCountry())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(InstitutionId)");
          nare = NewsArticlesReadEvent.institutionNotFound();
        } else {
          nare = new NewsArticlesReadEvent(institutionId, dets, articles.getTotalElements(), articles.getTotalPages());
        }
      } else {
        nare = new NewsArticlesReadEvent(institutionId, dets, articles.getTotalElements(), articles.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = NewsArticlesReadEvent.institutionNotFound();
    }
    return nare;
  }

  //TODO Remove these and use generic.
  @Override
  public NewsArticleLikesEvent likesNewsArticle(LikesNewsArticleEvent likesNewsArticleEvent, Direction sortDirection, int pageNumber, int pageSize) {
    Long articleId = likesNewsArticleEvent.getNewsArticleId();
    ArrayList<UserDetails> userDetailses = new ArrayList<UserDetails>();
    NewsArticleLikesEvent newsArticleLikesEvent = new NewsArticleLikesEvent();

    if (LOG.isDebugEnabled()) LOG.debug("articleId " + articleId);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection, "a.date");
    Page<User> users = userRepository.findByArticleId(articleId, pageable);
    if (LOG.isDebugEnabled())
      LOG.debug("Total elements = " + users.getTotalElements() + " total pages =" + users.getTotalPages());

    if (users != null) {
      Iterator<User> iter = users.iterator();
      while (iter.hasNext()) {
        User user = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + user.getEmail());
        UserDetails userDetails = user.toUserDetails();
        userDetailses.add(userDetails);
      }
      if (0 == userDetailses.size()) {
        // Need to check if we actually found article.
        NewsArticle newsArticle = newsRepo.findById(articleId).get();
        if ((null == newsArticle) ||
          ((null == newsArticle.getTitle()) || ((null == newsArticle.getContent()) && (null == newsArticle.getCreator()) && (null == newsArticle.getNewsFeed())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by newsRepo.findOne(articleId)");
          newsArticleLikesEvent = NewsArticleLikesEvent.articleNotFound(articleId);
        } else {
          newsArticleLikesEvent = new NewsArticleLikesEvent(articleId, userDetailses);
        }
      } else {
        newsArticleLikesEvent = new NewsArticleLikesEvent(articleId, userDetailses);
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByArticleId");
      newsArticleLikesEvent = NewsArticleLikesEvent.articleNotFound(articleId);
    }
    return newsArticleLikesEvent;
  }
}
