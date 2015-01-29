package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.positions.*;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 8/10/14.
 */
public interface PositionService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public CreatedEvent createPosition(CreatePositionEvent createPositionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent readPosition(RequestReadPositionEvent requestReadPositionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updatePosition(UpdatePositionEvent updatePositionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deletePosition(DeletePositionEvent deletePositionEvent);
}
