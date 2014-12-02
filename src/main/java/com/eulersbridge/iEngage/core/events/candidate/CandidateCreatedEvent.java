package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CandidateCreatedEvent extends CreatedEvent {
    private Long candidateId;

    private static Logger LOG = LoggerFactory.getLogger(CandidateCreatedEvent.class);

    public CandidateCreatedEvent(Long candidateId) {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.candidateId = candidateId;
    }

    public CandidateCreatedEvent(Long candidateId, CandidateDetails candidateDetails) {
        super(candidateDetails);
        this.candidateId = candidateId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
}
