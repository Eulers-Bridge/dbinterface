package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.database.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class EventDetails {
    private Long eventId;
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

    private static Logger LOG = LoggerFactory.getLogger(EventDetails.class);

    public EventDetails(){}

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer("[ id = ");
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
