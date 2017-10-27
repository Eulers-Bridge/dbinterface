package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.elections.*;
import com.eulersbridge.iEngage.core.events.votingLocation.AddVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.RemoveVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.ElectionService;
import com.eulersbridge.iEngage.core.services.interfacePack.VotingLocationService;
import com.eulersbridge.iEngage.rest.domain.ElectionDomain;
import com.eulersbridge.iEngage.rest.domain.WrappedDomainList;
import com.eulersbridge.iEngage.rest.domain.Response;
import com.eulersbridge.iEngage.rest.domain.VotingLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class ElectionController {
  private static Logger LOG = LoggerFactory
    .getLogger(ElectionController.class);

  @Autowired
  ElectionService electionService;
  @Autowired
  VotingLocationService votingLocationService;

  public ElectionController() {
    if (LOG.isDebugEnabled()) LOG.debug("ElectionController()");
  }


  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.ELECTION_LABEL + "/{electionId}")
  public @ResponseBody
  ResponseEntity<ElectionDomain> findElection(@PathVariable Long electionId) {
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(
      electionId);
    ReadEvent readElectionEvent = electionService
      .readElection(requestReadElectionEvent);
    if (readElectionEvent.isEntityFound()) {
      ElectionDomain electionDomain = ElectionDomain.fromElectionDetails((ElectionDetails) readElectionEvent.getDetails());
      return new ResponseEntity<ElectionDomain>(electionDomain, HttpStatus.OK);
    } else {
      return new ResponseEntity<ElectionDomain>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.ELECTIONS_LABEL + "/{institutionId}")
  public @ResponseBody
  ResponseEntity<WrappedDomainList> findElections(
    @PathVariable(value = "") Long institutionId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve elections from institution "
        + institutionId + '.');
    ResponseEntity<WrappedDomainList> response;

    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    AllReadEvent electionEvent = electionService.readElections(
      new ReadAllEvent(institutionId), sortDirection,
      pageNumber, pageLength);

    if (!electionEvent.isEntityFound()) {
      response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<ElectionDomain> elections = ElectionDomain
        .toElectionsIterator(electionEvent.getDetails().iterator());
      WrappedDomainList theElections = WrappedDomainList.fromIterator(elections, electionEvent.getTotalItems(), electionEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(theElections, HttpStatus.OK);
    }
    return response;
  }

  // Create
  @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.ELECTION_LABEL)
  public @ResponseBody
  ResponseEntity<ElectionDomain> createElection(@RequestBody ElectionDomain electionDomain) {
    RequestHandledEvent<ElectionDomain> res = electionService.createElection(electionDomain);
    return res.toResponseEntity();
  }

  // Add Voting Location
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.ELECTION_LABEL
    + "/{electionId}" + ControllerConstants.VOTING_LOCATION_LABEL + "/{votingLocationId}")
  public @ResponseBody
  ResponseEntity<VotingLocation> addVotingLocation(
    @PathVariable Long electionId, @PathVariable Long votingLocationId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to add voting location " + votingLocationId + " to election " + electionId + ".");

    UpdatedEvent electionUpdatedEvent = votingLocationService.addVotingLocationToElection(new AddVotingLocationEvent(votingLocationId, electionId));
    if ((null != electionUpdatedEvent)) {
      if (LOG.isDebugEnabled())
        LOG.debug("electionUpdatedEvent - " + electionUpdatedEvent);
      if (electionUpdatedEvent.isEntityFound()) {
        VotingLocation restElection = VotingLocation
          .fromVotingLocationDetails((VotingLocationDetails) electionUpdatedEvent
            .getDetails());
        if (LOG.isDebugEnabled())
          LOG.debug("restElection = " + restElection);
        return new ResponseEntity<VotingLocation>(restElection, HttpStatus.OK);
      } else {
        return new ResponseEntity<VotingLocation>(HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<VotingLocation>(HttpStatus.BAD_REQUEST);
    }
  }

  // Remove Voting Location
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.ELECTION_LABEL
    + "/{electionId}" + ControllerConstants.VOTING_LOCATION_LABEL + "/{votingLocationId}")
  public @ResponseBody
  ResponseEntity<Response> removeVotingLocation(
    @PathVariable Long electionId, @PathVariable Long votingLocationId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to remove voting location " + votingLocationId + " from election " + electionId + ".");

    DeletedEvent locationRemovedEvent = votingLocationService.removeVotingLocationFromElection(new RemoveVotingLocationEvent(electionId, votingLocationId));
    if ((null != locationRemovedEvent)) {
      if (LOG.isDebugEnabled())
        LOG.debug("locationRemovedEvent - " + locationRemovedEvent);
      if (locationRemovedEvent.isEntityFound()) {
        Response restEvent = new Response();
        return new ResponseEntity<Response>(restEvent, HttpStatus.OK);
      } else {
        return new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
    }
  }

  // Get Previous
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.ELECTION_LABEL
    + "/{electionId}/previous")
  public @ResponseBody
  ResponseEntity<ElectionDomain> findPreviousElection(
    @PathVariable Long electionId) {
    if (LOG.isInfoEnabled())
      LOG.info("attempting to get previous election");
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(
      electionId);
    ReadEvent readElectionEvent = electionService
      .readPreviousElection(requestReadElectionEvent);
    if (readElectionEvent.isEntityFound()) {
      ElectionDomain electionDomain = ElectionDomain.fromElectionDetails((ElectionDetails) readElectionEvent.getDetails());
      return new ResponseEntity<ElectionDomain>(electionDomain, HttpStatus.OK);
    } else {
      return new ResponseEntity<ElectionDomain>(HttpStatus.NOT_FOUND);
    }
  }

  // Get Next
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.ELECTION_LABEL
    + "/{electionId}/next")
  public @ResponseBody
  ResponseEntity<ElectionDomain> findNextElection(
    @PathVariable Long electionId) {
    if (LOG.isInfoEnabled()) LOG.info("attempting to get next election");
    RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(
      electionId);
    ReadEvent readElectionEvent = electionService.readNextElection(requestReadElectionEvent);
    if (readElectionEvent.isEntityFound()) {
      ElectionDomain electionDomain = ElectionDomain.fromElectionDetails((ElectionDetails) readElectionEvent.getDetails());
      return new ResponseEntity<ElectionDomain>(electionDomain, HttpStatus.OK);
    } else {
      return new ResponseEntity<ElectionDomain>(HttpStatus.NOT_FOUND);
    }
  }

  // Delete
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.ELECTION_LABEL
    + "/{electionId}")
  public @ResponseBody
  ResponseEntity<Response> deleteElection(
    @PathVariable Long electionId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to delete election. " + electionId);
    ResponseEntity<Response> response;
    DeletedEvent elecEvent = electionService
      .deleteElection(new DeleteElectionEvent(electionId));
    Response restEvent;
    if (!elecEvent.isEntityFound()) {
      restEvent = Response.failed("Not found");
      response = new ResponseEntity<Response>(restEvent, HttpStatus.NOT_FOUND);
    } else {
      if (elecEvent.isDeletionCompleted()) {
        restEvent = new Response();
        response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
      } else {
        restEvent = Response.failed("Could not delete");
        response = new ResponseEntity<Response>(restEvent, HttpStatus.GONE);
      }
    }
    return response;
  }

  // Update
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.ELECTION_LABEL
    + "/{electionId}")
  public @ResponseBody
  ResponseEntity<ElectionDomain> updateElection(
    @PathVariable Long electionId, @RequestBody ElectionDomain electionDomain) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to update election. " + electionId);
    UpdatedEvent electionUpdatedEvent = electionService
      .updateElection(new UpdateElectionEvent(electionId, electionDomain
        .toElectionDetails()));
    if ((null != electionUpdatedEvent)) {
      if (LOG.isDebugEnabled())
        LOG.debug("electionUpdatedEvent - " + electionUpdatedEvent);
      if (electionUpdatedEvent.isEntityFound()) {
        ElectionDomain restElectionDomain = ElectionDomain
          .fromElectionDetails((ElectionDetails) electionUpdatedEvent
            .getDetails());
        if (LOG.isDebugEnabled())
          LOG.debug("restElection = " + restElectionDomain);
        return new ResponseEntity<ElectionDomain>(restElectionDomain, HttpStatus.OK);
      } else {
        return new ResponseEntity<ElectionDomain>(HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<ElectionDomain>(HttpStatus.BAD_REQUEST);
    }
  }
}