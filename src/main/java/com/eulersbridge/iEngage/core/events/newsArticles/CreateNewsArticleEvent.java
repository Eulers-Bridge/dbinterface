package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.CreateEvent;

public class CreateNewsArticleEvent extends CreateEvent 
{
	public CreateNewsArticleEvent(NewsArticleDetails newsArticleDetails) 
	{
		super(newsArticleDetails);
	}
}
