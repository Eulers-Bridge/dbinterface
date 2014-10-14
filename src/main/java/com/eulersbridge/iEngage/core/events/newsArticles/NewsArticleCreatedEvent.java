package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class NewsArticleCreatedEvent extends CreatedEvent
{
	private NewsArticleDetails newsArticleDetails;
	private Long id;
	private boolean creatorFound=true;
	private boolean institutionFound=true;
	
	public NewsArticleCreatedEvent(Long id, NewsArticleDetails newsArticleDetails) 
	{
		this.newsArticleDetails=newsArticleDetails;
		this.id=id;
	}

	public NewsArticleCreatedEvent(Long id) 
	{
		this.id=id;
	}

	public NewsArticleCreatedEvent() 
	{
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

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound() {
		return institutionFound;
	}

	/**
	 * @param institutionFound the institutionFound to set
	 */
	public void setInstitutionFound(boolean institutionFound) {
		this.institutionFound = institutionFound;
	}

	public static NewsArticleCreatedEvent creatorNotFound() 
	{
		NewsArticleCreatedEvent ev = new NewsArticleCreatedEvent();
	    ev.setCreatorFound(false);
	    return ev;
	}

	public static NewsArticleCreatedEvent institutionNotFound() {
		NewsArticleCreatedEvent ev = new NewsArticleCreatedEvent();
	    ev.setInstitutionFound(false);
	    return ev;
	}
}
