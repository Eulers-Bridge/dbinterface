package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.elections.*;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 4/09/14.
 */
public interface ElectionService {

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent readElection(RequestReadElectionEvent requestReadElectionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public ElectionCreatedEvent createElection(CreateElectionEvent createElectionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent readPreviousElection(RequestReadElectionEvent requestReadElectionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent readNextElection(RequestReadElectionEvent requestReadElectionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteElection(DeleteElectionEvent deleteElectionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateElection(UpdateElectionEvent updateElectionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
	public ElectionsReadEvent readElections(ReadAllEvent readElectionsEvent,
				Direction sortDirection,int pageNumber, int pageLength);
}
