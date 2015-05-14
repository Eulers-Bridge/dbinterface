package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent;
import com.eulersbridge.iEngage.core.events.positions.DeletePositionEvent;
import com.eulersbridge.iEngage.core.events.positions.RequestReadPositionEvent;
import com.eulersbridge.iEngage.core.events.positions.UpdatePositionEvent;
import com.eulersbridge.iEngage.security.SecurityConstants;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 8/10/14.
 */
public interface PositionService {
    @PreAuthorize("hasAnyRole('"+SecurityConstants.RETURNING_OFFICER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public CreatedEvent createPosition(CreatePositionEvent createPositionEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public ReadEvent readPosition(RequestReadPositionEvent requestReadPositionEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.RETURNING_OFFICER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent updatePosition(UpdatePositionEvent updatePositionEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.RETURNING_OFFICER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public DeletedEvent deletePosition(DeletePositionEvent deletePositionEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readPositions(ReadAllEvent readPositionsEvent,
			Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readCandidates(ReadAllEvent readAllEvent,
			Direction sortDirection, int pageNumber, int pageLength);
}
