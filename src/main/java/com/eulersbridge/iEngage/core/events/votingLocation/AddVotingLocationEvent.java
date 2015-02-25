/**
 * 
 */
package com.eulersbridge.iEngage.core.events.votingLocation;

/**
 * @author Greg Newitt
 *
 */
public class AddVotingLocationEvent
{
	Long votingLocationId;
	Long electionId;
	
	/**
	 * 
	 */
	public AddVotingLocationEvent()
	{
		super();
	}
	/**
	 * @param votingLocationDetails
	 */
	public AddVotingLocationEvent(Long votingLocationId,Long electionId)
	{
		super();
		this.votingLocationId=votingLocationId;
		this.electionId=electionId;
	}
	/**
	 * @return the votingLocationId
	 */
	public Long getVotingLocationId()
	{
		return votingLocationId;
	}
	/**
	 * @param votingLocationId the votingLocationId to set
	 */
	public void setVotingLocationId(Long votingLocationId)
	{
		this.votingLocationId = votingLocationId;
	}
	/**
	 * @return the electionId
	 */
	public Long getElectionId()
	{
		return electionId;
	}
	/**
	 * @param electionId the electionId to set
	 */
	public void setElectionId(Long electionId)
	{
		this.electionId = electionId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "AddVotingLocationEvent [votingLocationId=" + votingLocationId
				+ ", electionId=" + electionId + "]";
	}
	
	

}
