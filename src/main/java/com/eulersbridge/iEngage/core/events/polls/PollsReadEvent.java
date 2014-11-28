/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import java.util.ArrayList;
import java.util.Collection;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class PollsReadEvent extends ReadEvent
{
	private Long instId;
	private boolean newsFeedFound = true;
	private boolean institutionFound = true;
	private boolean eventsFound = true;
	private Long totalEvents;
	private Integer totalPages;

	private Collection<PollDetails> photoAlbums;

	public PollsReadEvent()
	{
		super(1l);
	}

	public PollsReadEvent(Long instId,
			Collection<PollDetails> photoAlbums)
	{
		super(1l);
		this.instId = instId;
		this.photoAlbums = photoAlbums;
	}

	public PollsReadEvent(Long institutionId,
			ArrayList<PollDetails> dets, long totalElements,
			int totalPages)
	{
		this(institutionId, dets);
		this.totalEvents = totalElements;
		this.totalPages = totalPages;
	}

	/**
	 * @return the instId
	 */
	public Long getInstId()
	{
		return instId;
	}

	/**
	 * @param instId
	 *            the instId to set
	 */
	public void setInstId(Long instId)
	{
		this.instId = instId;
	}

	/**
	 * @return the newsFeedFound
	 */
	public boolean isNewsFeedFound()
	{
		return newsFeedFound;
	}

	/**
	 * @param newsFeedFound
	 *            the newsFeedFound to set
	 */
	public void setNewsFeedFound(boolean newsFeedFound)
	{
		this.newsFeedFound = newsFeedFound;
	}

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound()
	{
		return institutionFound;
	}

	/**
	 * @param institutionFound
	 *            the institutionFound to set
	 */
	public void setInstitutionFound(boolean institutionFound)
	{
		this.institutionFound = institutionFound;
	}

	/**
	 * @return the eventsFound
	 */
	public boolean isEventsFound()
	{
		return eventsFound;
	}

	/**
	 * @param eventsFound
	 *            the eventsFound to set
	 */
	public void setEventsFound(boolean eventsFound)
	{
		this.eventsFound = eventsFound;
	}

	/**
	 * @return the totalEvents
	 */
	public Long getTotalEvents()
	{
		return totalEvents;
	}

	/**
	 * @param totalEvents
	 *            the totalEvents to set
	 */
	public void setTotalEvents(Long totalEvents)
	{
		this.totalEvents = totalEvents;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages()
	{
		return totalPages;
	}

	/**
	 * @param totalPages
	 *            the totalPages to set
	 */
	public void setTotalPages(Integer totalPages)
	{
		this.totalPages = totalPages;
	}

	/**
	 * @return the photoAlbums
	 */
	public Collection<PollDetails> getPolls()
	{
		return photoAlbums;
	}

	/**
	 * @param photoAlbums
	 *            the photoAlbums to set
	 */
	public void setPolls(Collection<PollDetails> articles)
	{
		this.photoAlbums = articles;
	}

	public static PollsReadEvent newsFeedNotFound()
	{
		PollsReadEvent nare = new PollsReadEvent();
		nare.setNewsFeedFound(false);
		nare.setEventsFound(false);
		nare.entityFound = false;
		return nare;
	}

	public static PollsReadEvent institutionNotFound()
	{
		PollsReadEvent nare = new PollsReadEvent();
		nare.setInstitutionFound(false);
		nare.setNewsFeedFound(false);
		nare.setEventsFound(false);
		nare.entityFound = false;
		return nare;
	}

}
