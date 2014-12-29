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
	private boolean answerValid = true;
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

	/**
	 * @return the answerValid
	 */
	public boolean isAnswerValid()
	{
		return answerValid;
	}

	/**
	 * @param answerValid the answerValid to set
	 */
	public void setAnswerValid(boolean answerValid)
	{
		this.answerValid = answerValid;
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

	public static PollAnswerCreatedEvent badAnswer(Integer answerIndex)
	{
		Long index=null;
		if (answerIndex!=null) index=new Long(answerIndex);
		PollAnswerCreatedEvent evt = new PollAnswerCreatedEvent(index);
		evt.setAnswerValid(false);
		return evt;
	}

}
