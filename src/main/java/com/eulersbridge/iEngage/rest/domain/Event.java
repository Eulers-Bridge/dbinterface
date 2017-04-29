package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.events.EventDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.rest.controller.EventController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Event extends ResourceSupport{
    private Long eventId;
    private String name;
    private String location;
    private Long starts;
    private Long ends;
    private String description;
	private Iterable<PhotoDetails> photos;
    private String volunteerPositions[];
    private Long created;
    private String organizer;
    private String organizerEmail;
    private Long institutionId;
    private Long modified;

    private static Logger LOG = LoggerFactory.getLogger(Event.class);

    public Event()
    {
    	if (LOG.isTraceEnabled()) LOG.trace("constructor()");
    }

    public static Event fromEventDetails(EventDetails eventDetails){
        Event event = new Event();
        String simpleName = Event.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        event.setEventId(eventDetails.getEventId());
        event.setName(eventDetails.getName());
        event.setLocation(eventDetails.getLocation());
        event.setStarts(eventDetails.getStarts());
        event.setEnds(eventDetails.getEnds());
        event.setDescription(eventDetails.getDescription());
        event.setPhotos(eventDetails.getPhotos());
        event.setVolunteerPositions(eventDetails.getVolunteerPositions());
        event.setCreated(eventDetails.getCreated());
        event.setOrganizer(eventDetails.getOrganizer());
        event.setOrganizerEmail(eventDetails.getOrganizerEmail());
        event.setModified(eventDetails.getModified());
        event.setInstitutionId(eventDetails.getInstitutionId());

	    // {!begin selfRel}
        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).withSelfRel());
	    // {!end selfRel}
	    // {!begin previous}
        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
	    // {!end previous}
	    // {!begin next}
        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
	    // {!end next}
	    // {!begin likedBy}
        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).slash(RestDomainConstants.LIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.LIKEDBY_LABEL));
	    // {!end likedBy}
	    // {!begin unlikedBy}
        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).slash(RestDomainConstants.UNLIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.UNLIKEDBY_LABEL));
	    // {!end unlikedBy}
	    // {!begin likes}
        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).slash(RestDomainConstants.LIKES).withRel(RestDomainConstants.LIKES_LABEL));
	    // {!end likes}
	    // {!begin readAll}
        event.add(linkTo(EventController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}
        return event;
    }

    public EventDetails toEventDetails(){
        EventDetails eventDetails = new EventDetails();

        eventDetails.setEventId(this.getEventId());
        eventDetails.setName(getName());
        eventDetails.setLocation(getLocation());
        eventDetails.setStarts(getStarts());
        eventDetails.setEnds(getEnds());
        eventDetails.setDescription(getDescription());
        eventDetails.setPhotos(getPhotos());
        eventDetails.setVolunteerPositions(getVolunteerPositions());
        eventDetails.setCreated(getCreated());
        eventDetails.setOrganizer(getOrganizer());
        eventDetails.setOrganizerEmail(getOrganizerEmail());
        eventDetails.setModified(getModified());
        eventDetails.setInstitutionId(getInstitutionId());

        return eventDetails;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	/**
	 * @return the photos
	 */
	public Iterable<PhotoDetails> getPhotos()
	{
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(Iterable<PhotoDetails> photos)
	{
		this.photos = photos;
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

	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId)
	{
		this.institutionId = institutionId;
	}

	public static Iterator<Event> toEventsIterator(Iterator<? extends Details> iter)
	{
		if (null==iter) return null;
		ArrayList <Event> events=new ArrayList<Event>();
		while(iter.hasNext())
		{
			EventDetails dets=(EventDetails)iter.next();
			Event thisEvent=Event.fromEventDetails(dets);
			Link self = thisEvent.getLink("self");
			thisEvent.removeLinks();
			thisEvent.add(self);
			events.add(thisEvent);		
		}
		return events.iterator();
	}
}
