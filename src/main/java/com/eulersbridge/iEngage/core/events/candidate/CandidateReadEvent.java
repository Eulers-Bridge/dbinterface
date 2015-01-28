package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class CandidateReadEvent extends ReadEvent{
    public CandidateReadEvent(Long candidateId) {
        super(candidateId);
    }

    public CandidateReadEvent(Long candidateId, CandidateDetails candidateDetails) {
        super(candidateId, candidateDetails);
    }
}
