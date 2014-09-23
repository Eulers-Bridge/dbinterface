package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class NewsArticleDeletedEvent extends DeletedEvent
{
	private Long id;
	private boolean deletionCompleted=true;

	public NewsArticleDeletedEvent(Long id) 
	{
		this.id = id;
	}

	  public Long getNewsArticleId() 
	  {
	    return id;
	  }

	  public boolean isDeletionCompleted() 
	  {
	    return deletionCompleted;
	  }

	  public static NewsArticleDeletedEvent deletionForbidden(Long id) 
	  {
		  NewsArticleDeletedEvent ev = new NewsArticleDeletedEvent(id);
		  ev.entityFound=true;
		  ev.deletionCompleted=false;
		  return ev;
	  }

	  public static NewsArticleDeletedEvent notFound(Long id) 
	  {
		  NewsArticleDeletedEvent ev = new NewsArticleDeletedEvent(id);
		  ev.entityFound=false;
		  ev.deletionCompleted=false;
		  return ev;
	  }
}
