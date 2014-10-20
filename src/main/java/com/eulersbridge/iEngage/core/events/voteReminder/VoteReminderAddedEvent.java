/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

/**
 * @author Greg Newitt
 *
 */
public class VoteReminderAddedEvent
{

	private VoteReminderDetails voteReminderDetails;
	private boolean userFound =true; 
	private boolean electionFound = true;

	/**
	 * @return the voteReminderDetails
	 */
	public VoteReminderDetails getVoteReminderDetails()
	{
		return voteReminderDetails;
	}

	public void setVoteReminderDetails(VoteReminderDetails dets) 
	{
		this.voteReminderDetails=dets;
		
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
