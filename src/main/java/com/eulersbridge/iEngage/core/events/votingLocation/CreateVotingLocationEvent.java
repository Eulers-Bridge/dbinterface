package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 */

public class CreateVotingLocationEvent extends CreateEvent
{
    public CreateVotingLocationEvent(Details details) {
        super(details);
    }
}
