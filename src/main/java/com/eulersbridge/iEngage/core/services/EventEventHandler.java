package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Institution;
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

public class EventEventHandler implements EventService{
    private static Logger LOG = LoggerFactory.getLogger(EventEventHandler.class);

    private EventRepository eventRepository;
    private InstitutionRepository institutionRepository;

    public EventEventHandler(EventRepository eventRepository)
    {
        this.eventRepository = eventRepository;
    }

    public EventEventHandler(EventRepository eventRepository,
			InstitutionRepository institutionRepository)
    {
        this.eventRepository = eventRepository;
        this.institutionRepository = institutionRepository;
	}

	@Override
    public EventCreatedEvent createEvent(CreateEventEvent createEventEvent) {
        EventDetails eventDetails = createEventEvent.getEventDetails();
        Event event = Event.fromEventDetails(eventDetails);
        Long instId=eventDetails.getInstitutionId();

        if (LOG.isDebugEnabled()) LOG.debug("Finding institution with instId = "+instId);
    	Institution inst=institutionRepository.findOne(instId);

    	EventCreatedEvent eventCreatedEvent;
    	if (inst!=null)
    	{
    		event.setInstitution(inst);
    		Event result = eventRepository.save(event);
    		eventCreatedEvent = new EventCreatedEvent(result.getEventId(), result.toEventDetails());
    	}
    	else
    	{
    		eventCreatedEvent=EventCreatedEvent.institutionNotFound(eventDetails.getInstitutionId());
    	}
        return eventCreatedEvent;
    }

    @Override
    public ReadEventEvent readEvent(RequestReadEventEvent requestReadEventEvent) {
        Event event = eventRepository.findOne(requestReadEventEvent.getEventId());
        ReadEventEvent readEventEvent;
        if(event != null)
        {
            readEventEvent = new ReadEventEvent(requestReadEventEvent.getEventId(), event.toEventDetails());
        }
        else
        {
            readEventEvent = ReadEventEvent.notFound(requestReadEventEvent.getEventId());
        }
        return readEventEvent;
    }

    @Override
    public EventUpdatedEvent updateEvent(UpdateEventEvent updateEventEvent) {
        EventDetails eventDetails = updateEventEvent.getEventDetails();
        Event event = Event.fromEventDetails(eventDetails);
        Long eventId = eventDetails.getEventId();
        if(LOG.isDebugEnabled()) LOG.debug("event Id is " + eventId);
        Event eventOld = eventRepository.findOne(eventId);
        if(eventOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("event entity not found " + eventId);
            return EventUpdatedEvent.notFound(eventId);
        }
        else{
            Event result = eventRepository.save(event);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getEventId());
            return new EventUpdatedEvent(result.getEventId(), result.toEventDetails());
        }
    }

    @Override
    public EventDeletedEvent deleteEvent(DeleteEventEvent deleteEventEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteEventEvent= "+deleteEventEvent);
        Long eventId = deleteEventEvent.getEventId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteEvent("+eventId+")");
        Event event = eventRepository.findOne(eventId);
        if(event == null){
            return EventDeletedEvent.notFound(eventId);
        }
        else{
            eventRepository.delete(eventId);
            EventDeletedEvent eventDeletedEvent = new EventDeletedEvent(eventId);
            return eventDeletedEvent;
        }
    }
    
	@Override
	public EventsReadEvent readEvents(ReadEventsEvent readEventsEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Long institutionId=readEventsEvent.getInstId();
		Page <Event>events=null;
		ArrayList<EventDetails> dets=new ArrayList<EventDetails>();
		EventsReadEvent nare=null;

		if (LOG.isDebugEnabled()) LOG.debug("InstitutionId "+institutionId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"e.start");
		events=eventRepository.findByInstitutionId(institutionId, pageable);
		if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+events.getTotalElements()+" total pages ="+events.getTotalPages());
		if (events!=null)
		{
			Iterator<Event> iter=events.iterator();
			while (iter.hasNext())
			{
				Event na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getName());
				EventDetails det=na.toEventDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found instId.
				Institution inst=institutionRepository.findOne(institutionId);
				if ( (null==inst) ||
					 ((null==inst.getName()) || ((null==inst.getCampus()) && (null==inst.getState()) && (null==inst.getCountry()))))
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(InstitutionId)");
					nare=EventsReadEvent.institutionNotFound();
				}
				else
				{	
					nare=new EventsReadEvent(institutionId,dets);
				}
			}
			else
			{	
				nare=new EventsReadEvent(institutionId,dets);
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByInstitutionId");
			nare=EventsReadEvent.institutionNotFound();
		}
		return nare;
	}

}
