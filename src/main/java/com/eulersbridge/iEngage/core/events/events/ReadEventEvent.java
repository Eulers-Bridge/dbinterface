package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadEventEvent extends ReadEvent{
    private Long eventId;
    private EventDetails eventDetails;

    public ReadEventEvent(Long eventId) {
        this.eventId = eventId;
    }

    public ReadEventEvent(Long eventId, EventDetails eventDetails) {
        this.eventId = eventId;
        this.eventDetails = eventDetails;
    }

    public Long getEventId() {
        return eventId;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public static ReadEventEvent notFound(Long eventId){
        ReadEventEvent readEventEvent = new ReadEventEvent(eventId);
        readEventEvent.entityFound = false;
        return readEventEvent;
    }
}
