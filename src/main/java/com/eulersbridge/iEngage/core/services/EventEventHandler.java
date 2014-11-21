package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Like;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import com.eulersbridge.iEngage.database.repository.EventRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author Yikai Gong
 */

public class EventEventHandler implements EventService
{
	private static Logger LOG = LoggerFactory
			.getLogger(EventEventHandler.class);

	private EventRepository eventRepository;
	private InstitutionRepository institutionRepository;

	public EventEventHandler(EventRepository eventRepository,
			InstitutionRepository institutionRepository)
	{
		this.eventRepository = eventRepository;
		this.institutionRepository = institutionRepository;
	}

	@Override
	public EventCreatedEvent createEvent(CreateEventEvent createEventEvent)
	{
		EventDetails eventDetails = createEventEvent.getEventDetails();
		Event event = Event.fromEventDetails(eventDetails);
		Long instId = eventDetails.getInstitutionId();

		if (LOG.isDebugEnabled())
			LOG.debug("Finding institution with instId = " + instId);
		Institution inst = institutionRepository.findOne(instId);
		NewsFeed nf = institutionRepository.findNewsFeed(instId);
		if (LOG.isDebugEnabled()) LOG.debug("news feed - " + nf);

		EventCreatedEvent eventCreatedEvent;
		if ((inst != null) && (nf != null))
		{
			event.setNewsFeed(nf);
			Event result = eventRepository.save(event);
			eventCreatedEvent = new EventCreatedEvent(result.getEventId(),
					result.toEventDetails());
		}
		else
		{
			eventCreatedEvent = EventCreatedEvent
					.institutionNotFound(eventDetails.getInstitutionId());
		}
		return eventCreatedEvent;
	}

	@Override
	public ReadEvent readEvent(RequestReadEventEvent requestReadEventEvent)
	{
		Event event = eventRepository.findOne(requestReadEventEvent
				.getNodeId());
		ReadEvent readEventEvent;
		if (event != null)
		{
			readEventEvent = new ReadEventEvent(
					requestReadEventEvent.getNodeId(), event.toEventDetails());
		}
		else
		{
			readEventEvent = ReadEventEvent.notFound(requestReadEventEvent
					.getNodeId());
		}
		return readEventEvent;
	}

	@Override
	public EventUpdatedEvent updateEvent(UpdateEventEvent updateEventEvent)
	{
		EventDetails eventDetails = updateEventEvent.getEventDetails();
		Event event = Event.fromEventDetails(eventDetails);
		Long eventId = eventDetails.getEventId();
		if (LOG.isDebugEnabled())
			LOG.debug("Finding institution with id = "
					+ eventDetails.getInstitutionId());
		NewsFeed nf = institutionRepository.findNewsFeed(eventDetails
				.getInstitutionId());
		event.setNewsFeed(nf);
		if (LOG.isDebugEnabled())
			LOG.debug("news feed - " + nf + ",event Id is " + eventId);

		Event eventOld = eventRepository.findOne(eventId);
		if (eventOld == null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("event entity not found " + eventId);
			return EventUpdatedEvent.notFound(eventId);
		}
		else
		{
			Event result = eventRepository.save(event);
			if (LOG.isDebugEnabled())
				LOG.debug("updated successfully" + result.getEventId());
			return new EventUpdatedEvent(result.getEventId(),
					result.toEventDetails());
		}
	}

	@Override
	public DeletedEvent deleteEvent(DeleteEventEvent deleteEventEvent)
	{
		if (LOG.isDebugEnabled())
			LOG.debug("Entered deleteEventEvent= " + deleteEventEvent);
		Long eventId = deleteEventEvent.getNodeId();
		if (LOG.isDebugEnabled()) LOG.debug("deleteEvent(" + eventId + ")");
		Event event = eventRepository.findOne(eventId);
		if (event == null)
		{
			return EventDeletedEvent.notFound(eventId);
		}
		else
		{
			eventRepository.delete(eventId);
			EventDeletedEvent eventDeletedEvent = new EventDeletedEvent(eventId);
			return eventDeletedEvent;
		}
	}

	@Override
	public EventsReadEvent readEvents(ReadAllEvent readAllEvent,
			Direction sortDirection, int pageNumber, int pageLength)
	{
		Long institutionId = readAllEvent.getInstId();
		Page<Event> events = null;
		ArrayList<EventDetails> dets = new ArrayList<EventDetails>();
		EventsReadEvent nare = null;

		if (LOG.isDebugEnabled()) LOG.debug("InstitutionId " + institutionId);
		Pageable pageable = new PageRequest(pageNumber, pageLength,
				sortDirection, "e.start");
		events = eventRepository.findByInstitutionId(institutionId, pageable);
		if (events != null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = " + events.getTotalElements()
						+ " total pages =" + events.getTotalPages());
			Iterator<Event> iter = events.iterator();
			while (iter.hasNext())
			{
				Event na = iter.next();
				if (LOG.isTraceEnabled())
					LOG.trace("Converting to details - " + na.getName());
				EventDetails det = na.toEventDetails();
				dets.add(det);
			}
			if (0 == dets.size())
			{
				// Need to check if we actually found instId.
				Institution inst = institutionRepository.findOne(institutionId);
				if ((null == inst)
						|| ((null == inst.getName()) || ((null == inst
								.getCampus()) && (null == inst.getState()) && (null == inst
								.getCountry()))))
				{
					if (LOG.isDebugEnabled())
						LOG.debug("Null or null properties returned by findOne(InstitutionId)");
					nare = EventsReadEvent.institutionNotFound();
				}
				else
				{
					nare = new EventsReadEvent(institutionId, dets,
							events.getTotalElements(), events.getTotalPages());
				}
			}
			else
			{
				nare = new EventsReadEvent(institutionId, dets,
						events.getTotalElements(), events.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Null returned by findByInstitutionId");
			nare = EventsReadEvent.institutionNotFound();
		}
		return nare;
	}

	@Override
	public LikedEvent likeEvent(LikeEvent likeEvent)
	{
		boolean result = true;
		LikedEvent retValue;
		String email = likeEvent.getEmailAddress();
		Long eventId = likeEvent.getNodeId();
		Like like = eventRepository.likeEvent(email, eventId);

		if (like != null)
			result = true;
		else result = false;
		retValue = new LikedEvent(eventId, email, result);
		return retValue;
	}

	@Override
	public LikedEvent unlikeEvent(LikeEvent unlikeEventEvent)
	{
		boolean result = true;
		LikedEvent retValue;
		String email = unlikeEventEvent.getEmailAddress();
		Long eventId = unlikeEventEvent.getNodeId();

		eventRepository.unlikeEvent(email, eventId);

		retValue = new LikedEvent(eventId, email, result);
		return retValue;
	}

}
