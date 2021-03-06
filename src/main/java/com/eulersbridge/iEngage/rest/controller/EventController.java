package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.beans.Util;
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
  ResponseEntity<EventDomain> createEvent(@RequestBody EventDomain eventDomain) {
    if (LOG.isInfoEnabled())
      LOG.info("attempting to create eventDomain " + eventDomain);
    String userEmail = Util.getUserEmailFromSession();
    eventDomain.setOrganizerEmail(userEmail);

    RequestHandledEvent<EventDomain> requestHandledEvent = eventService.createEvent(eventDomain);

    return requestHandledEvent.toResponseEntity();
  }

  // Get
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}")
  public @ResponseBody
  ResponseEntity<EventDomain> findEvent(
    @PathVariable Long eventId) {
    if (LOG.isInfoEnabled())
      LOG.info(eventId + " attempting to get eventDomain. ");
    RequestReadEventEvent requestReadEventEvent = new RequestReadEventEvent(
      eventId);
    ReadEvent readEventEvent = eventService
      .readEvent(requestReadEventEvent);
    if (readEventEvent.isEntityFound()) {
      EventDomain event = EventDomain.fromEventDetails((EventDetails) readEventEvent.getDetails());
      return new ResponseEntity<EventDomain>(event, HttpStatus.OK);
    } else {
      return new ResponseEntity<EventDomain>(HttpStatus.NOT_FOUND);
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
      Iterator<EventDomain> evts = EventDomain.toEventsIterator(evtEvent.getDetails()
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
  ResponseEntity<EventDomain> updateEvent(
    @PathVariable Long eventId, @RequestBody EventDomain event) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to update eventDomain. " + eventId);
    UpdatedEvent eventUpdatedEvent = eventService
      .updateEvent(new UpdateEventEvent(eventId, event
        .toEventDetails()));
    if ((null != eventUpdatedEvent)) {
      if (LOG.isDebugEnabled())
        LOG.debug("eventUpdatedEvent - " + eventUpdatedEvent);
      if (eventUpdatedEvent.isEntityFound()) {
        EventDomain resultEvent = EventDomain.fromEventDetails((EventDetails) eventUpdatedEvent
          .getDetails());
        if (LOG.isDebugEnabled())
          LOG.debug("resultEvent = " + resultEvent);
        return new ResponseEntity<EventDomain>(resultEvent, HttpStatus.OK);
      } else {
        return new ResponseEntity<EventDomain>(HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<EventDomain>(HttpStatus.BAD_REQUEST);
    }
  }

  // Delete
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}")
  public @ResponseBody
  ResponseEntity<Response> deleteEvent(
    @PathVariable Long eventId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to delete eventDomain. " + eventId);
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
   * Is passed all the necessary data to unlike an eventDomain from the database.
   * The request must be a PUT with the eventDomain id presented along with the
   * userid as the final portion of the URL.
   * <p/>
   * This method will return the a boolean result.
   *
   * @param email the eventId eventId of the eventDomain object to be unliked.
   * @param email the email address of the user unliking the eventDomain.
   * @return the success or failure.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.EVENT_LABEL
    + "/{eventId}" + ControllerConstants.LIKED_BY_LABEL + "/{email}/")
  public @ResponseBody
  ResponseEntity unlikeEvent(
    @PathVariable Long eventId, @PathVariable String email) {
    email = Util.getUserEmailFromSession();
    RequestHandledEvent result = likesService.unlike(email, eventId);
    return result.toResponseEntity();
  }

  /**
   * Is passed all the necessary data to like an eventDomain from the database. The
   * request must be a PUT with the eventDomain id presented along with the userid
   * as the final portion of the URL.
   * <p/>
   * This method will return the a boolean result.
   *
   * @param email the eventId eventId of the eventDomain object to be liked.
   * @param email the email address of the user liking the eventDomain.
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
      .like(new LikeEvent(eventId, email, EventDomain.class));

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
      LOG.info("Attempting to retrieve liked users from eventDomain " + eventId
        + '.');
    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(
      new LikesLikeableObjectEvent(eventId), sortDirection,
      pageNumber, pageLength);
    Iterator<LikeInfo> likes = UserDomain
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
