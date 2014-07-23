package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class NewsArticleCreatedEvent extends CreatedEvent
{
	private NewsArticleDetails newsArticleDetails;
	private Long id;
	
	public NewsArticleCreatedEvent(Long id, NewsArticleDetails newsArticleDetails) 
	{
		this.newsArticleDetails=newsArticleDetails;
		this.id=id;
	}

	public NewsArticleCreatedEvent(Long id) 
	{
		this.id=id;
	}

	public Long getNewsArticleId() 
	{
		return id;
	}

	public void setKey(Long id)
	{
		this.id = id;
	}

	public void setNewsArticleDetails(NewsArticleDetails newsArticleDetails) {
		this.newsArticleDetails = newsArticleDetails;
	}

	public NewsArticleDetails getNewsArticleDetails() 
	{
	    return newsArticleDetails;
	}
}
