package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.core.services.interfacePack.EventService;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.EventRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yikai Gong
 */
@Service
public class EventEventHandler implements EventService {
  private static Logger LOG = LoggerFactory
    .getLogger(EventEventHandler.class);

  private EventRepository eventRepository;
  private InstitutionRepository institutionRepository;
  private UserRepository userRepo;

  @Autowired
  public EventEventHandler(EventRepository eventRepository,
                           InstitutionRepository institutionRepository,
                           UserRepository userRepo) {
    this.eventRepository = eventRepository;
    this.institutionRepository = institutionRepository;
    this.userRepo = userRepo;
  }

  @Override
  public EventCreatedEvent createEvent(CreateEventEvent createEventEvent) {
    EventDetails eventDetails = (EventDetails) createEventEvent.getDetails();
    Event event = Event.fromEventDetails(eventDetails);
    Long instId = eventDetails.getInstitutionId();

    if (LOG.isDebugEnabled())
      LOG.debug("Finding institution with instId = " + instId);
    Institution inst = institutionRepository.findById(instId, 0).get();
    NewsFeed nf = institutionRepository.findNewsFeedByInstitutionId(instId);
    User creator = userRepo.findByEmail(eventDetails.getOrganizerEmail(), 0);
    if (creator == null)
      return EventCreatedEvent.creatorNotFound(eventDetails.getEventId());

    if (LOG.isDebugEnabled()) LOG.debug("news feed - " + nf);

    EventCreatedEvent eventCreatedEvent;
    if ((inst != null) && (nf != null)) {
      event.setNewsFeed(nf.toNode());
      event.setCreator(creator.toNode());
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
    Event event = eventRepository.findById(requestReadEventEvent
      .getNodeId()).get();
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

    Event eventOld = eventRepository.findById(eventId).get();
    if (eventOld == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("event entity not found " + eventId);
      return EventUpdatedEvent.notFound(eventId);
    } else {
      event.setCreator(eventOld.getCreator());
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
    Event event = eventRepository.findById(eventId).get();
    if (event == null) {
      return EventDeletedEvent.notFound(eventId);
    } else {
      eventRepository.deleteById(eventId);
      return new EventDeletedEvent(eventId);
    }
  }

  @Override
  public AllReadEvent readEvents(ReadAllEvent readAllEvent,
                                 Direction sortDirection, int pageNumber, int pageLength) {
    Long institutionId = readAllEvent.getParentId();
    List<Event> events;
    ArrayList<EventDetails> dets = new ArrayList<>();
    AllReadEvent nare;

    if (LOG.isDebugEnabled()) LOG.debug("InstitutionId " + institutionId);
    Pageable pageable = PageRequest.of(pageNumber, pageLength,
      sortDirection, "e.starts");
//    events = eventRepository.findByInstitutionId(institutionId, pageable);
    events = eventRepository.findByInstitutionId(institutionId, pageNumber * pageLength, pageLength, "DESC");
    if (events != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + events.size()
          + " total pages =" + events.size());
      for (Event na : events) {
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getName());
        EventDetails det = na.toEventDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        Institution inst = institutionRepository.findById(institutionId).get();
        if ((null == inst)
          || ((null == inst.getName()) || ((null == inst
          .getCampus()) && (null == inst.getState()) && (null == inst
          .getCountry())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(InstitutionId)");
          nare = AllReadEvent.notFound(null);
        } else {
          nare = new AllReadEvent(institutionId, dets,
            new Integer(events.size()).longValue(), -1);
        }
      } else {
        nare = new AllReadEvent(institutionId, dets,
          new Integer(events.size()).longValue(), -1);
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(null);
    }
    return nare;
  }

}
