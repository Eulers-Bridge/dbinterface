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
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationsReadEvent;

/**
 * @author Greg Newitt
 *
 */
public interface VotingLocationService
{
    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent readVotingLocation(ReadVotingLocationEvent readVotingLocationEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public CreatedEvent createVotingLocation(CreateVotingLocationEvent createVotingLocationEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteVotingLocation(DeleteVotingLocationEvent deleteVotingLocationEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateVotingLocation(UpdateVotingLocationEvent updateVotingLocationEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
	public AllReadEvent findVotingLocations(ReadAllEvent readVotingLocationsEvent,
			Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasRole('ROLE_USER')")
	public VotingLocationsReadEvent findVotingBooths(ReadAllEvent readVotingLocationsEvent,
			Direction sortDirection, int pageNumber, int pageLength);


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public UpdatedEvent addVotingLocationToElection(AddVotingLocationEvent addVotingLocationEvent);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DeletedEvent removeVotingLocationFromElection(RemoveVotingLocationEvent removeVotingLocationEvent);

}
