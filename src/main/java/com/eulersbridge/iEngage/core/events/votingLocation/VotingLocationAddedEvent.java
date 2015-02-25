/**
 * 
 */
package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationAddedEvent extends CreatedEvent
{
	private boolean votingLocationFound =true; 
	private boolean electionFound = true;

	/**
	 * 
	 */
	public VotingLocationAddedEvent()
	{
		super();
	}

	/**
	 * @param details
	 */
	public VotingLocationAddedEvent(VotingLocationDetails votingLocationDetails)
	{
		super(votingLocationDetails);
	}
	
	/**
	 * @return the votingLocationFound
	 */
	public boolean isVotingLocationFound() {
		return votingLocationFound;
	}

	/**
	 * @return the electionFound
	 */
	public boolean isElectionFound() {
		return electionFound;
	}

	public static VotingLocationAddedEvent electionNotFound()
	{
		VotingLocationAddedEvent evt=new VotingLocationAddedEvent();
		evt.electionFound=false;
		
		return evt;
	}

	public static VotingLocationAddedEvent votingLocationNotFound()
	{
		VotingLocationAddedEvent evt=new VotingLocationAddedEvent();
		evt.votingLocationFound=false;
		
		return evt;
	}

}
