/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

/**
 * @author Greg Newitt
 *
 */
public class AddVoteRecordEvent
{
	VoteRecordDetails voteRecordDetails;
	
	/**
	 * 
	 */
	public AddVoteRecordEvent()
	{
		super();
	}
	/**
	 * @param email
	 * @param electionId
	 * @param date
	 * @param voteRecordDetails
	 */
	public AddVoteRecordEvent(VoteRecordDetails voteRecordDetails)
	{
		super();
		this.voteRecordDetails=voteRecordDetails;
	}
	/**
	 * @return the voteRecordDetails
	 */
	public VoteRecordDetails getVoteRecordDetails() {
		return voteRecordDetails;
	}
	/**
	 * @param voteRecordDetails the voteRecordDetails to set
	 */
	public void setVoteRecordDetails(VoteRecordDetails voteRecordDetails) {
		this.voteRecordDetails = voteRecordDetails;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddVoteRecordEvent [voteRecordDetails=" + voteRecordDetails
				+ "]";
	}
	
}
