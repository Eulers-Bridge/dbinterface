package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeletePollEvent extends DeleteEvent
{
    public DeletePollEvent(Long pollId)
    {
        super(pollId);
    }
}
