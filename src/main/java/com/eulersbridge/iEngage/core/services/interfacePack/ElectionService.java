package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.elections.*;
import com.eulersbridge.iEngage.rest.domain.ElectionDomain;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 4/09/14.
 */
public interface ElectionService {

  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public RequestHandledEvent readElection(Long electionId);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public RequestHandledEvent createElection(ElectionDomain electionDomain);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readPreviousElection(RequestReadElectionEvent requestReadElectionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readNextElection(RequestReadElectionEvent requestReadElectionEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deleteElection(DeleteElectionEvent deleteElectionEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updateElection(UpdateElectionEvent updateElectionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent readElections(Long institutionID, int pageIndex, int pageSize);

}
