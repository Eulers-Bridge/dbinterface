/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.AddVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.DeleteVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.RemoveVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.UpdateVotingLocationEvent;
import com.eulersbridge.iEngage.security.SecurityConstants;

/**
 * @author Greg Newitt
 *
 */
public interface VotingLocationService
{
    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public ReadEvent readVotingLocation(ReadVotingLocationEvent readVotingLocationEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.RETURNING_OFFICER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public CreatedEvent createVotingLocation(CreateVotingLocationEvent createVotingLocationEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.RETURNING_OFFICER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public DeletedEvent deleteVotingLocation(DeleteVotingLocationEvent deleteVotingLocationEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.RETURNING_OFFICER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent updateVotingLocation(UpdateVotingLocationEvent updateVotingLocationEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent findVotingLocations(ReadAllEvent readVotingLocationsEvent,
			Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent findVotingBooths(ReadAllEvent readVotingLocationsEvent,
			Direction sortDirection, int pageNumber, int pageLength);


    @PreAuthorize("hasAnyRole('"+SecurityConstants.RETURNING_OFFICER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent addVotingLocationToElection(AddVotingLocationEvent addVotingLocationEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.RETURNING_OFFICER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public DeletedEvent removeVotingLocationFromElection(RemoveVotingLocationEvent removeVotingLocationEvent);

}
