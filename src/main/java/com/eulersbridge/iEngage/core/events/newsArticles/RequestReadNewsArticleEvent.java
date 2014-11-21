package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

public class RequestReadNewsArticleEvent extends RequestReadEvent
{
	public RequestReadNewsArticleEvent(Long id) 
	{
	    super(id);
	}
}
