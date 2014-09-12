package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.Elections.ReadElectionEvent;
import com.eulersbridge.iEngage.core.events.Elections.RequestReadElectionEvent;

/**
 * Created by darcular on 4/09/14.
 */
public interface ElectionService {
    public ReadElectionEvent requestReadElection(RequestReadElectionEvent requestReadElectionEvent);
}
