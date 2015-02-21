package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Greg Newitt
 */

public class UpdateVotingLocationEvent extends UpdateEvent
{
    public UpdateVotingLocationEvent(Long locationId, VotingLocationDetails votingLocationDetails)
    {
        super(locationId, votingLocationDetails);
    }
}
