package com.eulersbridge.iEngage.database.domain;

import java.util.Arrays;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.eulersbridge.iEngage.core.events.events.EventDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Event extends Likeable
{
    @GraphId private Long eventId;
    private String name;
    private String location;
    @Indexed @NotNull private Long starts;
    private Long ends;
    private String description;
    private String volunteerPositions[];
    private Long created;
    private String organizer;
	@RelatedTo(type=DatabaseDomainConstants.HAS_PHOTO_LABEL,direction=Direction.BOTH) @Fetch
	private Iterable<Photo> photos;
//	@RelatedTo(type = DatabaseDomainConstants.CREATED_BY_LABEL, direction=Direction.BOTH) @Fetch
//	private User creator;
    private String organizerEmail;
    private Long modified;
	@RelatedTo(type = DatabaseDomainConstants.HAS_EVENT_LABEL, direction=Direction.BOTH) @Fetch
	private NewsFeed newsFeed;

    private static Logger LOG = LoggerFactory.getLogger(Event.class);

    public Event()
    {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public static Event fromEventDetails(EventDetails eventDetails)
    {
        if (LOG.isTraceEnabled()) LOG.trace("fromEventDetails()");
        Event event = new Event();
        if (LOG.isTraceEnabled()) LOG.trace("eventDetails "+eventDetails);
        event.setEventId(eventDetails.getEventId());
        event.setName(eventDetails.getName());
        event.setLocation(eventDetails.getLocation());
        event.setStarts(eventDetails.getStarts());
        event.setEnds(eventDetails.getEnds());
        event.setDescription(eventDetails.getDescription());
        event.setVolunteerPositions(eventDetails.getVolunteerPositions());
        event.setCreated(eventDetails.getCreated());
        event.setOrganizer(eventDetails.getOrganizer());
        event.setOrganizerEmail(eventDetails.getOrganizerEmail());
        event.setModified(eventDetails.getModified());
        Institution inst=new Institution();
        inst.setNodeId(eventDetails.getInstitutionId());
	    NewsFeed nf=new NewsFeed();
	    nf.setInstitution(inst);
		event.setNewsFeed(nf);

        if (LOG.isTraceEnabled()) LOG.trace("event "+event);
        return event;
    }

    public EventDetails toEventDetails()
    {
        if (LOG.isTraceEnabled()) LOG.trace("toEventDetails()");
        EventDetails eventDetails = new EventDetails();
        if (LOG.isTraceEnabled()) LOG.trace("event "+this);
        eventDetails.setEventId(this.getEventId());
        eventDetails.setName(getName());
        eventDetails.setLocation(getLocation());
        eventDetails.setStarts(getStarts());
        eventDetails.setEnds(getEnds());
	    if (getNewsFeed()!=null)
	    {
	    	if (getNewsFeed().getInstitution()!=null)
	    		eventDetails.setInstitutionId(getNewsFeed().getInstitution().getNodeId());
	    }
        eventDetails.setDescription(getDescription());
	    eventDetails.setPhotos(Photo.photosToPhotoDetails(getPhotos()));	
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

    public Long getStarts() {
        return starts;
    }

    public void setStarts(Long starts) {
        this.starts = starts;
    }

    /**
	 * @return the ends
	 */
	public Long getEnds() {
		return ends;
	}

	/**
	 * @param ends the ends to set
	 */
	public void setEnds(Long ends) {
		this.ends = ends;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Iterable<Photo> getPhotos()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getPhotos() = "+photos);
		return photos;
	}
	
	public void setPhotos(Iterable<Photo> picture)
	{
		this.photos=picture;
		
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
	 * @return the institution
	 */
	public NewsFeed getNewsFeed()
	{
		return newsFeed;
	}

	/**
	 * @param institution the institution to set
	 */
	public void setNewsFeed(NewsFeed newsFeed) {
		this.newsFeed = newsFeed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (this.eventId!=null)
		{
			result = prime * result + eventId.hashCode();
		}
		else
		{
			result = prime * result + ((created == null) ? 0 : created.hashCode());
			result = prime * result
					+ ((description == null) ? 0 : description.hashCode());
			result = prime * result + ((ends == null) ? 0 : ends.hashCode());
			result = prime * result
					+ ((newsFeed == null) ? 0 : newsFeed.hashCode());
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
			result = prime * result + ((starts == null) ? 0 : starts.hashCode());
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
		Event other = (Event) obj;
		if (eventId != null)
		{
			if (eventId.equals(other.eventId))
				return true;
			else return false;
		}
		else
		{
			if (other.eventId != null)
				return false;
			if (created == null) {
				if (other.created != null)
					return false;
			} else if (!created.equals(other.created))
				return false;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (ends == null) {
				if (other.ends != null)
					return false;
			} else if (!ends.equals(other.ends))
				return false;
			if (newsFeed == null) {
				if (other.newsFeed != null)
					return false;
			} else if (!newsFeed.equals(other.newsFeed))
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
			if (starts == null) {
				if (other.starts != null)
					return false;
			} else if (!starts.equals(other.starts))
				return false;
			if (!Arrays.equals(volunteerPositions, other.volunteerPositions))
				return false;
		}
		return true;
	}

}
