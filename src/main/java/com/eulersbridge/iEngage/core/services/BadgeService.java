package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeleteEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.badge.CreateBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.RequestReadBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.UpdateBadgeEvent;
import com.eulersbridge.iEngage.security.SecurityConstants;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface BadgeService
{
    @PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"')")
    public CreatedEvent createBadge(CreateBadgeEvent createBadgeEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public ReadEvent requestReadBadge(RequestReadBadgeEvent requestReadBadgeEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent updateBadge(UpdateBadgeEvent updateBadgeEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"')")
    public DeletedEvent deleteBadge(DeleteEvent deleteBadgeEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readBadges(ReadAllEvent readBadgesEvent,
			Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public UpdatedEvent completedBadge(UpdateEvent any);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readCompletedBadges(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readRemainingBadges(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);
}
