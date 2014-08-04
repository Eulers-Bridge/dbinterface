package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class NewsArticleCreatedEvent extends CreatedEvent
{
	private NewsArticleDetails newsArticleDetails;
	private Long id;
	private boolean creatorFound=true;
	
	public NewsArticleCreatedEvent(Long id, NewsArticleDetails newsArticleDetails) 
	{
		this.newsArticleDetails=newsArticleDetails;
		this.id=id;
	}

	public NewsArticleCreatedEvent(Long id) 
	{
		this.id=id;
	}

	public Long getNewsArticleId() 
	{
		return id;
	}

	public void setKey(Long id)
	{
		this.id = id;
	}

	public void setNewsArticleDetails(NewsArticleDetails newsArticleDetails) {
		this.newsArticleDetails = newsArticleDetails;
	}

	public NewsArticleDetails getNewsArticleDetails() 
	{
	    return newsArticleDetails;
	}

	/**
	 * @return the creatorFound
	 */
	public boolean isCreatorFound() {
		return creatorFound;
	}

	/**
	 * @param creatorFound the creatorFound to set
	 */
	public void setCreatorFound(boolean creatorFound) {
		this.creatorFound = creatorFound;
	}

	public static NewsArticleCreatedEvent creatorNotFound(Long nodeId) 
	{
		NewsArticleCreatedEvent ev = new NewsArticleCreatedEvent(nodeId);
	    ev.setCreatorFound(false);
	    return ev;
	}
}
