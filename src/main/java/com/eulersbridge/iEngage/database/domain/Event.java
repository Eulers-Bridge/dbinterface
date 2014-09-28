package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.events.EventDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Event {
    @GraphId
    private Long eventId;
    //TODO Add properties

    private static Logger LOG = LoggerFactory.getLogger(Event.class);

    public Event(){
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public static Event fromEventDetails(EventDetails eventDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromEventDetails()");
        Event event = new Event();
        if (LOG.isTraceEnabled()) LOG.trace("eventDetails "+eventDetails);
        event.setEventId(eventDetails.getEventId());
        //TODO add other properties

        if (LOG.isTraceEnabled()) LOG.trace("event "+event);
        return event;
    }

    public EventDetails toEventDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toEventDetails()");
        EventDetails eventDetails = new EventDetails();
        if (LOG.isTraceEnabled()) LOG.trace("event "+this);
        eventDetails.setEventId(this.getEventId());
        //TODO add other properties

        if (LOG.isTraceEnabled()) LOG.trace("eventDetails; "+ eventDetails);
        return eventDetails;
    }

    @Override
    public String toString()
    {
        StringBuffer buff=new StringBuffer("[ nodeId = ");
        String retValue;
        buff.append(getEventId());
        //TODO add other properties
        buff.append(" ]");
        retValue=buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
