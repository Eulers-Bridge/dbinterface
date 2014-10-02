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
    @GraphId private Long eventId;
    private String name;
    private String location;
    private Long date;
    private String description;
    private String picture[];
    private String volunteerPositions[];
    private Long created;
    private String organizer;
    private String organizerEmail;
    private Long modified;

    private static Logger LOG = LoggerFactory.getLogger(Event.class);

    public Event(){
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public static Event fromEventDetails(EventDetails eventDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromEventDetails()");
        Event event = new Event();
        if (LOG.isTraceEnabled()) LOG.trace("eventDetails "+eventDetails);
        event.setEventId(eventDetails.getEventId());
        event.setName(eventDetails.getName());
        event.setLocation(eventDetails.getLocation());
        event.setDate(eventDetails.getDate());
        event.setDescription(eventDetails.getDescription());
        event.setPicture(eventDetails.getPicture());
        event.setVolunteerPositions(eventDetails.getVolunteerPositions());
        event.setCreated(eventDetails.getCreated());
        event.setOrganizer(eventDetails.getOrganizer());
        event.setOrganizerEmail(eventDetails.getOrganizerEmail());
        event.setModified(eventDetails.getModified());

        if (LOG.isTraceEnabled()) LOG.trace("event "+event);
        return event;
    }

    public EventDetails toEventDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toEventDetails()");
        EventDetails eventDetails = new EventDetails();
        if (LOG.isTraceEnabled()) LOG.trace("event "+this);
        eventDetails.setEventId(this.getEventId());
        eventDetails.setName(getName());
        eventDetails.setLocation(getLocation());
        eventDetails.setDate(getDate());
        eventDetails.setDescription(getDescription());
        eventDetails.setPicture(getPicture());
        eventDetails.setVolunteerPositions(getVolunteerPositions());
        eventDetails.setCreated(getCreated());
        eventDetails.setOrganizer(getOrganizer());
        eventDetails.setOrganizerEmail(getOrganizerEmail());
        eventDetails.setModified(getModified());

        if (LOG.isTraceEnabled()) LOG.trace("eventDetails; "+ eventDetails);
        return eventDetails;
    }

    @Override
    public String toString()
    {
        StringBuffer buff=new StringBuffer("[ nodeId = ");
        String retValue;
        buff.append(getEventId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", location = ");
        buff.append(getLocation());
        buff.append(", date = ");
        buff.append(getDate());
        buff.append(", description = ");
        buff.append(getDescription());
        buff.append(", picture = ");
        buff.append(getPicture());
        buff.append(", volunteerPositions = ");
        buff.append(getVolunteerPositions());
        buff.append(", created = ");
        buff.append(getCreated());
        buff.append(", organizer = ");
        buff.append(getOrganizer());
        buff.append(", organizerEmail = ");
        buff.append(getOrganizerEmail());
        buff.append(", modified = ");
        buff.append(getModified());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPicture() {
        return picture;
    }

    public void setPicture(String[] picture) {
        this.picture = picture;
    }

    public String[] getVolunteerPositions() {
        return volunteerPositions;
    }

    public void setVolunteerPositions(String[] volunteerPositions) {
        this.volunteerPositions = volunteerPositions;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }
}
