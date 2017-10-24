package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.services.interfacePack.EventService;
import com.eulersbridge.iEngage.core.services.interfacePack.LikesService;
import com.eulersbridge.iEngage.rest.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class EventController {

  @Autowired
  EventService eventService;

  @Autowired
  LikesService likesService;

  public EventController() {
  }

  private static Logger LOG = LoggerFactory.getLogger(EventController.class);

  // Create
  @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.EVENT_LABEL)
  public @ResponseBody
  ResponseEntity<Event> createEvent(
    @RequestBody Event event) {
    if (LOG.isInfoEnabled())
      LOG.info("attempting to create event " + event);
    CreateEventEvent createEventEvent = new CreateEventEvent(
      event.toEventDetails());
    EventCreatedEvent eventCreatedEvent = eventService
      .createEvent(createEventEvent);
    ResponseEntity<Event> response;
    if ((null == eventCreatedEvent)
      || (eventCreatedEvent.getEventId() == null)) {
      response = new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
    } else if ((!eventCreatedEvent.isInstitutionFound()) || (!eventCreatedEvent.isCreatorFound())) {
      response = new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
    } else {
      Event result = Event.fromEventDetails((EventDetails) eventCreatedEvent
        .getDetails());
      if (LOG.isDebugEnabled()) LOG.debug("event" + result.toString());
      response = new ResponseEntity<Event>(result, HttpStatus.CREATED);
    }
    return response;
  }

  // Get
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}")
  public @ResponseBody
  ResponseEntity<Event> findEvent(
    @PathVariable Long eventId) {
    if (LOG.isInfoEnabled())
      LOG.info(eventId + " attempting to get event. ");
    RequestReadEventEvent requestReadEventEvent = new RequestReadEventEvent(
      eventId);
    ReadEvent readEventEvent = eventService
      .readEvent(requestReadEventEvent);
    if (readEventEvent.isEntityFound()) {
      Event event = Event.fromEventDetails((EventDetails) readEventEvent.getDetails());
      return new ResponseEntity<Event>(event, HttpStatus.OK);
    } else {
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
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.EVENTS_LABEL
    + "/{institutionId}")
  public @ResponseBody
  ResponseEntity<WrappedDomainList> findEvents(
    @PathVariable(value = "") Long institutionId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve events from institution "
        + institutionId + '.');

    ResponseEntity<WrappedDomainList> response;

    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    AllReadEvent evtEvent = eventService.readEvents(new ReadAllEvent(
      institutionId), sortDirection, pageNumber, pageLength);

    if (!evtEvent.isEntityFound()) {
      response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<Event> evts = Event.toEventsIterator(evtEvent.getDetails()
        .iterator());
      WrappedDomainList events = WrappedDomainList.fromIterator(evts,
        evtEvent.getTotalItems(), evtEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(events, HttpStatus.OK);
    }

    return response;
  }

  // Update
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}")
  public @ResponseBody
  ResponseEntity<Event> updateEvent(
    @PathVariable Long eventId, @RequestBody Event event) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to update event. " + eventId);
    UpdatedEvent eventUpdatedEvent = eventService
      .updateEvent(new UpdateEventEvent(eventId, event
        .toEventDetails()));
    if ((null != eventUpdatedEvent)) {
      if (LOG.isDebugEnabled())
        LOG.debug("eventUpdatedEvent - " + eventUpdatedEvent);
      if (eventUpdatedEvent.isEntityFound()) {
        Event resultEvent = Event.fromEventDetails((EventDetails) eventUpdatedEvent
          .getDetails());
        if (LOG.isDebugEnabled())
          LOG.debug("resultEvent = " + resultEvent);
        return new ResponseEntity<Event>(resultEvent, HttpStatus.OK);
      } else {
        return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
    }
  }

  // Delete
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}")
  public @ResponseBody
  ResponseEntity<Response> deleteEvent(
    @PathVariable Long eventId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to delete event. " + eventId);
    ResponseEntity<Response> response;
    DeletedEvent eventDeletedEvent = eventService
      .deleteEvent(new DeleteEventEvent(eventId));
    Response restEvent;
    if (!eventDeletedEvent.isEntityFound()) {
      restEvent = Response.failed("Not found");
      response = new ResponseEntity<Response>(restEvent, HttpStatus.NOT_FOUND);
    } else {
      if (eventDeletedEvent.isDeletionCompleted()) {
        restEvent = new Response();
        response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
      } else {
        restEvent = Response.failed("Could not delete");
        response = new ResponseEntity<Response>(restEvent, HttpStatus.GONE);
      }
    }
    return response;
  }

  /**
   * Is passed all the necessary data to unlike an event from the database.
   * The request must be a PUT with the event id presented along with the
   * userid as the final portion of the URL.
   * <p/>
   * This method will return the a boolean result.
   *
   * @param email the eventId eventId of the event object to be unliked.
   * @param email the email address of the user unliking the event.
   * @return the success or failure.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}" + ControllerConstants.LIKED_BY_LABEL + "/{email}/")
  public @ResponseBody
  ResponseEntity<Response> unlikeEvent(
    @PathVariable Long eventId, @PathVariable String email) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to have " + email + " unlike news article. "
        + eventId);
    LikedEvent event = likesService.unlike(new LikeEvent(eventId,
      email));

    ResponseEntity<Response> response;

    if (!event.isEntityFound()) {
      response = new ResponseEntity<Response>(HttpStatus.GONE);
    } else if (!event.isUserFound()) {
      response = new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
    } else {
      Response restEvent;
      if (event.isResultSuccess())
        restEvent = new Response();
      else
        restEvent = Response.failed("Could not unlike.");
      response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Is passed all the necessary data to like an event from the database. The
   * request must be a PUT with the event id presented along with the userid
   * as the final portion of the URL.
   * <p/>
   * This method will return the a boolean result.
   *
   * @param email the eventId eventId of the event object to be liked.
   * @param email the email address of the user liking the event.
   * @return the success or failure.
   */
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}" + ControllerConstants.LIKED_BY_LABEL + "/{email}/")
  public @ResponseBody
  ResponseEntity<Response> likeEvent(
    @PathVariable Long eventId, @PathVariable String email) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to have " + email + " like news article. "
        + eventId);
    LikedEvent event = likesService
      .like(new LikeEvent(eventId, email, Event.class));

    ResponseEntity<Response> response;

    if (!event.isEntityFound()) {
      response = new ResponseEntity<Response>(HttpStatus.GONE);
    } else if (!event.isUserFound()) {
      response = new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
    } else {
      Response restEvent;
      if (event.isResultSuccess())
        restEvent = new Response();
      else
        restEvent = Response.failed("Could not like.");
      response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
    }
    return response;
  }

  // likes
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}" + ControllerConstants.LIKES_LABEL)
  public @ResponseBody
  ResponseEntity<Iterator<LikeInfo>> findLikes(
    @PathVariable Long eventId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve liked users from event " + eventId
        + '.');
    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(
      new LikesLikeableObjectEvent(eventId), sortDirection,
      pageNumber, pageLength);
    Iterator<LikeInfo> likes = User
      .toLikesIterator(likeableObjectLikesEvent.getUserDetails()
        .iterator());
    if (likes.hasNext() == false) {
      ReadEvent readPollEvent = eventService
        .readEvent(new RequestReadEventEvent(eventId));
      if (!readPollEvent.isEntityFound())
        return new ResponseEntity<Iterator<LikeInfo>>(
          HttpStatus.NOT_FOUND);
      else return new ResponseEntity<Iterator<LikeInfo>>(likes,
        HttpStatus.OK);
    } else return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
  }
}
