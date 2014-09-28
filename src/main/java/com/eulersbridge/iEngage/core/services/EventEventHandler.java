package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.events.*;
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
        return null;
    }

    @Override
    public ReadEventEvent requestReadEvent(RequestReadEventEvent requestReadEventEvent) {
        return null;
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
