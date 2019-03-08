package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.services.interfacePack.CandidateService;
import com.eulersbridge.iEngage.core.services.interfacePack.LikesService;
import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
import com.eulersbridge.iEngage.rest.domain.*;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
  public @ResponseBody
  ResponseEntity<CandidateDomain>
  createCandidate(@RequestBody CandidateDomain candidate) {
    if (LOG.isInfoEnabled()) LOG.info("attempting to create candidate " + candidate);
    CreateCandidateEvent createCandidateEvent = new CreateCandidateEvent(candidate.toCandidateDetails());
    RequestHandledEvent requestHandledEvent = candidateService.createCandidate(createCandidateEvent);
    return requestHandledEvent.toResponseEntity();
  }

  //Get
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CANDIDATE_LABEL + "/{candidateId}")
  public @ResponseBody
  ResponseEntity<CandidateDomain>
  findCandidate(@PathVariable Long candidateId) {
    if (LOG.isInfoEnabled())
      LOG.info(candidateId + " attempting to get candidate. ");
    RequestReadCandidateEvent requestReadCandidateEvent = new RequestReadCandidateEvent(candidateId);
    ReadEvent readCandidateEvent = candidateService.requestReadCandidate(requestReadCandidateEvent);
    if (readCandidateEvent.isEntityFound()) {
      CandidateDomain candidate = CandidateDomain.fromCandidateDetails((CandidateDetails) (readCandidateEvent.getDetails()));
      return new ResponseEntity<>(candidate, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Is passed all the necessary data to read candidates from the database. The
   * request must be a GET with the electionId presented as the final
   * portion of the URL.
   * <p/>
   * This method will return the candidates read from the database.
   *
   * @param electionId the electionId of the candidate objects to be read.
   * @return the candidates.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CANDIDATES_LABEL
    + "/{electionId}")
  public @ResponseBody
  ResponseEntity<WrappedDomainList> findCandidates(
    @PathVariable(value = "") Long electionId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve candidates from institution "
        + electionId + '.');
    ResponseEntity<WrappedDomainList> response;

    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    AllReadEvent articleEvent = candidateService.readCandidates(
      new ReadAllEvent(electionId), sortDirection,
      pageNumber, pageLength);

    if (!articleEvent.isEntityFound()) {
      response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<CandidateDomain> candidates = CandidateDomain
        .toCandidatesIterator(articleEvent.getDetails().iterator());
      WrappedDomainList theBadges = WrappedDomainList.fromIterator(candidates, articleEvent.getTotalItems(), articleEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(theBadges, HttpStatus.OK);
    }
    return response;
  }

  //Update
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.CANDIDATE_LABEL + "/{candidateId}")
  public @ResponseBody
  ResponseEntity<CandidateDomain>
  updateCandidate(@PathVariable Long candidateId, @RequestBody CandidateDomain candidate) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to update candidate. " + candidateId);
    UpdatedEvent candidateUpdatedEvent = candidateService.updateCandidate(new UpdateCandidateEvent(candidateId, candidate.toCandidateDetails()));
    if (null != candidateUpdatedEvent) {
      if (LOG.isDebugEnabled())
        LOG.debug("candidateUpdatedEvent - " + candidateUpdatedEvent);
      if (candidateUpdatedEvent.isEntityFound()) {
        CandidateDomain result = CandidateDomain.fromCandidateDetails((CandidateDetails) candidateUpdatedEvent.getDetails());
        if (LOG.isDebugEnabled()) LOG.debug("result = " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  //Delete
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.CANDIDATE_LABEL + "/{candidateId}")
  public @ResponseBody
  ResponseEntity<Response>
  deleteCandidate(@PathVariable Long candidateId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to delete candidate. " + candidateId);
    DeletedEvent candidateDeletedEvent = candidateService.deleteCandidate(new DeleteCandidateEvent(candidateId));
    ResponseEntity<Response> response;
    Boolean isDeletionCompleted = Boolean.valueOf(candidateDeletedEvent.isDeletionCompleted());
    if (!candidateDeletedEvent.isEntityFound()) {
      response = new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
    } else {
      Response restEvent;
      if (isDeletionCompleted) {
        restEvent = new Response();
        response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
      } else {
        restEvent = Response.failed("Could not delete");
        response = new ResponseEntity<Response>(restEvent, HttpStatus.GONE);
      }
    }
    return response;
  }

  // like
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.CANDIDATE_LABEL
    + "/{candidateId}" + ControllerConstants.LIKED_BY_LABEL + "/{email}/")
  public @ResponseBody
  ResponseEntity<Response> likeCandidate(
    @PathVariable Long candidateId, @PathVariable String email) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to have " + email + " like candidate. " + candidateId);
    LikedEvent likedCandidateEvent = likesService.like(new LikeEvent(candidateId,
      email));
    ResponseEntity<Response> response;
    if (!likedCandidateEvent.isEntityFound()) {
      response = new ResponseEntity<Response>(HttpStatus.GONE);
    } else if (!likedCandidateEvent.isUserFound()) {
      response = new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
    } else {
      Response restEvent;
      if (likedCandidateEvent.isResultSuccess())
        restEvent = new Response();
      else
        restEvent = Response.failed("Could not like.");
      response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
    }
    return response;
  }

  // unlike
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.CANDIDATE_LABEL
    + "/{candidateId}" + ControllerConstants.LIKED_BY_LABEL + "/{email}/")
  public @ResponseBody
  ResponseEntity<Response> unlikeCandidate(
    @PathVariable Long candidateId, @PathVariable String email) {
    email = Util.getUserEmailFromSession();
    RequestHandledEvent result = likesService.unlike(email, candidateId);
    return result.toResponseEntity();
  }

  // likes
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CANDIDATE_LABEL
    + "/{candidateId}" + ControllerConstants.LIKES_LABEL)
  public @ResponseBody
  ResponseEntity<Iterator<LikeInfo>> findLikes(
    @PathVariable Long candidateId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
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
    if (likes.hasNext() == false) {
      ReadEvent readPollEvent = candidateService
        .requestReadCandidate(new RequestReadCandidateEvent(candidateId));
      if (!readPollEvent.isEntityFound())
        return new ResponseEntity<Iterator<LikeInfo>>(
          HttpStatus.NOT_FOUND);
      else return new ResponseEntity<Iterator<LikeInfo>>(likes,
        HttpStatus.OK);
    } else return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
  }

  // Add ticket to candidate
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.CANDIDATE_LABEL
    + "/{candidateId}" + ControllerConstants.TICKET_LABEL + "/{ticketId}")
  public @ResponseBody
  ResponseEntity<Boolean> addTicket(
    @PathVariable Long candidateId, @PathVariable Long ticketId) {
    if (LOG.isInfoEnabled()) LOG.info("Attempting to add ticket " + ticketId + " to candidate " + candidateId);
    RequestHandledEvent<Boolean> requestHandledEvent = candidateService.addTicket(new AddTicketEvent(candidateId, ticketId));
    return requestHandledEvent.toResponseEntity();
  }

  // remove ticket from candidate
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.CANDIDATE_LABEL
    + "/{candidateId}" + ControllerConstants.TICKET_LABEL + "/{ticketId}")
  public @ResponseBody
  ResponseEntity<Response> removeTicket(
    @PathVariable Long candidateId, @PathVariable Long ticketId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to remove ticket " + ticketId + " from candidate " + candidateId);
    ResponseEntity<Response> response;
    UpdatedEvent ticketRemovedEvent = candidateService.removeTicket(new DeleteEvent(candidateId));
    if (!ticketRemovedEvent.isEntityFound()) {
      response = new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
    } else if (ticketRemovedEvent.isFailed()) {
      response = new ResponseEntity<Response>(HttpStatus.FORBIDDEN);
    } else {
      Response restEvent = new Response();
      response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
    }
    return response;
  }
}