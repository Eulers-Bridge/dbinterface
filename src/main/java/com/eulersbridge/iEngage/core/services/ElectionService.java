package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.Elections.*;

/**
 * Created by darcular on 4/09/14.
 */
public interface ElectionService {
    public ReadElectionEvent requestReadElection(RequestReadElectionEvent requestReadElectionEvent);
    public ElectionCreatedEvent createElection(CreateElectionEvent createElectionEvent);
    public ReadElectionEvent readPreviousElection(RequestReadElectionEvent requestReadElectionEvent);
    public ReadElectionEvent readNextElection(RequestReadElectionEvent requestReadElectionEvent);
    public ElectionDeletedEvent deleteElection(DeleteElectionEvent deleteElectionEvent);
    public ElectionUpdatedEvent updateElection(UpdateElectionEvent updateElectionEvent);
}
