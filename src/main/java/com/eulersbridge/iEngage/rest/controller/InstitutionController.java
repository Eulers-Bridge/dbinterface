package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoReadEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.ReadGeneralInfoEvent;
import com.eulersbridge.iEngage.core.events.institutions.*;
import com.eulersbridge.iEngage.core.events.newsFeed.CreateNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;
import com.eulersbridge.iEngage.core.events.newsFeed.ReadNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.InstitutionService;
import com.eulersbridge.iEngage.core.services.interfacePack.PhotoService;
import com.eulersbridge.iEngage.core.services.interfacePack.VotingLocationService;
import com.eulersbridge.iEngage.rest.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class InstitutionController {
  final
  InstitutionService instService;
  final
  VotingLocationService locationService;
  final
  PhotoService photoService;

  private static Logger LOG = LoggerFactory.getLogger(InstitutionController.class);

  @Autowired
  public InstitutionController(InstitutionService instService, VotingLocationService locationService, PhotoService photoService) {
    this.instService = instService;
    this.locationService = locationService;
    this.photoService = photoService;
  }

  /**
   * Is passed all the necessary data to update an institution.
   * Or potentially create a new one.
   * The request must be a PUT with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting user object.
   * There will also be a relationship set up with the
   * country the institution belongs to.
   *
   * @param id          the id of the institution to be updated.
   * @param institution the institution object passed across as JSON.
   * @return the institution object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.INSTITUTION_LABEL + "/{institutionId}")
  public @ResponseBody
  ResponseEntity<InstitutionDomain> alterInstitution(@PathVariable Long institutionId,
                                                     @RequestBody InstitutionDomain inst) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to edit institution. " + institutionId);
    if (LOG.isInfoEnabled()) LOG.info("institution. " + inst);
    InstitutionDomain restInst = null;
    ResponseEntity<InstitutionDomain> result;
    UpdateInstitutionEvent updEvt = new UpdateInstitutionEvent(institutionId, inst.toInstitutionDetails());
    if (LOG.isDebugEnabled()) LOG.debug("updateEvt = " + updEvt);
    UpdatedEvent instEvent = instService.updateInstitution(updEvt);

    if ((null == instEvent) || (!instEvent.isEntityFound())) {
      if (LOG.isDebugEnabled()) {
        if (null == instEvent) {
          LOG.debug("instEvent = null");
        } else {
          LOG.debug("countryFound = " + instEvent.isEntityFound());
        }
      }
      result = new ResponseEntity<InstitutionDomain>(HttpStatus.FAILED_DEPENDENCY);
    } else {
      restInst = InstitutionDomain.fromInstDetails((InstitutionDetails) instEvent.getDetails());
      result = new ResponseEntity<InstitutionDomain>(restInst, HttpStatus.OK);
    }
    return result;
  }

  /**
   * Is passed all the necessary data to read an institution from the database.
   * The request must be a GET with the institution id presented
   * as the final portion of the URL.
   * <p/>
   * This method will return the institution object read from the database.
   *
   * @param institutionid the institution id of the institution object to be read.
   * @return the institution object.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.INSTITUTION_LABEL + "/{institutionId}")
  public @ResponseBody
  ResponseEntity<InstitutionDomain> findInstitution(@PathVariable Long institutionId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve institution. " + institutionId);
    InstitutionDomain restInst = null;
    ResponseEntity<InstitutionDomain> result;
    ReadEvent instEvent = instService.requestReadInstitution(new RequestReadInstitutionEvent(institutionId));

    if (!instEvent.isEntityFound()) {
      result = new ResponseEntity<InstitutionDomain>(HttpStatus.NOT_FOUND);
    } else {
      restInst = InstitutionDomain.fromInstDetails((InstitutionDetails) instEvent.getDetails());
      result = new ResponseEntity<InstitutionDomain>(restInst, HttpStatus.OK);
    }
    return result;
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.INSTITUTION_LABEL + "/{institutionId}" + ControllerConstants.PHOTOS_LABEL)
  public @ResponseBody
  ResponseEntity<List<PhotoDomain>> findAllInstitutionPhoto(@PathVariable Long institutionId) {
    RequestHandledEvent<List<PhotoDomain>> res = photoService.findPhotosToInstitution(institutionId);
    if (res.getTargetNotFound())
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (!res.getSuccess())
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(res.getResponseEntity(), HttpStatus.OK);
  }

  /**
   * Is passed all the necessary data to delete an institution.
   * The request must be a DELETE with the user email presented
   * as the final portion of the URL.
   * <p/>
   * This method will return the deleted user object.
   *
   * @param email the email address of the user object to be deleted.
   * @return the user object deleted.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.INSTITUTION_LABEL + "/{institutionId}")
  public @ResponseBody
  ResponseEntity<InstitutionDomain> deleteInstitution(@PathVariable Long institutionId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to delete institution. " + institutionId);
    InstitutionDomain restInst = null;
    ResponseEntity<InstitutionDomain> result;
    DeletedEvent instEvent = instService.deleteInstitution(new DeleteInstitutionEvent(institutionId));

    if (!instEvent.isEntityFound()) {
      result = new ResponseEntity<InstitutionDomain>(HttpStatus.NOT_FOUND);
    } else {
      restInst = InstitutionDomain.fromInstDetails((InstitutionDetails) instEvent.getDetails());
      result = new ResponseEntity<InstitutionDomain>(restInst, HttpStatus.OK);
    }
    return result;
  }


  /**
   * Is passed all the necessary data to create a new institution.
   * The request must be a POST with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting institution object.
   * There will also be a relationship set up with the
   * country the institution belongs to.
   *
   * @param institution the institution object passed across as JSON.
   * @return the institution object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.INSTITUTION_LABEL)
  public @ResponseBody
  ResponseEntity<InstitutionDomain> createInstitution(@RequestBody InstitutionDomain inst) {
    if (LOG.isInfoEnabled()) LOG.info("attempting to save institution " + inst);
    InstitutionDomain restInst = null;
    ResponseEntity<InstitutionDomain> result;
    InstitutionCreatedEvent instEvent = instService.createInstitution(new CreateInstitutionEvent(inst.toInstitutionDetails()));

    if (instEvent.getId() == null) {
      result = new ResponseEntity<InstitutionDomain>(HttpStatus.BAD_REQUEST);
    } else if (!instEvent.isCountryFound()) {
      result = new ResponseEntity<InstitutionDomain>(HttpStatus.FAILED_DEPENDENCY);
    } else {
      restInst = InstitutionDomain.fromInstDetails((InstitutionDetails) instEvent.getDetails());
      result = new ResponseEntity<InstitutionDomain>(restInst, HttpStatus.CREATED);
    }
    return result;
  }

  /**
   * Is passed all the necessary data to create a new student year.
   * The request must be a POST with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting student year object.
   * There will also be a relationship set up with the
   * institution the student year belongs to.
   *
   * @param student year the student year object passed across as JSON.
   * @return the student year object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.INSTITUTION_LABEL + "/{institutionId}" + ControllerConstants.NEWSFEED_LABEL)
  public @ResponseBody
  ResponseEntity<NewsFeed> createNewsFeed(@PathVariable Long institutionId, @RequestBody NewsFeed sy) {
    if (LOG.isInfoEnabled()) LOG.info("attempting to save newsFeed " + sy);
    NewsFeed restNewsFeed = null;
    ResponseEntity<NewsFeed> result;
    NewsFeedCreatedEvent nfEvent = instService.createNewsFeed(new CreateNewsFeedEvent(sy.toNewsFeedDetails()));

    if (nfEvent.getId() == null) {
      result = new ResponseEntity<NewsFeed>(HttpStatus.BAD_REQUEST);
    } else if (!nfEvent.isInstitutionFound()) {
      result = new ResponseEntity<NewsFeed>(HttpStatus.FAILED_DEPENDENCY);
    } else {
      restNewsFeed = NewsFeed.fromNewsFeedDetails((NewsFeedDetails) nfEvent.getDetails());
      result = new ResponseEntity<NewsFeed>(restNewsFeed, HttpStatus.CREATED);
    }
    return result;
  }

  /**
   * Is passed all the necessary data to create a new voting location.
   * The request must be a POST with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting voting location object.
   * There will also be a relationship set up with the
   * institution the voting location belongs to.
   *
   * @param votingLocation the voting location object passed across as JSON.
   * @return the votingLocation object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.INSTITUTION_LABEL + "/{institutionId}" + ControllerConstants.VOTING_LOCATION_LABEL)
  public @ResponseBody
  ResponseEntity<VotingLocation> createVotingLocation(@PathVariable Long institutionId, @RequestBody VotingLocation votingLocation) {
    if (LOG.isInfoEnabled())
      LOG.info("attempting to save votingLocation " + votingLocation);
    VotingLocation restVotingLocation = null;
    ResponseEntity<VotingLocation> result;
    // Make sure institutionId in URL matches ownerId in object.
    votingLocation.setOwnerId(institutionId);
    CreatedEvent vlEvent = locationService.createVotingLocation(new CreateVotingLocationEvent(votingLocation.toVotingLocationDetails()));

    if (vlEvent.getNodeId() == null) {
      result = new ResponseEntity<VotingLocation>(HttpStatus.BAD_REQUEST);
    } else if (vlEvent.isFailed()) {
      result = new ResponseEntity<VotingLocation>(HttpStatus.FAILED_DEPENDENCY);
    } else {
      restVotingLocation = VotingLocation.fromVotingLocationDetails((VotingLocationDetails) vlEvent.getDetails());
      result = new ResponseEntity<VotingLocation>(restVotingLocation, HttpStatus.CREATED);
    }
    return result;
  }

  /**
   * Is passed all the necessary data to read a news feed.
   * The request must be a GET with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting student year object.
   *
   * @param institutionid the institution id object passed across as JSON.
   * @param instid        the institution id object passed across as JSON.
   * @return the institution object returned by the Graph Database.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.INSTITUTION_LABEL + "/{institutionId}" + ControllerConstants.NEWSFEED_LABEL)
  public @ResponseBody
  ResponseEntity<NewsFeed> readNewsFeed(@PathVariable Long institutionId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve news feed for inst " + institutionId + ".");
    NewsFeed restNF = null;
    ResponseEntity<NewsFeed> result;
    ReadEvent nfEvent = instService.readNewsFeed(new ReadNewsFeedEvent(institutionId));

    if (!nfEvent.isEntityFound()) {
      result = new ResponseEntity<NewsFeed>(HttpStatus.NOT_FOUND);
    } else {
      restNF = NewsFeed.fromNewsFeedDetails((NewsFeedDetails) nfEvent.getDetails());
      result = new ResponseEntity<NewsFeed>(restNF, HttpStatus.OK);
    }
    return result;
  }

  @RequestMapping(value = "/displayInstParams")
  public @ResponseBody
  ResponseEntity<Boolean> displayDetails(@RequestBody InstitutionDomain inst) {
    if (LOG.isInfoEnabled())
      LOG.info("inst = " + inst);
    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.GENERAL_INFO_LABEL)
  public @ResponseBody
  ResponseEntity<GeneralInfo> generalInfo() {
    if (LOG.isInfoEnabled()) LOG.info("general info called. ");
    GeneralInfoReadEvent generalInfoReadEvent = instService.getGeneralInfo(new ReadGeneralInfoEvent());
    GeneralInfo restGeneralInfo = GeneralInfo.fromGIDetails(generalInfoReadEvent.getDets());
    ResponseEntity<GeneralInfo> result = new ResponseEntity<GeneralInfo>(restGeneralInfo, HttpStatus.OK);
    return result;
  }


  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.INSTITUTIONS_LABEL)
  public @ResponseBody
  ResponseEntity<Iterator<InstitutionDomain>> getInstitutions() {
    if (LOG.isInfoEnabled()) LOG.info(" attempting to retrieve institutions. ");

    ReadAllEvent rie = new ReadAllEvent(null);
    return readInstitutions(rie);
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.INSTITUTIONS_LABEL + "/{countryId}")
  public @ResponseBody
  ResponseEntity<Iterator<InstitutionDomain>> getInstitutions(@PathVariable Long countryId) {
    if (LOG.isInfoEnabled())
      LOG.info(" attempting to retrieve institutions from country " + countryId + ". ");

    ReadAllEvent rie = new ReadAllEvent(countryId);
    return readInstitutions(rie);
  }

  public @ResponseBody
  ResponseEntity<Iterator<InstitutionDomain>> readInstitutions(ReadAllEvent rie) {
    InstitutionsReadEvent ire = instService.readInstitutions(rie);

    Iterator<InstitutionDetails> iter = ire.getInstitutions().iterator();
    ArrayList<InstitutionDomain> institutionDomains = new ArrayList<InstitutionDomain>();
    while (iter.hasNext()) {
      InstitutionDetails dets = iter.next();
      InstitutionDomain thisInst = InstitutionDomain.fromInstDetails(dets);
      institutionDomains.add(thisInst);
    }
    return new ResponseEntity<Iterator<InstitutionDomain>>(institutionDomains.iterator(), HttpStatus.OK);
  }
}