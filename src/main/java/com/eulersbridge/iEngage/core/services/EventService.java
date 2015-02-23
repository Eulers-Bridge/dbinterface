package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.events.*;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface EventService {

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public EventCreatedEvent createEvent(CreateEventEvent createEventEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent readEvent(RequestReadEventEvent requestReadEventEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateEvent(UpdateEventEvent updateEventEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteEvent(DeleteEventEvent deleteEventEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
	public EventsReadEvent readEvents(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);
}
