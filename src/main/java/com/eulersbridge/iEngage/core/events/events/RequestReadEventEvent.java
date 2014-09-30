package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadEventEvent extends RequestReadEvent{
    private Long eventId;

    public RequestReadEventEvent(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }
}
