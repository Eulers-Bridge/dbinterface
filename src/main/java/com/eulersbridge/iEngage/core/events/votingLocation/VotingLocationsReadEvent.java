/**
 * 
 */
package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.AllReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationsReadEvent extends AllReadEvent
{
	Iterable<VotingLocationDetails> votingLocations;
	
	public VotingLocationsReadEvent(Iterable<VotingLocationDetails> votingLocations, Long totalItems, Integer totalPages)
	{
		super(null,totalItems,totalPages);
		this.votingLocations=votingLocations;
	}

	public VotingLocationsReadEvent(Iterable<VotingLocationDetails> votingLocations)
	{
		super(null);
		this.votingLocations=votingLocations;
	}

	public VotingLocationsReadEvent()
	{
		super(null);
	}
	
	/**
	 * @return the votingLocations
	 */
	public Iterable<VotingLocationDetails> getVotingLocations() 
	{
		return votingLocations;
	}

	/**
	 * @param votingLocations the votingLocations to set
	 */
	public void setVotingLocations(Iterable<VotingLocationDetails> votingLocations) 
	{
		this.votingLocations = votingLocations;
	}
	
	public static VotingLocationsReadEvent notFound(Long id)
	{
		VotingLocationsReadEvent ev = new VotingLocationsReadEvent();
		ev.entityFound = false;
		return ev;
	}
}
