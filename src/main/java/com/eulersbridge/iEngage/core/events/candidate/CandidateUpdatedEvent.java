package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class CandidateUpdatedEvent extends UpdatedEvent{
    public CandidateUpdatedEvent(Long candidateId, CandidateDetails candidateDetails) {
        super(candidateId, candidateDetails);
    }

    public CandidateUpdatedEvent(Long candidateId) {
        super(candidateId);
    }
}
