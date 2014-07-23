package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class NewsArticleDeletedEvent extends DeletedEvent
{
	private Long id;
	private NewsArticleDetails newsArticleDetails;
	private boolean deletionCompleted;

	private NewsArticleDeletedEvent(Long id) 
	{
		this.id = id;
	}

	public NewsArticleDeletedEvent(Long id, NewsArticleDetails newsArticleDetails) 
	{
	    this.id = id;
	    this.newsArticleDetails = newsArticleDetails;
	    this.deletionCompleted = true;
	  }

	  public Long getNewsArticleId() 
	  {
	    return id;
	  }

	  public NewsArticleDetails getNewsArticleDetails() 
	  {
	    return newsArticleDetails;
	  }

	  public boolean isDeletionCompleted() 
	  {
	    return deletionCompleted;
	  }

	  public static NewsArticleDeletedEvent deletionForbidden(Long id, NewsArticleDetails newsArticleDetails) 
	  {
		  NewsArticleDeletedEvent ev = new NewsArticleDeletedEvent(id, newsArticleDetails);
		  ev.entityFound=true;
		  ev.deletionCompleted=false;
		  return ev;
	  }

	  public static NewsArticleDeletedEvent notFound(Long id) 
	  {
		  NewsArticleDeletedEvent ev = new NewsArticleDeletedEvent(id);
		  ev.entityFound=false;
		  return ev;
	  }
}
