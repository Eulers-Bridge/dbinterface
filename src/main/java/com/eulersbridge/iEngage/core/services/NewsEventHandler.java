package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.LikeNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleLikedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;
import com.eulersbridge.iEngage.database.domain.Like;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.StudentYear;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleRepository;
import com.eulersbridge.iEngage.database.repository.StudentYearRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

public class NewsEventHandler implements NewsService 
{
    private static Logger LOG = LoggerFactory.getLogger(NewsEventHandler.class);

    private UserRepository userRepository;
	private NewsArticleRepository newsRepo;
	private InstitutionRepository instRepo;
    private StudentYearRepository syRepository;

	public NewsEventHandler(NewsArticleRepository newsRepo,UserRepository userRepository, InstitutionRepository instRepo, StudentYearRepository syRepo) 
	{
		this.newsRepo=newsRepo;
		this.userRepository=userRepository;
		this.instRepo=instRepo;
		this.syRepository=syRepo;
	}

	@Override
	public NewsArticleCreatedEvent createNewsArticle(
			CreateNewsArticleEvent createNewsArticleEvent) 
	{
		NewsArticleDetails nADs = createNewsArticleEvent.getNewsArticleDetails();
		NewsArticle na=NewsArticle.fromNewsArticleDetails(nADs);
		
		if (LOG.isDebugEnabled()) LOG.debug("Finding institution with id = "+nADs.getInstitutionId());
		StudentYear sy=instRepo.findLatestStudentYear(nADs.getInstitutionId());
		if (LOG.isDebugEnabled()) LOG.debug("inst - "+sy);
		if (LOG.isDebugEnabled()) LOG.debug("Finding user with email = "+nADs.getCreatorEmail());
    	User creator=userRepository.findByEmail(nADs.getCreatorEmail());
    	if (LOG.isDebugEnabled()) LOG.debug("User Details :"+creator);
    	NewsArticleCreatedEvent nACE;
    	if ((creator!=null)&&(sy!=null))
    	{
    		na.setCreator(creator);
    		na.setStudentYear(sy);
			NewsArticle result=newsRepo.save(na);
			nACE=new NewsArticleCreatedEvent(result.getNodeId(), result.toNewsArticleDetails());
    	}
    	else
    	{
    		if (null==creator)
    		{
    			nACE=NewsArticleCreatedEvent.creatorNotFound(na.getNodeId());
    		}
    		else
    		{
    			nACE=NewsArticleCreatedEvent.institutionNotFound(na.getNodeId());
    		}
    	}
		return nACE;
	}

	@Override
	public ReadNewsArticleEvent requestReadNewsArticle(
			RequestReadNewsArticleEvent requestReadNewsArticleEvent) 
	{
		NewsArticle na=newsRepo.findOne(requestReadNewsArticleEvent.getNewsArticleId());
		ReadNewsArticleEvent nade;
		if (na!=null)
			nade=new ReadNewsArticleEvent(requestReadNewsArticleEvent.getNewsArticleId(), na.toNewsArticleDetails());
		else
			nade=ReadNewsArticleEvent.notFound(requestReadNewsArticleEvent.getNewsArticleId());
		return nade;
	}

	@Override
	public NewsArticleUpdatedEvent updateNewsArticle(
			UpdateNewsArticleEvent updateNewsArticleEvent) 
	{
		NewsArticleDetails nADs = updateNewsArticleEvent.getUNewsArticleDetails();
		NewsArticle na=NewsArticle.fromNewsArticleDetails(nADs);
		NewsArticle result=newsRepo.save(na);
		NewsArticleUpdatedEvent nACE=new NewsArticleUpdatedEvent(result.getNodeId(), result.toNewsArticleDetails());
		return nACE;
	}

	@Override
	public NewsArticleDeletedEvent deleteNewsArticle(
			DeleteNewsArticleEvent deleteNewsArticleEvent) 
	{
		newsRepo.delete(deleteNewsArticleEvent.getNewsArticleId());
		NewsArticleDeletedEvent nade=new NewsArticleDeletedEvent(deleteNewsArticleEvent.getNewsArticleId(), null);
		return nade;
	}

	@Override
	public NewsArticlesReadEvent readNewsArticles(
			ReadNewsArticlesEvent readNewsArticlesEvent, Direction sortDirection,int pageNumber, int pageSize) 
	{
		Long institutionId=readNewsArticlesEvent.getInstId();
		Long syId=readNewsArticlesEvent.getSyId();
		StudentYear sy;
		Page <NewsArticle>articles=null;
		ArrayList<NewsArticleDetails> dets=new ArrayList<NewsArticleDetails>();
		NewsArticlesReadEvent nare=null;
		if (null==syId)
		{
			if (LOG.isDebugEnabled()) LOG.debug("InstitutionId "+institutionId);
			sy=instRepo.findLatestStudentYear(institutionId);
			syId=sy.getNodeId();
		}
		if (syId!=null)
		{
			if (LOG.isDebugEnabled()) LOG.debug("Student Year Id "+syId);
			Pageable pageable=new PageRequest(pageNumber,pageSize,sortDirection,"a.date");
			articles=newsRepo.findByStudentYearId(syId,pageable);
			if (LOG.isDebugEnabled())
					LOG.debug("Total elements = "+articles.getTotalElements()+" total pages ="+articles.getTotalPages());
			if (articles!=null)
			{
				Iterator<NewsArticle> iter=articles.iterator();
				while (iter.hasNext())
				{
					NewsArticle na=iter.next();
					if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getTitle());
					NewsArticleDetails det=na.toNewsArticleDetails();
					dets.add(det);
				}
				nare=new NewsArticlesReadEvent(institutionId,syId,dets);
			}
			else
			{
				if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByStudentYear");
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findStudentYear");
			nare=NewsArticlesReadEvent.studentYearNotFound();
		}
		return nare;
	}

	@Override
	public NewsArticleLikedEvent likeNewsArticle(
			LikeNewsArticleEvent likeNewsArticleEvent) 
	{
		boolean result;
		NewsArticleLikedEvent retValue;
		String email=likeNewsArticleEvent.getEmailAddress();
		Long newsArticleId=likeNewsArticleEvent.getNewsArticleId();
		User user=userRepository.findByEmail(email);
		if (null==user)
		{
			return NewsArticleLikedEvent.userNotFound(email);
		}
			
		NewsArticle article=newsRepo.findOne(newsArticleId);
		if (null==article)
		{
			return NewsArticleLikedEvent.articleNotFound(newsArticleId, email);
		}
		Like like=new Like(user,article);
		boolean articleResult=article.addLike(like);
		if ((!articleResult)&&(LOG.isWarnEnabled())) LOG.warn("Unable to add like to article, already exists.");
		boolean userResult=user.addLike(like);
		if ((!userResult)&&(LOG.isWarnEnabled())) LOG.warn("Unable to add like to user, already exists.");
		userRepository.save(user);
		newsRepo.save(article);
		result=articleResult&&userResult;
		retValue=new NewsArticleLikedEvent(newsArticleId,email,result);
		return retValue;
	}
}
