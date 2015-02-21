package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Greg Newitt
 */

public class DeleteVotingLocationEvent extends DeleteEvent
{
    public DeleteVotingLocationEvent(Long badgeId) {
        super(badgeId);
    }
}
