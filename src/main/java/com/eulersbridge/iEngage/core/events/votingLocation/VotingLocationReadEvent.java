package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 */

public class VotingLocationReadEvent extends ReadEvent
{
	public VotingLocationReadEvent(Long votingLocationId)
	{
		super(votingLocationId);
	}

	public VotingLocationReadEvent(Long votingLocationId, VotingLocationDetails votingLocationDetails)
	{
		super(votingLocationId, votingLocationDetails);
	}
}
