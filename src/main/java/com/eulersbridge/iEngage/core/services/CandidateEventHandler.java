package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.Position;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.CandidateRepository;
import com.eulersbridge.iEngage.database.repository.PositionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CandidateEventHandler implements CandidateService {
    private static Logger LOG = LoggerFactory.getLogger(CandidateService.class);

    private CandidateRepository candidateRepository;
    private UserRepository userRepository;
    private PositionRepository positionRepository;

    public CandidateEventHandler(CandidateRepository candidateRepository,UserRepository userRepository,PositionRepository positionRepository)
    {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public CreatedEvent createCandidate(CreateCandidateEvent createCandidateEvent)
    {
        CandidateDetails candidateDetails = (CandidateDetails) createCandidateEvent.getDetails();
        CreatedEvent candidateCreatedEvent;
        
        Long userId=candidateDetails.getUserId();
    	if (LOG.isDebugEnabled()) LOG.debug("Finding user with nodeId = "+userId);
    	
        User user=null;
        if (userId!=null) user = userRepository.findOne(userId);
        
        if (user!=null)
        {
	        Long positionId=candidateDetails.getPositionId();
	    	if (LOG.isDebugEnabled()) LOG.debug("Finding position with nodeId = "+positionId);
	        Position position=null;
	        if (positionId!=null) position=positionRepository.findOne(positionId);
	    	if (position!=null)
	    	{
		        Candidate candidate = Candidate.fromCandidateDetails(candidateDetails);
		        candidate.setUser(user);
		        candidate.setPosition(position);
		        Candidate result = candidateRepository.save(candidate);
		        if ((null==result)||(null==result.getNodeId()))
		        	candidateCreatedEvent = CreatedEvent.failed(candidateDetails);
		        else
		        	candidateCreatedEvent = new CandidateCreatedEvent(result.toCandidateDetails());
	    	}
	    	else
	    	{
	    		candidateCreatedEvent=CandidateCreatedEvent.positionNotFound(candidateDetails.getPositionId());
	    	}
        }
        else
        {
    		candidateCreatedEvent=CandidateCreatedEvent.userNotFound(candidateDetails.getUserId());
        }
        return candidateCreatedEvent;
    }
    
    @Override
    public ReadEvent requestReadCandidate(RequestReadCandidateEvent requestReadCandidateEvent) {
        Candidate candidate = candidateRepository.findOne(requestReadCandidateEvent.getNodeId());
        ReadEvent readCandidateEvent;
        if(candidate != null){
            readCandidateEvent = new CandidateReadEvent(candidate.getNodeId(), candidate.toCandidateDetails());
        }
        else{
            readCandidateEvent = CandidateReadEvent.notFound(requestReadCandidateEvent.getNodeId());
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
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            return new CandidateUpdatedEvent(result.getNodeId(), result.toCandidateDetails());
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
