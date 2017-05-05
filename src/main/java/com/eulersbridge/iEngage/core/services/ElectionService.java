package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.elections.*;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 4/09/14.
 */
public interface ElectionService {

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readElection(RequestReadElectionEvent requestReadElectionEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public ElectionCreatedEvent createElection(CreateElectionEvent createElectionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readPreviousElection(RequestReadElectionEvent requestReadElectionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readNextElection(RequestReadElectionEvent requestReadElectionEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deleteElection(DeleteElectionEvent deleteElectionEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updateElection(UpdateElectionEvent updateElectionEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public AllReadEvent readElections(ReadAllEvent readElectionsEvent,
                                    Direction sortDirection, int pageNumber, int pageLength);

}
