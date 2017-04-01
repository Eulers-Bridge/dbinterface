package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.*;
import com.eulersbridge.iEngage.core.events.users.UserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

public class NewsEventHandler implements NewsService 
{
    private static Logger LOG = LoggerFactory.getLogger(NewsEventHandler.class);

    private UserRepository userRepository;
	private NewsArticleRepository newsRepo;
	private InstitutionRepository instRepo;

	public NewsEventHandler(NewsArticleRepository newsRepo,UserRepository userRepository, InstitutionRepository instRepo) 
	{
		this.newsRepo=newsRepo;
		this.userRepository=userRepository;
		this.instRepo=instRepo;
	}

	@Override
	public NewsArticleCreatedEvent createNewsArticle(
			CreateNewsArticleEvent createNewsArticleEvent) 
	{
		NewsArticleDetails nADs = (NewsArticleDetails) createNewsArticleEvent.getDetails();
		NewsArticle na=NewsArticle.fromNewsArticleDetails(nADs);
		
		if (LOG.isDebugEnabled()) LOG.debug("Finding institution with id = "+nADs.getInstitutionId());
		NewsFeed nf=instRepo.findNewsFeed(nADs.getInstitutionId());
		if (LOG.isDebugEnabled()) LOG.debug("news feed - "+nf);
		if (LOG.isDebugEnabled()) LOG.debug("Finding user with email = "+nADs.getCreatorEmail());
    	User creator=userRepository.findByEmail(nADs.getCreatorEmail());
    	if (LOG.isDebugEnabled()) LOG.debug("User Details :"+creator);
    	NewsArticleCreatedEvent nACE;
    	if ((creator!=null)&&(nf!=null))
    	{
    		na.setCreator(creator);
    		na.setNewsFeed(nf);
			NewsArticle result=newsRepo.save(na);
			nACE=new NewsArticleCreatedEvent(result.getNodeId(), result.toNewsArticleDetails());
    	}
    	else
    	{
    		if (null==creator)
    		{
    			nACE=NewsArticleCreatedEvent.creatorNotFound();
    		}
    		else
    		{
    			nACE=NewsArticleCreatedEvent.institutionNotFound();
    		}
    	}
		return nACE;
	}

	@Override
	public ReadEvent requestReadNewsArticle(
			RequestReadNewsArticleEvent requestReadNewsArticleEvent) 
	{
		NewsArticle na=newsRepo.findOne(requestReadNewsArticleEvent.getNodeId());
		ReadEvent nade;
		if (na!=null)
			nade=new ReadNewsArticleEvent(requestReadNewsArticleEvent.getNodeId(), na.toNewsArticleDetails());
		else
			nade=ReadNewsArticleEvent.notFound(requestReadNewsArticleEvent.getNodeId());
		return nade;
	}

	@Override
	public UpdatedEvent updateNewsArticle(
			UpdateNewsArticleEvent updateNewsArticleEvent) 
	{
		NewsArticleDetails nADs = (NewsArticleDetails) updateNewsArticleEvent.getDetails();
		NewsArticle na=NewsArticle.fromNewsArticleDetails(nADs);
		if (LOG.isDebugEnabled()) LOG.debug("Finding institution with id = "+nADs.getInstitutionId());
		NewsFeed nf=instRepo.findNewsFeed(nADs.getInstitutionId());
		na.setNewsFeed(nf);
		if (LOG.isDebugEnabled()) LOG.debug("news feed - "+nf);
		if (LOG.isDebugEnabled()) LOG.debug("Finding user with email = "+nADs.getCreatorEmail());
    	User creator=userRepository.findByEmail(nADs.getCreatorEmail());
    	na.setCreator(creator);
    	if (LOG.isDebugEnabled()) LOG.debug("User Details :"+creator);
		NewsArticle result=newsRepo.save(na);
		NewsArticleUpdatedEvent nACE=new NewsArticleUpdatedEvent(result.getNodeId(), result.toNewsArticleDetails());
		return nACE;
	}

