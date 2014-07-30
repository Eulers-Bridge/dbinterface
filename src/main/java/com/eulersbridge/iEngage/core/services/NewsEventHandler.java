package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.repository.NewsArticleRepository;

public class NewsEventHandler implements NewsService 
{
	private NewsArticleRepository newsRepo;

	public NewsEventHandler(NewsArticleRepository newsRepo) 
	{
		this.newsRepo=newsRepo;
	}

	@Override
	public NewsArticleCreatedEvent createNewsArticle(
			CreateNewsArticleEvent createNewsArticleEvent) 
	{
		NewsArticleDetails nADs = createNewsArticleEvent.getNewsArticleDetails();
		NewsArticle na=NewsArticle.fromNewsArticleDetails(nADs);
		NewsArticle result=newsRepo.save(na);
		NewsArticleCreatedEvent nACE=new NewsArticleCreatedEvent(result.getNodeId(), result.toNewsArticleDetails());
		return nACE;
	}

	@Override
	public ReadNewsArticleEvent requestReadNewsArticle(
			RequestReadNewsArticleEvent requestReadNewsArticleEvent) 
	{
		NewsArticle na=newsRepo.findOne(requestReadNewsArticleEvent.getNewsArticleId());
		ReadNewsArticleEvent nade=new ReadNewsArticleEvent(requestReadNewsArticleEvent.getNewsArticleId(), na.toNewsArticleDetails());
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

}
