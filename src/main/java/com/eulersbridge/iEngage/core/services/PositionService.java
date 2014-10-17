package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.positions.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 8/10/14.
 */
public interface PositionService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public PositionCreatedEvent createPosition(CreatePositionEvent createPositionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadPositionEvent requestReadPosition(RequestReadPositionEvent requestReadPositionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public PositionUpdatedEvent updatePosition(UpdatePositionEvent updatePositionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public PositionDeletedEvent deletePosition(DeletePositionEvent deletePositionEvent);
}