/**
 * 
 */
package com.eulersbridge.iEngage.core.events.events;

import java.util.ArrayList;
import java.util.Collection;

import com.eulersbridge.iEngage.core.events.AllReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class EventsReadEvent extends AllReadEvent
{
	private boolean newsFeedFound=true;
	private boolean institutionFound=true;
	private boolean eventsFound=true;
	
	private Iterable<EventDetails> events;

	public EventsReadEvent()
	{
		super(1l);
	}
	
	public EventsReadEvent(Long instId,Collection<EventDetails>articles)
	{
		super(instId);
		this.events=articles;
	}
	
	public EventsReadEvent(Long institutionId,
			ArrayList<EventDetails> dets, long totalElements,
			int totalPages) 
	{
		super(institutionId,totalElements,totalPages);
		this.events=dets;
	}

	/**
	 * @return the instId
	 */
	public Long getInstId() {
		return getParentId();
	}

	/**
	 * @param instId the instId to set
	 */
	public void setInstId(Long instId) {
		setParentId(instId);
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
	 * @return the events
	 */
	public Iterable<EventDetails> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(Collection<EventDetails> articles) {
		this.events = articles;
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
