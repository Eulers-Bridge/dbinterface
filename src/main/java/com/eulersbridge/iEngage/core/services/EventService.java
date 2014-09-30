package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.events.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface EventService {

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public EventCreatedEvent createEvent(CreateEventEvent createEventEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEventEvent requestReadEvent(RequestReadEventEvent requestReadEventEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public EventUpdatedEvent updateEvent(UpdateEventEvent updateEventEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public EventDeletedEvent deleteEvent(DeleteEventEvent deleteEventEvent);
}