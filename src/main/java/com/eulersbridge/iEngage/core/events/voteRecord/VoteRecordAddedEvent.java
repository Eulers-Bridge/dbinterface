/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;


/**
 * @author Greg Newitt
 *
 */
public class VoteRecordAddedEvent
{
	private VoteRecordDetails voteRecordDetails;
	private boolean userFound =true; 
	private boolean electionFound = true;

	/**
	 * @return the voteRecordDetails
	 */
	public VoteRecordDetails getVoteRecordDetails()
	{
		return voteRecordDetails;
	}

	/**
	 * @param voteRecordDetails the voteRecordDetails to set
	 */
	public void setVoteRecordDetails(VoteRecordDetails voteRecordDetails)
	{
		this.voteRecordDetails = voteRecordDetails;
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
