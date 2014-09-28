package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class EventCreatedEvent extends CreatedEvent{
    private EventDetails eventDetails;
    private Long eventId;

    public EventCreatedEvent( Long eventId, EventDetails eventDetails) {
        this.eventDetails = eventDetails;
        this.eventId = eventId;
    }

    public EventCreatedEvent(Long eventId) {
        this.eventId = eventId;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
