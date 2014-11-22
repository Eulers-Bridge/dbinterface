package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

public class DeleteNewsArticleEvent extends DeleteEvent
{
	public DeleteNewsArticleEvent(final Long id) 
	{
		super(id);
	}
}
