/**
 * 
 */
package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadForumQuestionsEvent extends RequestReadEvent
{
	Long ownerId = null;

	public ReadForumQuestionsEvent()
	{
		super(null);
	}

	public ReadForumQuestionsEvent(Long ownerId)
	{
		super(null);
		this.ownerId = ownerId;
	}

	/**
	 * @return the ownerId
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            the ownerId to set
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

}
