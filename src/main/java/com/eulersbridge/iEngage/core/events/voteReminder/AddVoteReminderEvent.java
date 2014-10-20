/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

/**
 * @author Greg Newitt
 *
 */
public class AddVoteReminderEvent
{
	VoteReminderDetails voteReminderDetails;
	
	/**
	 * 
	 */
	public AddVoteReminderEvent()
	{
		super();
	}
	/**
	 * @param email
	 * @param electionId
	 * @param date
	 * @param location
	 */
	public AddVoteReminderEvent(VoteReminderDetails voteReminderDetails)
	{
		super();
		this.voteReminderDetails = voteReminderDetails;
	}
	
	/**
	 * @return the voteReminderDetails
	 */
	public VoteReminderDetails getVoteReminderDetails() {
		return voteReminderDetails;
	}
	/**
	 * @param voteReminderDetails the voteReminderDetails to set
	 */
	public void setVoteReminderDetails(VoteReminderDetails voteReminderDetails) {
		this.voteReminderDetails = voteReminderDetails;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddVoteReminderEvent [voteReminderDetails="
				+ voteReminderDetails + "]";
	}
}
