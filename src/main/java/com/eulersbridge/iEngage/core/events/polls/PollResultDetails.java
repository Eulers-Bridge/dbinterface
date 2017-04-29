/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.database.domain.PollResultTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	static public ArrayList<PollResult> toPollResultList(Iterator<PollResultTemplate> iter,int numAnswers)
	{
		ArrayList<PollResult> al=new ArrayList<PollResult>();
		for (int i=0;i<numAnswers;i++)
		{
			al.add(i, new PollResult(i,0));
		}
		while(iter.hasNext())
		{
			PollResultTemplate prt=iter.next();
			Integer answer=prt.getAnswer();
			Integer freq=prt.getFrequency();
			PollResult elem=new PollResult(answer,freq);
			al.set(answer,elem);
		}
		return al;
	}

}
