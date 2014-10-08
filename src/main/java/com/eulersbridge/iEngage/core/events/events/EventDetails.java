package com.eulersbridge.iEngage.core.events.events;

import java.util.Arrays;

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

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		if (eventId != null)
		{	
			result = prime * result + eventId.hashCode();
		}
		else
		{
			result = prime * result + ((created == null) ? 0 : created.hashCode());
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result
					+ ((description == null) ? 0 : description.hashCode());
			result = prime * result
					+ ((location == null) ? 0 : location.hashCode());
			result = prime * result
					+ ((modified == null) ? 0 : modified.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((organizer == null) ? 0 : organizer.hashCode());
			result = prime * result
					+ ((organizerEmail == null) ? 0 : organizerEmail.hashCode());
			result = prime * result + Arrays.hashCode(picture);
			result = prime * result + Arrays.hashCode(volunteerPositions);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventDetails other = (EventDetails) obj;
		
		if (eventId != null)
		{	
			if (eventId.equals(other.eventId))
				return true;
			else return false;
		}
		else
		{
			if (other.eventId!=null) return false;
			if (created == null) {
				if (other.created != null)
					return false;
			} else if (!created.equals(other.created))
				return false;
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
				return false;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (location == null) {
				if (other.location != null)
					return false;
			} else if (!location.equals(other.location))
				return false;
			if (modified == null) {
				if (other.modified != null)
					return false;
			} else if (!modified.equals(other.modified))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (organizer == null) {
				if (other.organizer != null)
					return false;
			} else if (!organizer.equals(other.organizer))
				return false;
			if (organizerEmail == null) {
				if (other.organizerEmail != null)
					return false;
			} else if (!organizerEmail.equals(other.organizerEmail))
				return false;
			if (!Arrays.equals(picture, other.picture))
				return false;
			if (!Arrays.equals(volunteerPositions, other.volunteerPositions))
				return false;
		}
		return true;
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
