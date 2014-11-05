package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.services.EventService;
import com.eulersbridge.iEngage.rest.domain.Event;
import com.eulersbridge.iEngage.rest.domain.Events;
import com.eulersbridge.iEngage.rest.domain.NewsArticle;
import com.eulersbridge.iEngage.rest.domain.NewsArticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class EventController {

    @Autowired
    EventService eventService;

    public EventController(){}

    private static Logger LOG = LoggerFactory.getLogger(EventController.class);

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.EVENT_LABEL)
    public @ResponseBody
    ResponseEntity<Event> createEvent(@RequestBody Event event){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create event "+event);
        CreateEventEvent createEventEvent = new CreateEventEvent(event.toEventDetails());
        EventCreatedEvent eventCreatedEvent = eventService.createEvent(createEventEvent);
        ResponseEntity<Event> response;
        if((null==eventCreatedEvent)||(eventCreatedEvent.getEventId() == null))
        {
            response = new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
        }
        else if (!(eventCreatedEvent.isInstitutionFound()))
        {
            response=new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Event result = Event.fromEventDetails(eventCreatedEvent.getEventDetails());
            if (LOG.isDebugEnabled()) LOG.debug("event"+result.toString());
            response = new ResponseEntity<Event>(result, HttpStatus.CREATED);
        }
        return response;
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.EVENT_LABEL+"/{eventId}")
    public @ResponseBody
    ResponseEntity<Event> findEvent(@PathVariable Long eventId){
        if (LOG.isInfoEnabled()) LOG.info(eventId+" attempting to get event. ");
        RequestReadEventEvent requestReadEventEvent = new RequestReadEventEvent(eventId);
        ReadEventEvent readEventEvent = eventService.readEvent(requestReadEventEvent);
        if(readEventEvent.isEntityFound()){
            Event event = Event.fromEventDetails(readEventEvent.getEventDetails());
            return new ResponseEntity<Event>(event, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Is passed all the necessary data to read news articles from the database.
     * The request must be a GET with the institutionId/student year presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the news articles read from the database.
     * 
     * @param email the email address of the user object to be read.
     * @return the user object.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.EVENTS_LABEL+"/{institutionId}")
	public @ResponseBody ResponseEntity<Events> findEvents(@PathVariable(value="") Long institutionId,
			@RequestParam(value="direction",required=false,defaultValue=ControllerConstants.DIRECTION) String direction,
			@RequestParam(value="page",required=false,defaultValue=ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value="pageSize",required=false,defaultValue=ControllerConstants.PAGE_LENGTH) String pageSize) 
	{
		int pageNumber=0;
		int pageLength=10;
		pageNumber=Integer.parseInt(page);
		pageLength=Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve events from institution "+institutionId+'.');
				
		ResponseEntity<Events> response;
		
		Direction sortDirection=Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection=Direction.ASC;
		EventsReadEvent articleEvent=eventService.readEvents(new ReadAllEvent(institutionId),sortDirection, pageNumber,pageLength);
  	
		if (!articleEvent.isEntityFound())
		{
			response = new ResponseEntity<Events>(HttpStatus.NOT_FOUND);
		}
		
		else
		{
			Iterator<Event> articles = Event.toEventsIterator(articleEvent.getArticles().iterator());
			Events events = Events.fromEventsIterator(articles, articleEvent.getTotalArticles(), articleEvent.getTotalPages());
			response = new ResponseEntity<Events>(events,HttpStatus.OK);
		}

		return response;
	}

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.EVENT_LABEL+"/{eventId}")
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
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.EVENT_LABEL+"/{eventId}")
    public @ResponseBody
    ResponseEntity<Boolean> deleteEvent(@PathVariable Long eventId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete event. " + eventId);
    	ResponseEntity<Boolean> response;
        EventDeletedEvent eventDeletedEvent = eventService.deleteEvent(new DeleteEventEvent(eventId));
    	if (eventDeletedEvent.isDeletionCompleted())
    		response=new ResponseEntity<Boolean>(eventDeletedEvent.isDeletionCompleted(),HttpStatus.OK);
    	else if (eventDeletedEvent.isEntityFound())
    		response=new ResponseEntity<Boolean>(eventDeletedEvent.isDeletionCompleted(),HttpStatus.GONE);
    	else
    		response=new ResponseEntity<Boolean>(eventDeletedEvent.isDeletionCompleted(),HttpStatus.NOT_FOUND);
    	return response;
    }
}
