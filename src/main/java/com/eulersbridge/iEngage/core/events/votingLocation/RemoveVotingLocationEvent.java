/**
 * 
 */
package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Greg Newitt
 *
 */
public class RemoveVotingLocationEvent extends DeleteEvent
{
	private Long votingLocationId;
	private Long electionId;
	
	public RemoveVotingLocationEvent(final Long votingLocationid, final Long electionId) 
	{
		super(null);
		this.votingLocationId=votingLocationid;
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
	 * @return the electionId
	 */
	public Long getElectionId()
	{
		return electionId;
	}

}
