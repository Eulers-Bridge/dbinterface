/**
 *
 */
package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.*;
import com.eulersbridge.iEngage.core.services.interfacePack.LikesService;
import com.eulersbridge.iEngage.core.services.interfacePack.VotingLocationService;
import com.eulersbridge.iEngage.rest.domain.*;
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
 * @author Greg Newitt
 */
@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class VotingLocationController {
  @Autowired
  VotingLocationService votingLocationService;
  @Autowired
  LikesService likesService;

  private static Logger LOG = LoggerFactory.getLogger(VotingLocationController.class);

  public VotingLocationController() {
  }

  //Create
  @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.VOTING_LOCATION_LABEL)
  public @ResponseBody
  ResponseEntity<VotingLocation>
  createVotingLocation(@RequestBody VotingLocation votingLocation) {
    if (LOG.isInfoEnabled())
      LOG.info("attempting to create votingLocation " + votingLocation);
    CreateVotingLocationEvent createVotingLocationEvent = new CreateVotingLocationEvent(votingLocation.toVotingLocationDetails());

    ResponseEntity<VotingLocation> response;
    if (null == votingLocation.getOwnerId()) {
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      CreatedEvent votingLocationCreatedEvent = votingLocationService.createVotingLocation(createVotingLocationEvent);
      if (null == votingLocationCreatedEvent) {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else if ((votingLocationCreatedEvent.getClass() == VotingLocationCreatedEvent.class) && (!(((VotingLocationCreatedEvent) votingLocationCreatedEvent).isOwnerFound()))) {
        response = new ResponseEntity<VotingLocation>(HttpStatus.NOT_FOUND);
      } else if ((null == votingLocationCreatedEvent.getNodeId()) || (votingLocationCreatedEvent.isFailed())) {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else {
        VotingLocation result = VotingLocation.fromVotingLocationDetails((VotingLocationDetails) votingLocationCreatedEvent.getDetails());
        if (LOG.isDebugEnabled())
          LOG.debug("votingLocation" + result.toString());
        response = new ResponseEntity<>(result, HttpStatus.CREATED);
      }
    }
    return response;
  }

  //Get
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.VOTING_LOCATION_LABEL + "/{votingLocationId}")
  public @ResponseBody
  ResponseEntity<VotingLocation>
  findVotingLocation(@PathVariable Long votingLocationId) {
    if (LOG.isInfoEnabled())
      LOG.info(votingLocationId + " attempting to get votingLocation. ");
    ReadVotingLocationEvent requestReadVotingLocationEvent = new ReadVotingLocationEvent(votingLocationId);
    ReadEvent readVotingLocationEvent = votingLocationService.readVotingLocation(requestReadVotingLocationEvent);
    if (readVotingLocationEvent.isEntityFound()) {
      VotingLocation votingLocation = VotingLocation.fromVotingLocationDetails((VotingLocationDetails) (readVotingLocationEvent.getDetails()));
      return new ResponseEntity<>(votingLocation, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Is passed all the necessary data to read votingLocations from the database. The
   * request must be a GET with the institutionId presented as the final
   * portion of the URL.
   * <p/>
   * This method will return the votingLocations read from the database.
   *
   * @param institutionId the institutionId of the votingLocation objects to be read.
   * @return the votingLocations.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.VOTING_LOCATIONS_LABEL
    + "/{institutionId}")
  public @ResponseBody
  ResponseEntity<WrappedDomainList> findVotingLocations(
    @PathVariable(value = "") Long institutionId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve votingLocations from institution "
        + institutionId + '.');
    ResponseEntity<WrappedDomainList> response;

    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    AllReadEvent votingLocationsEvent = votingLocationService.findVotingLocations(
      new ReadAllEvent(institutionId), sortDirection,
      pageNumber, pageLength);

    if (!votingLocationsEvent.isEntityFound()) {
      response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<VotingLocation> votingLocations = VotingLocation
        .toVotingLocationsIterator(votingLocationsEvent.getDetails().iterator());
      WrappedDomainList theVotingLocations = WrappedDomainList.fromIterator(votingLocations, votingLocationsEvent.getTotalItems(), votingLocationsEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(theVotingLocations, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Is passed all the necessary data to read votingLocations from the database. The
   * request must be a GET with the electionId presented as the final
   * portion of the URL.
   * <p/>
   * This method will return the votingLocations read from the database.
   *
   * @param electionId the electionId of the votingLocation objects to be read.
   * @return the votingLocations.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.VOTING_BOOTHS_LABEL
    + "/{electionId}")
  public @ResponseBody
  ResponseEntity<WrappedDomainList> findVotingBooths(
    @PathVariable(value = "") Long electionId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve votingLocations from institution "
        + electionId + '.');
    ResponseEntity<WrappedDomainList> response;

    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    AllReadEvent votingBoothsEvent = votingLocationService.findVotingBooths(
      new ReadAllEvent(electionId), sortDirection,
      pageNumber, pageLength);

    if (!votingBoothsEvent.isEntityFound()) {
      return new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<VotingLocation> votingLocations = VotingLocation
        .toVotingLocationsIterator(votingBoothsEvent.getDetails().iterator());
      WrappedDomainList theVotingLocations = WrappedDomainList.fromIterator(votingLocations, votingBoothsEvent.getTotalItems(), votingBoothsEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(theVotingLocations, HttpStatus.OK);
    }
    return response;
  }

  //Update
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.VOTING_LOCATION_LABEL + "/{votingLocationId}")
  public @ResponseBody
  ResponseEntity<VotingLocation>
  updateVotingLocation(@PathVariable Long votingLocationId, @RequestBody VotingLocation votingLocation) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to update votingLocation. " + votingLocationId);
    UpdatedEvent votingLocationUpdatedEvent = votingLocationService.updateVotingLocation(new UpdateVotingLocationEvent(votingLocationId, votingLocation.toVotingLocationDetails()));
    if (null != votingLocationUpdatedEvent) {
      if (LOG.isDebugEnabled())
        LOG.debug("votingLocationUpdatedEvent - " + votingLocationUpdatedEvent);
      if (votingLocationUpdatedEvent.isEntityFound()) {
        VotingLocation result = VotingLocation.fromVotingLocationDetails((VotingLocationDetails) votingLocationUpdatedEvent.getDetails());
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
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.VOTING_LOCATION_LABEL + "/{votingLocationId}")
  public @ResponseBody
  ResponseEntity<Response>
  deleteVotingLocation(@PathVariable Long votingLocationId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to delete votingLocation. " + votingLocationId);
    DeletedEvent votingLocationDeletedEvent = votingLocationService.deleteVotingLocation(new DeleteVotingLocationEvent(votingLocationId));
    ResponseEntity<Response> response;

    Response restEvent;
    if (!votingLocationDeletedEvent.isEntityFound()) {
      restEvent = Response.failed("Not found");
      response = new ResponseEntity<Response>(restEvent, HttpStatus.NOT_FOUND);
    } else {
      if (votingLocationDeletedEvent.isDeletionCompleted()) {
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
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.VOTING_LOCATION_LABEL
    + "/{votingLocationId}" + ControllerConstants.LIKED_BY_LABEL + "/{email}/")
  public @ResponseBody
  ResponseEntity<Response> likeVotingLocation(
    @PathVariable Long votingLocationId, @PathVariable String email) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to have " + email + " like votingLocation. " + votingLocationId);
    LikedEvent likedVotingLocationEvent = likesService.like(new LikeEvent(votingLocationId,
      email));
    ResponseEntity<Response> response;
    if (!likedVotingLocationEvent.isEntityFound()) {
      response = new ResponseEntity<Response>(HttpStatus.GONE);
    } else if (!likedVotingLocationEvent.isUserFound()) {
      response = new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
    } else {
      Response restEvent;
      if (likedVotingLocationEvent.isResultSuccess())
        restEvent = new Response();
      else
        restEvent = Response.failed("Could not like.");
      response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
    }
    return response;
  }

  // unlike
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.VOTING_LOCATION_LABEL
    + "/{votingLocationId}" + ControllerConstants.LIKED_BY_LABEL + "/{email}/")
  public @ResponseBody
  ResponseEntity<Response> unlikeVotingLocation(
    @PathVariable Long votingLocationId, @PathVariable String email) {
    email = Util.getUserEmailFromSession();
    RequestHandledEvent result = likesService.unlike(email, votingLocationId);
    return result.toResponseEntity();
  }

  // likes
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.VOTING_LOCATION_LABEL
    + "/{votingLocationId}" + ControllerConstants.LIKES_LABEL)
  public @ResponseBody
  ResponseEntity<Iterator<LikeInfo>> findLikes(
    @PathVariable Long votingLocationId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve liked users from votingLocation " + votingLocationId
        + '.');
    Sort.Direction sortDirection = Sort.Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Sort.Direction.ASC;
    LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(
      new LikesLikeableObjectEvent(votingLocationId), sortDirection,
      pageNumber, pageLength);
    Iterator<LikeInfo> likes = UserDomain
      .toLikesIterator(likeableObjectLikesEvent.getUserDetails()
        .iterator());
    if (likes.hasNext() == false) {
      ReadEvent readPollEvent = votingLocationService
        .readVotingLocation(new ReadVotingLocationEvent(votingLocationId));
      if (!readPollEvent.isEntityFound())
        return new ResponseEntity<Iterator<LikeInfo>>(
          HttpStatus.NOT_FOUND);
      else return new ResponseEntity<Iterator<LikeInfo>>(likes,
        HttpStatus.OK);
    } else return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
  }
}
