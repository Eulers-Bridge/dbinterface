package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateCandidateEvent extends UpdateEvent {
    public UpdateCandidateEvent(Long candidateId, CandidateDetails candidateDetails) {
        super(candidateId, candidateDetails);
    }
}
