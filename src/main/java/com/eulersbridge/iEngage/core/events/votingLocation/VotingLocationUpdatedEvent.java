package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class VotingLocationUpdatedEvent extends UpdatedEvent
{
	public VotingLocationUpdatedEvent(Long votingLocationId,
			VotingLocationDetails votingLocationDetails)
	{
		super(votingLocationId, votingLocationDetails);
	}

	public VotingLocationUpdatedEvent(Long votingLocationId)
	{
		super(votingLocationId);
	}
}
