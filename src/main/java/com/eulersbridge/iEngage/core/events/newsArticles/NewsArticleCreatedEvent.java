package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class NewsArticleCreatedEvent extends CreatedEvent
{
	private Long id;
	private boolean creatorFound=true;
	private boolean institutionFound=true;
	
	public NewsArticleCreatedEvent(Long id, NewsArticleDetails newsArticleDetails) 
	{
		super(newsArticleDetails);
		this.id=id;
	}

	public NewsArticleCreatedEvent(Long id) 
	{
		super();
		this.id=id;
	}

	public NewsArticleCreatedEvent() 
	{
		super();
	}

	public Long getNewsArticleId() 
	{
		return id;
	}

	public void setKey(Long id)
	{
		this.id = id;
	}

	public void setNewsArticleDetails(NewsArticleDetails newsArticleDetails)
	{
		setDetails(newsArticleDetails);
	}

	public NewsArticleDetails getNewsArticleDetails() 
	{
	    return (NewsArticleDetails) super.getDetails();
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
