package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import com.eulersbridge.iEngage.database.repository.EventRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;

/**
 * @author Yikai Gong
 */

public class EventEventHandler implements EventService {
  private static Logger LOG = LoggerFactory
    .getLogger(EventEventHandler.class);

  private EventRepository eventRepository;
  private InstitutionRepository institutionRepository;

  public EventEventHandler(EventRepository eventRepository,
                           InstitutionRepository institutionRepository) {
    this.eventRepository = eventRepository;
    this.institutionRepository = institutionRepository;
  }

  @Override
  public EventCreatedEvent createEvent(CreateEventEvent createEventEvent) {
    EventDetails eventDetails = (EventDetails) createEventEvent.getDetails();
    Event event = Event.fromEventDetails(eventDetails);
    Long instId = eventDetails.getInstitutionId();

    if (LOG.isDebugEnabled())
      LOG.debug("Finding institution with instId = " + instId);
    Institution inst = institutionRepository.findOne(instId, 0);
    NewsFeed nf = institutionRepository.findNewsFeedByInstitutionId(instId);
    if (LOG.isDebugEnabled()) LOG.debug("news feed - " + nf);

    EventCreatedEvent eventCreatedEvent;
    if ((inst != null) && (nf != null)) {
      event.setNewsFeed(nf.toNode());
      Event result = eventRepository.save(event);
      eventCreatedEvent = new EventCreatedEvent(result.getNodeId(),
        result.toEventDetails());
    } else {
      eventCreatedEvent = EventCreatedEvent
        .institutionNotFound(eventDetails.getInstitutionId());
    }
    return eventCreatedEvent;
  }

  @Override
  public ReadEvent readEvent(RequestReadEventEvent requestReadEventEvent) {
    Event event = eventRepository.findOne(requestReadEventEvent
      .getNodeId());
    ReadEvent readEventEvent;
    if (event != null) {
      readEventEvent = new ReadEventEvent(
        requestReadEventEvent.getNodeId(), event.toEventDetails());
    } else {
      readEventEvent = ReadEventEvent.notFound(requestReadEventEvent
        .getNodeId());
    }
    return readEventEvent;
  }

  @Override
  public UpdatedEvent updateEvent(UpdateEventEvent updateEventEvent) {
    EventDetails eventDetails = (EventDetails) updateEventEvent.getDetails();
    Event event = Event.fromEventDetails(eventDetails);
    Long eventId = eventDetails.getEventId();
    if (LOG.isDebugEnabled())
      LOG.debug("Finding institution with id = "
        + eventDetails.getInstitutionId());
    NewsFeed nf = institutionRepository.findNewsFeedByInstitutionId(eventDetails
      .getInstitutionId());
    if (nf != null)
      event.setNewsFeed(nf.toNode());
    if (LOG.isDebugEnabled())
      LOG.debug("news feed - " + nf + ",event Id is " + eventId);

    Event eventOld = eventRepository.findOne(eventId);
    if (eventOld == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("event entity not found " + eventId);
      return EventUpdatedEvent.notFound(eventId);
    } else {
      Event result = eventRepository.save(event, 0);
      if (LOG.isDebugEnabled())
        LOG.debug("updated successfully" + result.getNodeId());
      return new EventUpdatedEvent(result.getNodeId(),
        result.toEventDetails());
    }
  }

  @Override
  public DeletedEvent deleteEvent(DeleteEventEvent deleteEventEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deleteEventEvent= " + deleteEventEvent);
    Long eventId = deleteEventEvent.getNodeId();
    if (LOG.isDebugEnabled()) LOG.debug("deleteEvent(" + eventId + ")");
    Event event = eventRepository.findOne(eventId);
    if (event == null) {
      return EventDeletedEvent.notFound(eventId);
    } else {
      eventRepository.delete(eventId);
      return new EventDeletedEvent(eventId);
    }
  }

  @Override
  public AllReadEvent readEvents(ReadAllEvent readAllEvent,
                                 Direction sortDirection, int pageNumber, int pageLength) {
    Long institutionId = readAllEvent.getParentId();
    Page<Event> events;
    ArrayList<EventDetails> dets = new ArrayList<>();
    AllReadEvent nare;

    if (LOG.isDebugEnabled()) LOG.debug("InstitutionId " + institutionId);
    Pageable pageable = new PageRequest(pageNumber, pageLength,
      sortDirection, "e.starts");
    events = eventRepository.findByInstitutionId(institutionId, pageable);
    if (events != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + events.getTotalElements()
          + " total pages =" + events.getTotalPages());
      for (Event na : events) {
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getName());
        EventDetails det = na.toEventDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        Institution inst = institutionRepository.findOne(institutionId);
        if ((null == inst)
          || ((null == inst.getName()) || ((null == inst
          .getCampus()) && (null == inst.getState()) && (null == inst
          .getCountry())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(InstitutionId)");
          nare = AllReadEvent.notFound(null);
        } else {
          nare = new AllReadEvent(institutionId, dets,
            events.getTotalElements(), events.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(institutionId, dets,
          events.getTotalElements(), events.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(null);
    }
    return nare;
  }

}
