package com.eulersbridge.iEngage.core.events.elections;


import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadElectionEvent extends RequestReadEvent{
    public RequestReadElectionEvent(Long electionId)
    {
        super(electionId);
    }
}
