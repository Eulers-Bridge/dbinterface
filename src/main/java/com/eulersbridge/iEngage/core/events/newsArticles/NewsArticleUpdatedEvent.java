package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class NewsArticleUpdatedEvent extends UpdatedEvent 
{
	public NewsArticleUpdatedEvent(Long id, NewsArticleDetails newsArticleDetails) 
	{
	    super(id,newsArticleDetails);
	}

	public NewsArticleUpdatedEvent(Long id)
	{
	    super(id);
	}
}
