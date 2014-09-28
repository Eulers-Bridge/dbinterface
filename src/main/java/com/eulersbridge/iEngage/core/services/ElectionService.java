package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.elections.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 4/09/14.
 */
public interface ElectionService {

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadElectionEvent requestReadElection(RequestReadElectionEvent requestReadElectionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public ElectionCreatedEvent createElection(CreateElectionEvent createElectionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadElectionEvent readPreviousElection(RequestReadElectionEvent requestReadElectionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadElectionEvent readNextElection(RequestReadElectionEvent requestReadElectionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public ElectionDeletedEvent deleteElection(DeleteElectionEvent deleteElectionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public ElectionUpdatedEvent updateElection(UpdateElectionEvent updateElectionEvent);
}
