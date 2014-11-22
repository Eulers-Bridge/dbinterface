package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class ReadNewsArticleEvent extends ReadEvent 
{
	public ReadNewsArticleEvent(Long id) 
	{
		super(id);
	}

	  public ReadNewsArticleEvent(Long id, NewsArticleDetails readNewsArticleDetails) 
	  {
		  super(id,readNewsArticleDetails);
	  }
}
