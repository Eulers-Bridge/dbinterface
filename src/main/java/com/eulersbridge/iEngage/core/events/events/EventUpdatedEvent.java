package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class EventUpdatedEvent extends UpdatedEvent{
    private Long eventId;
    private EventDetails eventDetails;

    public EventUpdatedEvent(Long eventId, EventDetails eventDetails) {
        this.eventId = eventId;
        this.eventDetails = eventDetails;
    }

    public EventUpdatedEvent(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public static EventUpdatedEvent notFound(Long id){
        EventUpdatedEvent eventUpdatedEvent = new EventUpdatedEvent(id);
        eventUpdatedEvent.entityFound = false;
        return eventUpdatedEvent;
    }
}
