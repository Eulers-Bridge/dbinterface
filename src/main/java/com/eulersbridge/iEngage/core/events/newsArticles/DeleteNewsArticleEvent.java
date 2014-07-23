package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

public class DeleteNewsArticleEvent extends DeleteEvent
{
	private final Long id;

	public DeleteNewsArticleEvent(final Long id) 
	{
		this.id = id;
	}

	public Long getNewsArticleId() 
	{
		return this.id;
	}
}
