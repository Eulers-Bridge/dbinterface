package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Greg Newitt
 */

public class VotingLocationDeletedEvent extends DeletedEvent
{
	public VotingLocationDeletedEvent(Long votingLocationId)
	{
		super(votingLocationId);
	}
}
