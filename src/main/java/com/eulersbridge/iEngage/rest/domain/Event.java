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

        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).withSelfRel());
        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).slash("previous").withRel("Previous"));
        event.add(linkTo(EventController.class).slash(name).slash(event.eventId).slash("next").withRel("Next"));
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
