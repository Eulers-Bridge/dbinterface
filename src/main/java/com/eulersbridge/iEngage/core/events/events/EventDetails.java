package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.database.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class EventDetails {
    private Long eventId;
    //TODO Add properties

    private static Logger LOG = LoggerFactory.getLogger(EventDetails.class);

    public EventDetails(){}

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getEventId());
        //TODO Add other properties

        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public boolean equals(Object other){
        if (null == other) return false;
        if (other ==this) return true;
        if(!(other instanceof EventDetails)) return false;
        EventDetails eventDetails = (EventDetails) other;
        if (eventDetails.getEventId() != null){
            if(eventDetails.getEventId().equals(getEventId()))
                return true;
        }
        return false;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
