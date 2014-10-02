package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.events.EventDetails;
import com.eulersbridge.iEngage.rest.controller.EventController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Event extends ResourceSupport{
    private Long eventId;
    //TODO Add other properties

    private static Logger LOG = LoggerFactory.getLogger(Event.class);

    public Event(){}

    public static Event fromEventDetails(EventDetails eventDetails){
        Event event = new Event();
        String simpleName = Event.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        event.setEventId(eventDetails.getEventId());
        //TODO Add other properties

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
        //TODO Add other properties

        return eventDetails;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

}
