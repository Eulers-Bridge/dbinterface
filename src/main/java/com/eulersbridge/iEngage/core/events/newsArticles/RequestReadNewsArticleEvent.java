package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

public class RequestReadNewsArticleEvent extends RequestReadEvent
{
	private Long id;

	public RequestReadNewsArticleEvent(Long id) 
	{
	    this.id = id;
	}

	public Long getNewsArticleId() 
	{
	    return id;
	}
}
