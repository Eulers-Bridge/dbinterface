/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import java.util.List;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class PollResultDetails extends Details
{
	Long pollId;
	private List<PollResult> answers;
	
	public PollResultDetails(Long pollId, List<PollResult> results)
	{
		super(pollId);
		this.answers=results;
	}
	/**
	 * @return the pollId
	 */
	public Long getPollId()
	{
		return getNodeId();
	}
	/**
	 * @param pollId the pollId to set
	 */
	public void setPollId(Long pollId)
	{
		setNodeId(pollId);
	}
	/**
	 * @return the answers
	 */
	public List<PollResult> getAnswers()
	{
		return answers;
	}
	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<PollResult> answers)
	{
		this.answers = answers;
	}

}
