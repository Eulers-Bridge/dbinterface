package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.badge.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface BadgeService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public BadgeCreatedEvent createBadge(CreateBadgeEvent createBadgeEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadBadge(RequestReadBadgeEvent requestReadBadgeEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateBadge(UpdateBadgeEvent updateBadgeEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteBadge(DeleteBadgeEvent deleteBadgeEvent);

    //TODO ReadALL
}