	@Override
	public DeletedEvent deleteNewsArticle(DeleteNewsArticleEvent deleteNewsArticleEvent) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Entered deleteNewsArticle newsarticleEvent = "+deleteNewsArticleEvent);
		DeletedEvent nade;
		Long newsArticleId=deleteNewsArticleEvent.getNodeId();
	    if (LOG.isDebugEnabled()) LOG.debug("deleteNewsArticle("+newsArticleId+")");
	    if (newsRepo.exists(newsArticleId))
	    {
	    	newsRepo.delete(newsArticleId);
			nade=new NewsArticleDeletedEvent(newsArticleId);
	    }
	    else
	    {
			nade=NewsArticleDeletedEvent.notFound(newsArticleId);
	    }
		return nade;
	}

	@Override
	public NewsArticlesReadEvent readNewsArticles(
			ReadAllEvent readNewsArticlesEvent, Direction sortDirection,int pageNumber, int pageSize) 
	{
		Long institutionId=readNewsArticlesEvent.getParentId();
		Page <NewsArticle>articles=null;
		ArrayList<NewsArticleDetails> dets=new ArrayList<NewsArticleDetails>();
		NewsArticlesReadEvent nare=null;

		if (LOG.isDebugEnabled()) LOG.debug("InstitutionId "+institutionId);
		Pageable pageable=new PageRequest(pageNumber,pageSize,sortDirection,"a.date");
		System.out.println("start findByInstitutionId()");
		articles=newsRepo.findByInstitutionId(institutionId, pageable);
		System.out.println("end findByInstitutionId");
		if (articles!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+articles.getTotalElements()+" total pages ="+articles.getTotalPages());
			Iterator<NewsArticle> iter=articles.iterator();
			while (iter.hasNext())
			{
				NewsArticle na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getTitle());
				NewsArticleDetails det=na.toNewsArticleDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found instId.
				Institution inst=instRepo.findOne(institutionId);
				if ( (null==inst) ||
					 ((null==inst.getName()) || ((null==inst.getCampus()) && (null==inst.getState()) && (null==inst.getCountry()))))
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(InstitutionId)");
					nare=NewsArticlesReadEvent.institutionNotFound();
				}
				else
				{	
					nare=new NewsArticlesReadEvent(institutionId,dets,articles.getTotalElements(),articles.getTotalPages());
				}
			}
			else
			{	
				nare=new NewsArticlesReadEvent(institutionId,dets,articles.getTotalElements(),articles.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByInstitutionId");
			nare=NewsArticlesReadEvent.institutionNotFound();
		}
		return nare;
	}
//TODO Remove these and use generic.
    @Override
    public NewsArticleLikesEvent likesNewsArticle(LikesNewsArticleEvent likesNewsArticleEvent, Direction sortDirection, int pageNumber, int pageSize)
    {
        Long articleId = likesNewsArticleEvent.getNewsArticleId();
        ArrayList<UserDetails> userDetailses = new ArrayList<UserDetails>();
        NewsArticleLikesEvent newsArticleLikesEvent = new NewsArticleLikesEvent();

        if (LOG.isDebugEnabled()) LOG.debug("articleId "+articleId);
        Pageable pageable = new PageRequest(pageNumber,pageSize,sortDirection,"a.date");
        Page<User> users = userRepository.findByArticleId(articleId, pageable);
        if (LOG.isDebugEnabled())
            LOG.debug("Total elements = "+users.getTotalElements()+" total pages ="+users.getTotalPages());

        if (users != null)
        {
            Iterator<User> iter = users.iterator();
            while (iter.hasNext())
            {
                User user =iter.next();
                if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+user.getEmail());
                UserDetails userDetails = user.toUserDetails();
                userDetailses.add(userDetails);
            }
            if (0==userDetailses.size())
            {
                // Need to check if we actually found article.
                NewsArticle newsArticle=newsRepo.findOne(articleId);
                if ( (null==newsArticle) ||
                        ((null==newsArticle.getTitle()) || ((null==newsArticle.getContent()) && (null==newsArticle.getCreator()) && (null==newsArticle.getNewsFeed()))))
                {
                    if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by newsRepo.findOne(articleId)");
                    newsArticleLikesEvent = NewsArticleLikesEvent.articleNotFound(articleId);
                }
                else
                {
                    newsArticleLikesEvent = new NewsArticleLikesEvent(articleId, userDetailses);
                }
            }
            else
            {
                newsArticleLikesEvent=new NewsArticleLikesEvent(articleId,userDetailses);
            }
        }
        else
        {
            if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByArticleId");
            newsArticleLikesEvent=NewsArticleLikesEvent.articleNotFound(articleId);
        }
        return newsArticleLikesEvent;
    }
}
