package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadCandidateEvent extends RequestReadEvent{
    public RequestReadCandidateEvent(Long candidateId) {
        super(candidateId);
    }
}
