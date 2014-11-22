package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class NewsArticleDeletedEvent extends DeletedEvent
{
	public NewsArticleDeletedEvent(Long id)
	{
		super(id);
	}
}
