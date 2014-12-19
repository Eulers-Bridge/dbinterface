package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.polls.RequestReadPollEvent;
import com.eulersbridge.iEngage.core.services.CandidateService;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.Candidate;
import com.eulersbridge.iEngage.rest.domain.LikeInfo;
import com.eulersbridge.iEngage.rest.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class CandidateController {
    @Autowired
    CandidateService candidateService;
    @Autowired
    LikesService likesService;
    @Autowired
    UserService userService;

    private static Logger LOG = LoggerFactory.getLogger(CandidateController.class);

    public CandidateController() {
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.CANDIDATE_LABEL)
    public @ResponseBody ResponseEntity<Candidate>
    createCandidate(@RequestBody Candidate candidate){
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
    findCandidate(@PathVariable Long candidateId){
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
    updateCandidate(@PathVariable Long candidateId, @RequestBody Candidate candidate){
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
    deleteCandidate(@PathVariable Long candidateid){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete candidate. " + candidateid);
        DeletedEvent candidateDeletedEvent = candidateService.deleteCandidate(new DeleteCandidateEvent(candidateid));
        Boolean isDeletionCompleted = Boolean.valueOf(candidateDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }

    // like
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.CANDIDATE_LABEL
            + "/{candidateId}/likedBy/{email}/")
    public @ResponseBody ResponseEntity<Boolean> likeCandidate(
            @PathVariable Long candidateId, @PathVariable String email)
    {
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to have " + email + " like candidate. " + candidateId);
        LikedEvent likedPollEvent = userService.like(new LikeEvent(candidateId,
                email));
        ResponseEntity<Boolean> response;
        if (!likedPollEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!likedPollEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restNews = likedPollEvent.isResultSuccess();
            response = new ResponseEntity<Boolean>(restNews, HttpStatus.OK);
        }
        return response;
    }

    // unlike
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.CANDIDATE_LABEL
            + "/{candidateId}/unlikedBy/{email}/")
    public @ResponseBody ResponseEntity<Boolean> unlikeCandidate(
            @PathVariable Long candidateId, @PathVariable String email)
    {
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to have " + email + " unlike candidate. " + candidateId);
        LikedEvent unlikedPollEvent = userService.unlike(new LikeEvent(candidateId,
                email));
        ResponseEntity<Boolean> response;
        if (!unlikedPollEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!unlikedPollEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restNews = unlikedPollEvent.isResultSuccess();
            response = new ResponseEntity<Boolean>(restNews, HttpStatus.OK);
        }
        return response;
    }

    // likes
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CANDIDATE_LABEL
            + "/{candidateId}" + ControllerConstants.LIKES_LABEL)
    public @ResponseBody ResponseEntity<Iterator<LikeInfo>> findLikes(
            @PathVariable Long candidateId,
            @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
            @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
    {
        int pageNumber = 0;
        int pageLength = 10;
        pageNumber = Integer.parseInt(page);
        pageLength = Integer.parseInt(pageSize);
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to retrieve liked users from candidate " + candidateId
                    + '.');
        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (direction.equalsIgnoreCase("asc")) sortDirection = Sort.Direction.ASC;
        LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(
                new LikesLikeableObjectEvent(candidateId), sortDirection,
                pageNumber, pageLength);
        Iterator<LikeInfo> likes = User
                .toLikesIterator(likeableObjectLikesEvent.getUserDetails()
                        .iterator());
        if (likes.hasNext() == false)
        {
            ReadEvent readPollEvent = candidateService
                    .requestReadCandidate(new RequestReadCandidateEvent(candidateId));
            if (!readPollEvent.isEntityFound())
                return new ResponseEntity<Iterator<LikeInfo>>(
                        HttpStatus.NOT_FOUND);
            else return new ResponseEntity<Iterator<LikeInfo>>(likes,
                    HttpStatus.OK);
        }
        else return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
    }

}