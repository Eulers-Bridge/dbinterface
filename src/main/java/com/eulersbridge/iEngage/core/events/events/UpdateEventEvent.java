package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateEventEvent extends UpdateEvent{
    private Long eventId;
    private EventDetails eventDetails;

    public UpdateEventEvent(Long eventId, EventDetails eventDetails) {
        this.eventId = eventId;
        this.eventDetails = eventDetails;
        this.eventDetails.setEventId(eventId);
    }

    public Long getEventId() {
        return eventId;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }
}
