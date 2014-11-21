package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteEventEvent extends DeleteEvent
{
    public DeleteEventEvent(Long eventId)
    {
        super(eventId);
    }

    public Long getEventId()
    {
        return getNodeId();
    }
}
