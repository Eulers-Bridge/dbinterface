package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteEventEvent extends DeleteEvent{
    private final Long eventId;

    public DeleteEventEvent(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }
}
