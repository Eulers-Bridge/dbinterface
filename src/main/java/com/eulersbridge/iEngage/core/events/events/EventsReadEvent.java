/**
 * 
 */
package com.eulersbridge.iEngage.core.events.events;

import java.util.ArrayList;
import java.util.Collection;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class EventsReadEvent extends ReadEvent
{
	private Long instId;
	private boolean newsFeedFound=true;
	private boolean institutionFound=true;
	private boolean eventsFound=true;
	private Long totalArticles;
	private Integer totalPages;
	
	private Collection<EventDetails> articles;

	public EventsReadEvent()
	{
		super();
	}
	
	public EventsReadEvent(Long instId,Collection<EventDetails>articles)
	{
		this.instId=instId;
		this.articles=articles;
	}
	
	public EventsReadEvent(Long institutionId,
			ArrayList<EventDetails> dets, long totalElements,
			int totalPages) 
	{
		this(institutionId,dets);
		this.totalArticles=totalElements;
		this.totalPages=totalPages;
	}

	/**
	 * @return the instId
	 */
	public Long getInstId() {
		return instId;
	}

	/**
	 * @param instId the instId to set
	 */
	public void setInstId(Long instId) {
		this.instId = instId;
	}

	/**
	 * @return the newsFeedFound
	 */
	public boolean isNewsFeedFound() {
		return newsFeedFound;
	}

	/**
	 * @param newsFeedFound the newsFeedFound to set
	 */
	public void setNewsFeedFound(boolean newsFeedFound) {
		this.newsFeedFound = newsFeedFound;
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

	/**
	 * @return the eventsFound
	 */
	public boolean isEventsFound() {
		return eventsFound;
	}

	/**
	 * @param eventsFound the eventsFound to set
	 */
	public void setEventsFound(boolean eventsFound) {
		this.eventsFound = eventsFound;
	}

	/**
	 * @return the totalArticles
	 */
	public Long getTotalArticles() {
		return totalArticles;
	}

	/**
	 * @param totalArticles the totalArticles to set
	 */
	public void setTotalArticles(Long totalArticles) {
		this.totalArticles = totalArticles;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the articles
	 */
	public Collection<EventDetails> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Collection<EventDetails> articles) {
		this.articles = articles;
	}

	public static EventsReadEvent newsFeedNotFound() 
	{
		EventsReadEvent nare=new EventsReadEvent();
		nare.setNewsFeedFound(false);
		nare.setEventsFound(false);
		nare.entityFound=false;
		return nare;
	}

	public static EventsReadEvent institutionNotFound() 
	{
		EventsReadEvent nare=new EventsReadEvent();
		nare.setInstitutionFound(false);
		nare.setNewsFeedFound(false);
		nare.setEventsFound(false);
		nare.entityFound=false;
		return nare;
	}

}
