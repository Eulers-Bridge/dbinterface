package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.rest.domain.CandidateDomain;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface CandidateService {
  @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','" + SecurityConstants.ADMIN_ROLE + "')")
  public RequestHandledEvent<CandidateDomain> createCandidate(CreateCandidateEvent createCandidateEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent requestReadCandidate(RequestReadCandidateEvent requestReadCandidateEvent);

  @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updateCandidate(UpdateCandidateEvent updateCandidateEvent);

  @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deleteCandidate(DeleteCandidateEvent deleteCandidateEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public AllReadEvent readCandidates(ReadAllEvent readCandidatesEvent,
                                     Direction sortDirection, int pageNumber, int pageLength);

  @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent addTicket(AddTicketEvent addTicketEvent);

  @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent removeTicket(DeleteEvent deleteEvent);
}
