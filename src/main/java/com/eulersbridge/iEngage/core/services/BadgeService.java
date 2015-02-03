package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.badge.BadgesReadEvent;
import com.eulersbridge.iEngage.core.events.badge.CreateBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.DeleteBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.RequestReadBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.UpdateBadgeEvent;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface BadgeService
{
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public CreatedEvent createBadge(CreateBadgeEvent createBadgeEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadBadge(RequestReadBadgeEvent requestReadBadgeEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateBadge(UpdateBadgeEvent updateBadgeEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteBadge(DeleteBadgeEvent deleteBadgeEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
	public BadgesReadEvent readBadges(ReadAllEvent readBadgesEvent,
			Direction sortDirection, int pageNumber, int pageLength);
}
