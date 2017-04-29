package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Yikai Gong
 */

public class EventDetails extends Details
{
    private String name;
    private String location;
	private Iterable<PhotoDetails> photos;
    private Long starts;
    private Long ends;
    private String description;
    private String volunteerPositions[];
    private Long created;
    private String organizer;
    private String organizerEmail;
    private Long modified;
    private Long institutionId;

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
        buff.append(", starts = ");
        buff.append(getStarts());
        buff.append(", ends = ");
        buff.append(getEnds());
        buff.append(", description = ");
        buff.append(getDescription());
        buff.append(", photos = ");
        buff.append(getPhotos());
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
        buff.append(", institutionId = ");
        buff.append(getInstitutionId());
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
		if (nodeId != null)
		{	
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((created == null) ? 0 : created.hashCode());
			result = prime * result + ((starts == null) ? 0 : starts.hashCode());
			result = prime * result + ((ends == null) ? 0 : ends.hashCode());
			result = prime * result + ((institutionId == null) ? 0 : institutionId.hashCode());
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
			result = prime * result
					+ ((getPhotos() == null) ? 0 : getPhotos().hashCode());
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
		
		if (nodeId != null)
		{	
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId!=null) return false;
			if (created == null) {
				if (other.created != null)
					return false;
			} else if (!created.equals(other.created))
				return false;
			if (starts == null) {
				if (other.starts != null)
					return false;
			} else if (!starts.equals(other.starts))
				return false;
			if (ends == null) {
				if (other.ends != null)
					return false;
			} else if (!ends.equals(other.ends))
				return false;
			if (institutionId == null) {
				if (other.institutionId != null)
					return false;
			} else if (!institutionId.equals(other.institutionId))
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
			if (getPhotos() == null) {
				if (other.getPhotos() != null)
					return false;
			} else if (!getPhotos().equals(other.getPhotos()))
				return false;
			if (!Arrays.equals(volunteerPositions, other.volunteerPositions))
				return false;
		}
		return true;
	}

	public Long getEventId() {
        return nodeId;
    }

    public void setEventId(Long eventId) {
        this.nodeId = eventId;
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

    public Long getStarts() {
        return starts;
    }

    public void setStarts(Long starts) {
        this.starts = starts;
    }

	public Long getEnds()
	{
		return ends;
	}
    /**
	 * @param ends the ends to set
	 */
	public void setEnds(Long ends)
	{
		this.ends = ends;
	}

	public String getDescription()
	{
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Iterable<PhotoDetails> getPhotos()
	{
		return this.photos;
	}
	
	public void setPhotos(Iterable<PhotoDetails> picture)
	{
		this.photos = picture;
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

	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

}
