/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import java.util.Collection;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class PollResultDetails extends Details
{
	Long pollId;
	private Collection<Integer> answers;
	
	public PollResultDetails(Long pollId, Collection<Integer> results)
	{
		this.pollId=pollId;
		this.answers=results;
	}
	/**
	 * @return the pollId
	 */
	public Long getPollId()
	{
		return pollId;
	}
	/**
	 * @param pollId the pollId to set
	 */
	public void setPollId(Long pollId)
	{
		this.pollId = pollId;
	}
	/**
	 * @return the answers
	 */
	public Collection<Integer> getAnswers()
	{
		return answers;
	}
	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(Collection<Integer> answers)
	{
		this.answers = answers;
	}

}
