package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

public class UpdateNewsArticleEvent extends UpdateEvent
{
	 private Long id;
	 private NewsArticleDetails newsArticleDetails;

	 public UpdateNewsArticleEvent(Long id, NewsArticleDetails newsArticleDetails) 
	  {
	    this.id = id;
	    this.newsArticleDetails = newsArticleDetails;
	    this.newsArticleDetails.setNewsArticleId(id);
	  }

	  public Long getNewsArticleId() 
	  {
	    return id;
	  }

	  public NewsArticleDetails getUNewsArticleDetails() 
	  {
	    return newsArticleDetails;
	  }
}
