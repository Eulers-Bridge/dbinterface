package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteCandidateEvent extends DeleteEvent{
    public DeleteCandidateEvent(Long candidateId) {
        super(candidateId);
    }
}
