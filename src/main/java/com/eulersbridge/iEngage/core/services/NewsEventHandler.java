package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.conversion.Result;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;
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
			ReadNewsArticlesEvent readNewsArticlesEvent) 
	{
		Long institutionId=readNewsArticlesEvent.getInstId();
		StudentYear sy;
		Iterable <NewsArticle>articles=null;
		ArrayList<NewsArticleDetails> dets=new ArrayList<NewsArticleDetails>();
		NewsArticlesReadEvent nare=null;
		if (null==readNewsArticlesEvent.getSyId())
		{
			sy=instRepo.findLatestStudentYear(institutionId);
		}
		else
		{
			sy=syRepository.findOne(readNewsArticlesEvent.getSyId());
		}
		if (sy!=null)
		{
			articles=newsRepo.findByStudentYear(sy);
			if (articles!=null)
			{
				Iterator<NewsArticle> iter=articles.iterator();
				while (iter.hasNext())
				{
					NewsArticle na=iter.next();
					NewsArticleDetails det=na.toNewsArticleDetails();
					dets.add(det);
				}
				nare=new NewsArticlesReadEvent(institutionId,sy.getNodeId(),dets);
			}
		}
		return nare;
	}

}
