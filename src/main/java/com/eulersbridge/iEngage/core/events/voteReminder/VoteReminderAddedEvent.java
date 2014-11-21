/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 *
 */
public class VoteReminderAddedEvent extends CreatedEvent
{

	private boolean userFound =true; 
	private boolean electionFound = true;

	/**
	 * 
	 */
	public VoteReminderAddedEvent()
	{
		super();
	}
	/**
	 * @param details
	 */
	public VoteReminderAddedEvent(VoteReminderDetails voteReminderDetails)
	{
		super(voteReminderDetails);
	}
	
	/**
	 * @return the voteReminderDetails
	 */
	public VoteReminderDetails getVoteReminderDetails()
	{
		return (VoteReminderDetails) getDetails();
	}

	public void setVoteReminderDetails(VoteReminderDetails dets) 
	{
		setDetails(dets);
		
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

	public static VoteReminderAddedEvent userNotFound()
	{
		VoteReminderAddedEvent evt=new VoteReminderAddedEvent();
		evt.userFound=false;
		
		return evt;
	}

	public static VoteReminderAddedEvent electionNotFound()
	{
		VoteReminderAddedEvent evt=new VoteReminderAddedEvent();
		evt.electionFound=false;
		
		return evt;
	}

}
