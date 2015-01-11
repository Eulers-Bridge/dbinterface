package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class CandidateDeletedEvent extends DeletedEvent{
    public CandidateDeletedEvent(Long candidateId) {
        super(candidateId);
    }
}
