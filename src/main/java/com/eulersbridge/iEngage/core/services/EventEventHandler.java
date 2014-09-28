package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class EventEventHandler implements EventService{
    private static Logger LOG = LoggerFactory.getLogger(EventEventHandler.class);

    private EventRepository eventRepository;

    public EventEventHandler(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @Override
    public EventCreatedEvent createEvent(CreateEventEvent createEventEvent) {
        EventDetails eventDetails = createEventEvent.getEventDetails();
        Event event = Event.fromEventDetails(eventDetails);
        Event result = eventRepository.save(event);
        EventCreatedEvent eventCreatedEvent = new EventCreatedEvent(result.getEventId(), result.toEventDetails());
        return eventCreatedEvent;
    }

    @Override
    public ReadEventEvent requestReadEvent(RequestReadEventEvent requestReadEventEvent) {
        Event event = eventRepository.findOne(requestReadEventEvent.getEventId());
        ReadEventEvent readEventEvent;
        if(event != null){
            readEventEvent = new ReadEventEvent(requestReadEventEvent.getEventId(), event.toEventDetails());
        }
        else{
            readEventEvent = ReadEventEvent.notFound(requestReadEventEvent.getEventId());
        }
        return readEventEvent;
    }

    @Override
    public EventUpdatedEvent updateEvent(UpdateEventEvent updateEventEvent) {
        return null;
    }

    @Override
    public EventDeletedEvent deleteEvent(DeleteEventEvent deleteEventEvent) {
        return null;
    }
}
