package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class ReadNewsArticleEvent extends ReadEvent 
{
	private Long id;
	private NewsArticleDetails readNewsArticleDetails;
	
	public ReadNewsArticleEvent(Long id) 
	{
		this.id = id;
	}

	  public ReadNewsArticleEvent(Long id, NewsArticleDetails readNewsArticleDetails) 
	  {
		  this.id = id;
		  this.readNewsArticleDetails = readNewsArticleDetails;
	  }

	  public Long getNewsArticleId() 
	  {
		  return this.id;
	  }

	  public NewsArticleDetails getReadNewsArticleDetails() 
	  {
		  return readNewsArticleDetails;
	  }

	  public static ReadNewsArticleEvent notFound(Long id) 
	  {
		  ReadNewsArticleEvent ev = new ReadNewsArticleEvent(id);
		  ev.entityFound=false;
		  return ev;
	  }
}
