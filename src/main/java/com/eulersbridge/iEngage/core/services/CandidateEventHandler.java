package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.repository.CandidateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CandidateEventHandler implements CandidateService {
    private static Logger LOG = LoggerFactory.getLogger(CandidateService.class);

    private CandidateRepository candidateRepository;

    public CandidateEventHandler(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public CandidateCreatedEvent createCandidate(CreateCandidateEvent createCandidateEvent) {
        CandidateDetails candidateDetails = (CandidateDetails) createCandidateEvent.getDetails();
        Candidate candidate = Candidate.fromCandidateDetails(candidateDetails);
        Candidate result = candidateRepository.save(candidate);
        CandidateCreatedEvent candidateCreatedEvent = new CandidateCreatedEvent(result.getCandidateId(), result.toCandidateDetails());
        return candidateCreatedEvent;
    }

    @Override
    public ReadEvent requestReadCandidate(RequestReadCandidateEvent requestReadCandidateEvent) {
        Candidate candidate = candidateRepository.findOne(requestReadCandidateEvent.getNodeId());
        ReadEvent readCandidateEvent;
        if(candidate != null){
            readCandidateEvent = new ReadCandidateEvent(candidate.getCandidateId(), candidate.toCandidateDetails());
        }
        else{
            readCandidateEvent = ReadCandidateEvent.notFound(requestReadCandidateEvent.getNodeId());
        }
        return readCandidateEvent;
    }

    @Override
    public UpdatedEvent updateCandidate(UpdateCandidateEvent updateCandidateEvent) {
        CandidateDetails candidateDetails = (CandidateDetails) updateCandidateEvent.getDetails();
        Candidate candidate = Candidate.fromCandidateDetails(candidateDetails);
        Long candidateId = candidateDetails.getNodeId();
        if(LOG.isDebugEnabled()) LOG.debug("candidateId is " + candidateId);
        Candidate candidateOld = candidateRepository.findOne(candidateId);
        if(candidateOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("candidate entity not found " + candidateId);
            return CandidateUpdatedEvent.notFound(candidateId);
        }
        else{
            Candidate result = candidateRepository.save(candidate);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getCandidateId());
            return new CandidateUpdatedEvent(result.getCandidateId(), result.toCandidateDetails());
        }
    }

    @Override
    public DeletedEvent deleteCandidate(DeleteCandidateEvent deleteCandidateEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteCandidateEvent= "+deleteCandidateEvent);
        Long candidateId = deleteCandidateEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteCandidate("+candidateId+")");
        Candidate candidate = candidateRepository.findOne(candidateId);
        if(candidate == null){
            return CandidateDeletedEvent.notFound(candidateId);
        }
        else{
            candidateRepository.delete(candidate);
            CandidateDeletedEvent candidateDeletedEvent = new CandidateDeletedEvent(candidateId);
            return candidateDeletedEvent;
        }
    }
}
