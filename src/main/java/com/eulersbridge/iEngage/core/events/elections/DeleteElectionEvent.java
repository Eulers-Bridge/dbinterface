package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteElectionEvent extends DeleteEvent
{
    public DeleteElectionEvent(final Long electionId)
    {
        super(electionId);
    }
}
