package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.core.services.CandidateService;
import com.eulersbridge.iEngage.rest.domain.Candidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class CandidateController {
    @Autowired
    CandidateService candidateService;

    private static Logger LOG = LoggerFactory.getLogger(CandidateController.class);

    public CandidateController() {
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.CANDIDATE_LABEL)
    public @ResponseBody ResponseEntity<Candidate>
    createPosition(@RequestBody Candidate candidate){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create candidate "+candidate);
        CreateCandidateEvent createCandidateEvent = new CreateCandidateEvent(candidate.toCandidateDetails());
        CandidateCreatedEvent candidateCreatedEvent = candidateService.createCandidate(createCandidateEvent);
        if(candidateCreatedEvent.getCandidateId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Candidate result = Candidate.fromCandidateDetails((CandidateDetails)candidateCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("candidate"+result.toString());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CANDIDATE_LABEL + "/{candidateid}")
    public @ResponseBody ResponseEntity<Candidate>
    findPosition(@PathVariable Long candidateId){
        if (LOG.isInfoEnabled()) LOG.info(candidateId+" attempting to get candidate. ");
        RequestReadCandidateEvent requestReadCandidateEvent = new RequestReadCandidateEvent(candidateId);
        ReadEvent readCandidateEvent = candidateService.requestReadCandidate(requestReadCandidateEvent);
        if(readCandidateEvent.isEntityFound()){
            Candidate candidate = Candidate.fromCandidateDetails((CandidateDetails) readCandidateEvent.getDetails());
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.CANDIDATE_LABEL+"/{candidateid}")
    public @ResponseBody ResponseEntity<Candidate>
    updatePosition(@PathVariable Long candidateId, @RequestBody Candidate candidate){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update candidate. " + candidateId);
        UpdatedEvent candidateUpdatedEvent = candidateService.updateCandidate(new UpdateCandidateEvent(candidateId, candidate.toCandidateDetails()));
        if(null != candidateUpdatedEvent){
            if (LOG.isDebugEnabled()) LOG.debug("candidateUpdatedEvent - "+candidateUpdatedEvent);
            if(candidateUpdatedEvent.isEntityFound()){
                Candidate result = Candidate.fromCandidateDetails((CandidateDetails) candidateUpdatedEvent.getDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.CANDIDATE_LABEL+"/{candidateid}")
    public @ResponseBody ResponseEntity<Boolean>
    deletePosition(@PathVariable Long candidateid){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete candidate. " + candidateid);
        DeletedEvent candidateDeletedEvent = candidateService.deleteCandidate(new DeleteCandidateEvent(candidateid));
        Boolean isDeletionCompleted = Boolean.valueOf(candidateDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }
}