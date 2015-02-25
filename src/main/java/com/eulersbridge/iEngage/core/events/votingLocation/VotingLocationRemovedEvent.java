/**
 * 
 */
package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationRemovedEvent extends DeletedEvent
{
	private boolean votingLocationFound =true; 
	private boolean electionFound = true;

	public VotingLocationRemovedEvent() 
	{
		super(null);
	}

	public VotingLocationRemovedEvent(Long id) 
	{
		super(id);
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

	public static VotingLocationRemovedEvent electionNotFound()
	{
		VotingLocationRemovedEvent evt=new VotingLocationRemovedEvent();
		evt.electionFound=false;
		
		return evt;
	}

	public static VotingLocationRemovedEvent votingLocationNotFound()
	{
		VotingLocationRemovedEvent evt=new VotingLocationRemovedEvent();
		evt.votingLocationFound=false;
		
		return evt;
	}

}