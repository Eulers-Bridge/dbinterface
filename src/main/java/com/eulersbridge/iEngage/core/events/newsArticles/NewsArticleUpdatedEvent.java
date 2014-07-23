package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class NewsArticleUpdatedEvent extends UpdatedEvent 
{
	private Long id;
	private NewsArticleDetails newsArticleDetails;

	public NewsArticleUpdatedEvent(Long id, NewsArticleDetails newsArticleDetails) 
	{
	    this.id = id;
	    this.newsArticleDetails = newsArticleDetails;
	}

	public NewsArticleUpdatedEvent(Long id)
	{
	    this.id = id;
	}

	public Long getNewsArticleId()
	{
	    return id;
	}

	public NewsArticleDetails getNewsArticleDetails()
	{
	    return newsArticleDetails;
	}
}
