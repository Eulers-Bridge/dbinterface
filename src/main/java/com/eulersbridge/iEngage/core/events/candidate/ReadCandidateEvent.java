package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadCandidateEvent extends ReadEvent{
    public ReadCandidateEvent(Long candidateId) {
        super(candidateId);
    }

    public ReadCandidateEvent(Long candidateId, CandidateDetails candidateDetails) {
        super(candidateId, candidateDetails);
    }
}
