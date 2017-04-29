/**
 * 
 */
package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Greg Newitt
 *
 */
public class ForumQuestionsReadEvent extends ReadEvent
{
	private Long instId;
	private boolean newsFeedFound = true;
	private boolean institutionFound = true;
	private boolean eventsFound = true;
	private Long totalEvents;
	private Integer totalPages;

	private Collection<ForumQuestionDetails> forumQuestions;

	public ForumQuestionsReadEvent()
	{
		super(1l);
	}

	public ForumQuestionsReadEvent(Long instId,
			Collection<ForumQuestionDetails> forumQuestions)
	{
		super(1l);
		this.instId = instId;
		this.forumQuestions = forumQuestions;
	}

	public ForumQuestionsReadEvent(Long institutionId,
			ArrayList<ForumQuestionDetails> dets, long totalElements,
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
	 * @return the forumQuestions
	 */
	public Collection<ForumQuestionDetails> getForumQuestions()
	{
		return forumQuestions;
	}

	/**
	 * @param forumQuestions
	 *            the forumQuestions to set
	 */
	public void setPolls(Collection<ForumQuestionDetails> articles)
	{
		this.forumQuestions = articles;
	}

	public static ForumQuestionsReadEvent newsFeedNotFound()
	{
		ForumQuestionsReadEvent nare = new ForumQuestionsReadEvent();
		nare.setNewsFeedFound(false);
		nare.setEventsFound(false);
		nare.entityFound = false;
		return nare;
	}

	public static ForumQuestionsReadEvent institutionNotFound()
	{
		ForumQuestionsReadEvent nare = new ForumQuestionsReadEvent();
		nare.setInstitutionFound(false);
		nare.setNewsFeedFound(false);
		nare.setEventsFound(false);
		nare.entityFound = false;
		return nare;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ForumQuestionsReadEvent [instId=" + instId + ", newsFeedFound="
				+ newsFeedFound + ", institutionFound=" + institutionFound
				+ ", eventsFound=" + eventsFound + ", totalEvents="
				+ totalEvents + ", totalPages=" + totalPages
				+ ", forumQuestions=" + forumQuestions + "]";
	}

}
