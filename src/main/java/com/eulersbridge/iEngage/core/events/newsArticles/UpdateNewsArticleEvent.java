package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

public class UpdateNewsArticleEvent extends UpdateEvent
{
	 public UpdateNewsArticleEvent(Long id, NewsArticleDetails newsArticleDetails) 
	  {
	    super(id,newsArticleDetails);
	  }
}
