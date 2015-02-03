package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.CandidatesReadEvent;
import com.eulersbridge.iEngage.core.events.candidate.CreateCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.DeleteCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.RequestReadCandidateEvent;
import com.eulersbridge.iEngage.core.events.candidate.UpdateCandidateEvent;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface CandidateService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public CreatedEvent createCandidate(CreateCandidateEvent createCandidateEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadCandidate(RequestReadCandidateEvent requestReadCandidateEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateCandidate(UpdateCandidateEvent updateCandidateEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteCandidate(DeleteCandidateEvent deleteCandidateEvent);


    @PreAuthorize("hasRole('ROLE_USER')")
	public CandidatesReadEvent readCandidates(ReadAllEvent readCandidatesEvent,
			Direction sortDirection, int pageNumber, int pageLength);
}
