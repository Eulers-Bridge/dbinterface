/**
 *
 */
package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.votingLocation.*;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Greg Newitt
 */
public interface VotingLocationService {
  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readVotingLocation(ReadVotingLocationEvent readVotingLocationEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public CreatedEvent createVotingLocation(CreateVotingLocationEvent createVotingLocationEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deleteVotingLocation(DeleteVotingLocationEvent deleteVotingLocationEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updateVotingLocation(UpdateVotingLocationEvent updateVotingLocationEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public AllReadEvent findVotingLocations(ReadAllEvent readVotingLocationsEvent,
                                          Direction sortDirection, int pageNumber, int pageLength);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public AllReadEvent findVotingBooths(ReadAllEvent readVotingLocationsEvent,
                                       Direction sortDirection, int pageNumber, int pageLength);


  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent addVotingLocationToElection(AddVotingLocationEvent addVotingLocationEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.RETURNING_OFFICER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent removeVotingLocationFromElection(RemoveVotingLocationEvent removeVotingLocationEvent);

}
