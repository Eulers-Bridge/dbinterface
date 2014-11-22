/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import com.eulersbridge.iEngage.core.events.CreatedEvent;


/**
 * @author Greg Newitt
 *
 */
public class VoteRecordAddedEvent extends CreatedEvent
{
	private boolean userFound =true; 
	private boolean electionFound = true;

	/**
	 * 
	 */
	public VoteRecordAddedEvent()
	{
		super();
	}
	/**
	 * @param details
	 */
	public VoteRecordAddedEvent(VoteRecordDetails voteRecordDetails)
	{
		super(voteRecordDetails);
	}
	
	/**
	 * @return the userFound
	 */
	public boolean isUserFound() {
		return userFound;
	}

	/**
	 * @return the electionFound
	 */
	public boolean isElectionFound() {
		return electionFound;
	}

	public static VoteRecordAddedEvent electionNotFound()
	{
		VoteRecordAddedEvent evt=new VoteRecordAddedEvent();
		evt.electionFound=false;
		
		return evt;
	}

	public static VoteRecordAddedEvent userNotFound()
	{
		VoteRecordAddedEvent evt=new VoteRecordAddedEvent();
		evt.userFound=false;
		
		return evt;
	}

}
