package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeleteEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.security.SecurityConstants;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface CandidateService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','"+SecurityConstants.ADMIN_ROLE+"')")
    public CreatedEvent createCandidate(CreateCandidateEvent createCandidateEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public ReadEvent requestReadCandidate(RequestReadCandidateEvent requestReadCandidateEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent updateCandidate(UpdateCandidateEvent updateCandidateEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','"+SecurityConstants.ADMIN_ROLE+"')")
    public DeletedEvent deleteCandidate(DeleteCandidateEvent deleteCandidateEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readCandidates(ReadAllEvent readCandidatesEvent,
			Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent addTicket(AddTicketEvent addTicketEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent removeTicket(DeleteEvent deleteEvent);
}
