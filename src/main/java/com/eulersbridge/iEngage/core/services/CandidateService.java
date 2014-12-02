package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface CandidateService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public CandidateCreatedEvent createCandidate(CreateCandidateEvent createCandidateEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadCandidate(RequestReadCandidateEvent requestReadCandidateEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateCandidate(UpdateCandidateEvent updateCandidateEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteCandidate(DeleteCandidateEvent deleteCandidateEvent);
}
