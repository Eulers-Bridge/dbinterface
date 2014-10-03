package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CreateEventEvent extends CreateEvent {
    private EventDetails eventDetails;

    private static Logger LOG = LoggerFactory.getLogger(CreateEventEvent.class);

    public CreateEventEvent(Long id, EventDetails eventDetails){
        if (LOG.isDebugEnabled()) LOG.debug("CreateEvent("+id+","+eventDetails+") = ");
        eventDetails.setEventId(id);
        this.eventDetails = eventDetails;
    }

    public CreateEventEvent(EventDetails eventDetails){
        if (LOG.isDebugEnabled()) LOG.debug("CreateEvent("+eventDetails+") = ");
        this.eventDetails = eventDetails;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }
}
