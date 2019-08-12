package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.core.services.interfacePack.EventService;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.*;
import com.eulersbridge.iEngage.rest.domain.EventDomain;
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

  private NodeRepository nodeRepository;
  private EventRepository eventRepository;
  private InstitutionRepository institutionRepository;
  private UserRepository userRepo;
  private PhotoRepository photoRepo;

  @Autowired
  public EventEventHandler(EventRepository eventRepository,
                           InstitutionRepository institutionRepository,
                           UserRepository userRepo,
                           NodeRepository nodeRepository,
                           PhotoRepository photoRepo) {
    this.eventRepository = eventRepository;
    this.institutionRepository = institutionRepository;
    this.userRepo = userRepo;
    this.nodeRepository = nodeRepository;
    this.photoRepo = photoRepo;
  }

  @Override
  public RequestHandledEvent<EventDomain> createEvent(EventDomain eventDomain) {
    eventDomain.setCreated(System.currentTimeMillis());
    if (null == eventDomain.getInstitutionId() || null == eventDomain.getOrganizerEmail())
      return RequestHandledEvent.badRequest();
    NewsFeed nf = institutionRepository.findNewsFeedByInstitutionId(eventDomain.getInstitutionId());
    if (null == nf)
      return RequestHandledEvent.targetNotFound();
    User creator = userRepo.findByEmail(eventDomain.getOrganizerEmail(), 0);
    if (null == creator)
      return RequestHandledEvent.userNotFound();

    Event result = eventRepository.save(Event.fromEventDetails(eventDomain.toEventDetails()), 0);

    final Long eventId = result.getNodeId();
    nodeRepository.createdBy(eventId, creator.getNodeId());
    nodeRepository.hasEvent(eventId, nf.getNodeId());
    if (eventDomain.getPhotos() != null && !eventDomain.getPhotos().isEmpty()) {
      eventDomain.getPhotos().forEach(photoDomain -> {
        Photo p = null;
        if (photoDomain.getNodeId() != null)
          p = photoRepo.findById(photoDomain.getNodeId(), 0).orElse(null);
        if (p == null){
          photoDomain.setDate(System.currentTimeMillis());
          p = photoRepo.save(Photo.fromPhotoDetails(photoDomain.toPhotoDetails()), 0);
        }
        if (p != null && p.getNodeId() != null && eventId != null)
          photoRepo.setOwner(p.getNodeId(), eventId);
      });
    }

    result = eventRepository.findById(result.getNodeId()).orElse(null);
    if (result == null)
      return RequestHandledEvent.failed();

    return  new RequestHandledEvent<>(EventDomain.fromEventDetails(result.toEventDetails()));
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
