package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.events.EventDetails;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Event extends Likeable {
  private String name;
  private String location;
  @Index
  @NotNull
  private Long starts;
  private Long ends;
  private String description;
  private String volunteerPositions[];
  private Long created;
  private String organizer;
  //@Fetch
  @Relationship(type = DatabaseDomainConstants.HAS_PHOTO_LABEL)
  private List<Node> photos;
  private String organizerEmail;
  private Long modified;
  //@Fetch
  @Relationship(type = DatabaseDomainConstants.HAS_EVENT_LABEL)
  private Node newsFeed;

  private static Logger LOG = LoggerFactory.getLogger(Event.class);

  public Event() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public static Event fromEventDetails(EventDetails eventDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromEventDetails()");
    Event event = new Event();
    if (LOG.isTraceEnabled()) LOG.trace("eventDetails " + eventDetails);
    event.setNodeId(eventDetails.getEventId());
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

    if (LOG.isTraceEnabled()) LOG.trace("event " + event);
    return event;
  }

  public EventDetails toEventDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toEventDetails()");
    EventDetails eventDetails = new EventDetails();
    if (LOG.isTraceEnabled()) LOG.trace("event " + this);
    eventDetails.setEventId(this.getNodeId());
    eventDetails.setName(getName());
    eventDetails.setLocation(getLocation());
    eventDetails.setStarts(getStarts());
    eventDetails.setEnds(getEnds());
    if (getNewsFeed() != null) {
      if (getNewsFeed().getInstitution() != null)
        eventDetails.setInstitutionId(getNewsFeed().getInstitution().getNodeId());
    }
    eventDetails.setDescription(getDescription());
    eventDetails.setPhotos(Photo.photosToPhotoDetails(getPhotos()));
    eventDetails.setVolunteerPositions(getVolunteerPositions());
    eventDetails.setCreated(getCreated());
    eventDetails.setOrganizer(getOrganizer());
    eventDetails.setOrganizerEmail(getOrganizerEmail());
    eventDetails.setModified(getModified());

    if (LOG.isTraceEnabled()) LOG.trace("eventDetails; " + eventDetails);
    return eventDetails;
  }

  @Override
  public String toString() {
    String buff = "[ nodeId = " + getNodeId() +
      ", name = " +
      getName() +
      ", location = " +
      getLocation() +
      ", starts = " +
      getStarts() +
      ", ends = " +
      getEnds() +
      ", description = " +
      getDescription() +
      ", photos = " +
      getPhotos() +
      ", volunteerPositions = " +
      Arrays.toString(getVolunteerPositions()) +
      ", created = " +
      getCreated() +
      ", organizer = " +
      getOrganizer() +
      ", organizerEmail = " +
      getOrganizerEmail() +
      ", modified = " +
      getModified() +
      " ]";
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + buff);
    return buff;
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

  Long getStarts() {
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

  public Iterable<Photo> getPhotos() {
    if (LOG.isDebugEnabled()) LOG.debug("getPhotos() = " + photos);
    return castList(photos, Photo.class);
  }

  public void setPhotos(List<Node> picture) {
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
   * @return the institution
   */
  public NewsFeed getNewsFeed() {
    return (NewsFeed) newsFeed;
  }

  /**
   * @param newsFeed the newsFeed to set
   */
  public void setNewsFeed(Node newsFeed) {
    this.newsFeed = newsFeed;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (this.nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
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
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
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
