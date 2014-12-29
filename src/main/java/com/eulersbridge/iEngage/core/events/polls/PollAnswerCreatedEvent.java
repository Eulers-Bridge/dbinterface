/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 *
 */
public class PollAnswerCreatedEvent extends CreatedEvent
{
	private Long failedNodeId;
	private boolean answererFound = true;
	private boolean pollFound = true;

	public PollAnswerCreatedEvent(PollAnswerDetails pollAnswerDetails)
	{
		super(pollAnswerDetails);
	}

	public PollAnswerCreatedEvent(Long failedId)
	{
		this.failedNodeId = failedId;
	}

	/**
	 * @return the ownerFound
	 */
	public boolean isAnswererFound()
	{
		return answererFound;
	}

	/**
	 * @param ownerFound the ownerFound to set
	 */
	public void setAnswererFound(boolean answererFound)
	{
		this.answererFound = answererFound;
	}

	/**
	 * @return the failedNodeId
	 */
	public Long getFailedNodeId()
	{
		return failedNodeId;
	}

	/**
	 * @return the creatorFound
	 */
	public boolean isPollFound()
	{
		return pollFound;
	}

	/**
	 * @param creatorFound the creatorFound to set
	 */
	public void setPollFound(boolean pollFound)
	{
		this.pollFound = pollFound;
	}

	public static PollAnswerCreatedEvent answererNotFound(Long answererId)
	{
		PollAnswerCreatedEvent evt = new PollAnswerCreatedEvent(answererId);
		evt.setAnswererFound(false);
		return evt;
	}
	
	public static PollAnswerCreatedEvent pollNotFound(Long pollId)
	{
		PollAnswerCreatedEvent evt = new PollAnswerCreatedEvent(pollId);
		evt.setPollFound(false);
		return evt;
	}

}
