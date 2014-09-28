package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.core.services.EventService;
import com.eulersbridge.iEngage.rest.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    EventService eventService;

    public EventController(){}

    private static Logger LOG = LoggerFactory.getLogger(EventController.class);

    //Create
    @RequestMapping(method = RequestMethod.POST, value = "/event")
    public @ResponseBody
    ResponseEntity<Event> createEvent(@RequestBody Event event){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create event "+event);
        CreateEventEvent createEventEvent = new CreateEventEvent(event.toEventDetails());
        EventCreatedEvent eventCreatedEvent = eventService.createEvent(createEventEvent);
        if(eventCreatedEvent.getEventId() == null){
            return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
        }
        else{
            Event result = Event.fromEventDetails(eventCreatedEvent.getEventDetails());
            if (LOG.isDebugEnabled()) LOG.debug("event"+result.toString());
            return new ResponseEntity<Event>(result, HttpStatus.OK);
        }
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = "/event/{eventId}")
    public @ResponseBody
    ResponseEntity<Event> findEvent(@PathVariable Long eventId){
        if (LOG.isInfoEnabled()) LOG.info(eventId+" attempting to get event. ");
        RequestReadEventEvent requestReadEventEvent = new RequestReadEventEvent(eventId);
        ReadEventEvent readEventEvent = eventService.requestReadEvent(requestReadEventEvent);
        if(readEventEvent.isEntityFound()){
            Event event = Event.fromEventDetails(readEventEvent.getEventDetails());
            return new ResponseEntity<Event>(event, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = "/event/{eventId}")
    public @ResponseBody
    ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody Event event){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update event. " + eventId);
        EventUpdatedEvent eventUpdatedEvent = eventService.updateEvent(new UpdateEventEvent(eventId, event.toEventDetails()));
        if ((null != eventUpdatedEvent))
        {
            if (LOG.isDebugEnabled()) LOG.debug("eventUpdatedEvent - "+eventUpdatedEvent);
            if(eventUpdatedEvent.isEntityFound())
            {
                Event resultEvent = Event.fromEventDetails(eventUpdatedEvent.getEventDetails());
                if (LOG.isDebugEnabled()) LOG.debug("resultEvent = "+resultEvent);
                return new ResponseEntity<Event>(resultEvent, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = "/event/{eventId}")
    public @ResponseBody
    ResponseEntity<Boolean> deleteEvent(@PathVariable Long eventId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete event. " + eventId);
        EventDeletedEvent eventDeletedEvent = eventService.deleteEvent(new DeleteEventEvent(eventId));
        Boolean isDeletionCompleted = Boolean.valueOf(eventDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }
}
