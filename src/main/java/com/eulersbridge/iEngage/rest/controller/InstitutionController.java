package com.eulersbridge.iEngage.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoReadEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.ReadGeneralInfoEvent;
import com.eulersbridge.iEngage.core.events.institutions.*;
import com.eulersbridge.iEngage.core.events.newsFeed.CreateNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;
import com.eulersbridge.iEngage.core.events.newsFeed.ReadNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedCreatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.core.services.VotingLocationService;
import com.eulersbridge.iEngage.rest.domain.VotingLocation;
import com.eulersbridge.iEngage.rest.domain.GeneralInfo;
import com.eulersbridge.iEngage.rest.domain.NewsFeed;
import com.eulersbridge.iEngage.rest.domain.Institution;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class InstitutionController 
{
@Autowired InstitutionService instService;
@Autowired VotingLocationService locationService;

    private static Logger LOG = LoggerFactory.getLogger(InstitutionController.class);

	public InstitutionController() 
	{
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
     * @param id the id of the institution to be updated.
     * @param institution the institution object passed across as JSON.
     * @return the institution object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.INSTITUTION_LABEL+"/{institutionId}")
    public @ResponseBody ResponseEntity<Institution> alterInstitution(@PathVariable Long institutionId,
    		@RequestBody Institution inst) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to edit institution. "+institutionId);
    	if (LOG.isInfoEnabled()) LOG.info("institution. "+inst);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
    	UpdateInstitutionEvent updEvt=new UpdateInstitutionEvent(institutionId,inst.toInstitutionDetails());
    	if (LOG.isDebugEnabled()) LOG.debug("updateEvt = "+updEvt);
    	UpdatedEvent instEvent=instService.updateInstitution(updEvt);

    	if ((null==instEvent)||(!instEvent.isEntityFound()))
    	{
    		if (LOG.isDebugEnabled())
    		{
	    		if (null==instEvent)
	    		{
	    			LOG.debug("instEvent = null");
	    		}
	    		else
	    		{
	    			LOG.debug("countryFound = "+instEvent.isEntityFound());
	    		}
    		}
    		result=new ResponseEntity<Institution>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
    		restInst=Institution.fromInstDetails((InstitutionDetails) instEvent.getDetails());
    		result=new ResponseEntity<Institution>(restInst,HttpStatus.OK);
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
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.INSTITUTION_LABEL+"/{institutionId}")
	public @ResponseBody ResponseEntity<Institution> findInstitution(@PathVariable Long institutionId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve institution. "+institutionId);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
		ReadEvent instEvent=instService.requestReadInstitution(new RequestReadInstitutionEvent(institutionId));
  	
		if (!instEvent.isEntityFound())
		{
			result=new ResponseEntity<Institution>(HttpStatus.NOT_FOUND);
		}
		else
		{
			restInst=Institution.fromInstDetails((InstitutionDetails)instEvent.getDetails());
			result=new ResponseEntity<Institution>(restInst,HttpStatus.OK);
		}
		return result;
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
     * 

	*/
	@RequestMapping(method=RequestMethod.DELETE,value=ControllerConstants.INSTITUTION_LABEL+"/{institutionId}")
	public @ResponseBody ResponseEntity<Institution> deleteInstitution(@PathVariable Long institutionId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to delete institution. "+institutionId);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
		DeletedEvent instEvent=instService.deleteInstitution(new DeleteInstitutionEvent(institutionId));
  	
		if (!instEvent.isEntityFound())
		{
			result=new ResponseEntity<Institution>(HttpStatus.NOT_FOUND);
		}
		else
		{
			restInst=Institution.fromInstDetails((InstitutionDetails)instEvent.getDetails());
			result=new ResponseEntity<Institution>(restInst,HttpStatus.OK);
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
     * 

	*/
    
    @RequestMapping(method=RequestMethod.POST,value=ControllerConstants.INSTITUTION_LABEL)
    public @ResponseBody ResponseEntity<Institution> createInstitution(@RequestBody Institution inst) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save institution "+inst);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
    	InstitutionCreatedEvent instEvent=instService.createInstitution(new CreateInstitutionEvent(inst.toInstitutionDetails()));

    	if (instEvent.getId()==null)
    	{
    		result=new ResponseEntity<Institution>(HttpStatus.BAD_REQUEST);
    	}
    	else if (!instEvent.isCountryFound())
    	{
    		result=new ResponseEntity<Institution>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
    		restInst=Institution.fromInstDetails((InstitutionDetails)instEvent.getDetails());
    		result=new ResponseEntity<Institution>(restInst,HttpStatus.CREATED);
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
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.INSTITUTION_LABEL+"/{institutionId}"+ControllerConstants.NEWSFEED_LABEL)
    public @ResponseBody ResponseEntity<NewsFeed> createNewsFeed(@PathVariable Long institutionId, @RequestBody NewsFeed sy) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save newsFeed "+sy);
    	NewsFeed restNewsFeed=null;
    	ResponseEntity<NewsFeed> result;
    	NewsFeedCreatedEvent nfEvent=instService.createNewsFeed(new CreateNewsFeedEvent(sy.toNewsFeedDetails()));

    	if (nfEvent.getId()==null)
    	{
    		result=new ResponseEntity<NewsFeed>(HttpStatus.BAD_REQUEST);
    	}
    	else if (!nfEvent.isInstitutionFound())
    	{
    		result=new ResponseEntity<NewsFeed>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
    		restNewsFeed=NewsFeed.fromNewsFeedDetails((NewsFeedDetails)nfEvent.getDetails());
    		result=new ResponseEntity<NewsFeed>(restNewsFeed,HttpStatus.CREATED);
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
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.INSTITUTION_LABEL+"/{institutionId}"+ControllerConstants.VOTING_LOCATION_LABEL)
    public @ResponseBody ResponseEntity<VotingLocation> createVotingLocation(@PathVariable Long institutionId, @RequestBody VotingLocation votingLocation) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save votingLocation "+votingLocation);
    	VotingLocation restVotingLocation=null;
    	ResponseEntity<VotingLocation> result;
    	// Make sure institutionId in URL matches ownerId in object.
    	votingLocation.setOwnerId(institutionId);
    	CreatedEvent vlEvent=locationService.createVotingLocation(new CreateVotingLocationEvent(votingLocation.toVotingLocationDetails()));

    	if (vlEvent.getNodeId()==null)
    	{
    		result=new ResponseEntity<VotingLocation>(HttpStatus.BAD_REQUEST);
    	}
    	else if (vlEvent.isFailed())
    	{
    		result=new ResponseEntity<VotingLocation>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
    		restVotingLocation=VotingLocation.fromVotingLocationDetails((VotingLocationDetails)vlEvent.getDetails());
    		result=new ResponseEntity<VotingLocation>(restVotingLocation,HttpStatus.CREATED);
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
     * @param instid the institution id object passed across as JSON.
     * @return the institution object returned by the Graph Database.
     * 

	*/
    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.INSTITUTION_LABEL+"/{institutionId}"+ControllerConstants.NEWSFEED_LABEL)
    public @ResponseBody ResponseEntity<NewsFeed> readNewsFeed(@PathVariable Long institutionId) 
    {
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve news feed for inst "+institutionId+".");
    	NewsFeed restNF=null;
    	ResponseEntity<NewsFeed> result;
		ReadEvent nfEvent=instService.readNewsFeed(new ReadNewsFeedEvent(institutionId));
  	
		if (!nfEvent.isEntityFound())
		{
			result=new ResponseEntity<NewsFeed>(HttpStatus.NOT_FOUND);
		}
		else
		{
			restNF=NewsFeed.fromNewsFeedDetails((NewsFeedDetails) nfEvent.getDetails());
			result=new ResponseEntity<NewsFeed>(restNF,HttpStatus.OK);
		}
		return result;
    }
    
    @RequestMapping(value="/displayInstParams")
    public @ResponseBody ResponseEntity<Boolean> displayDetails(@RequestBody Institution inst) 
    {
    	if (LOG.isInfoEnabled()) 
    		LOG.info("inst = "+inst);
    	return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.GENERAL_INFO_LABEL)
    public @ResponseBody ResponseEntity<GeneralInfo> generalInfo()
    {
    	if (LOG.isInfoEnabled()) LOG.info("general info called. ");
    	GeneralInfoReadEvent generalInfoReadEvent = instService.getGeneralInfo(new ReadGeneralInfoEvent());
    	GeneralInfo restGeneralInfo=GeneralInfo.fromGIDetails(generalInfoReadEvent.getDets());
    	ResponseEntity<GeneralInfo> result = new ResponseEntity<GeneralInfo>(restGeneralInfo,HttpStatus.OK);
    	return result;
    }
    

    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.INSTITUTIONS_LABEL)
    public @ResponseBody ResponseEntity<Iterator<Institution>> getInstitutions() 
    {
    	if (LOG.isInfoEnabled()) LOG.info(" attempting to retrieve institutions. ");
    	
		ReadInstitutionsEvent rie=new ReadInstitutionsEvent();
		return readInstitutions(rie);
    }
    
    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.INSTITUTIONS_LABEL+"/{countryId}")
    public @ResponseBody ResponseEntity<Iterator<Institution>> getInstitutions(@PathVariable Long countryId) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(" attempting to retrieve institutions from country "+countryId+". ");
    	
		ReadInstitutionsEvent rie=new ReadInstitutionsEvent(countryId);
		return readInstitutions(rie);
    }
    
    public @ResponseBody ResponseEntity<Iterator<Institution>> readInstitutions(ReadInstitutionsEvent rie)
    {
		InstitutionsReadEvent ire=instService.readInstitutions(rie);
		
		Iterator <InstitutionDetails> iter=ire.getInstitutions().iterator();
		ArrayList <Institution> institutions=new ArrayList<Institution>();
		while(iter.hasNext())
		{
			InstitutionDetails dets=iter.next();
			Institution thisInst=Institution.fromInstDetails(dets);
			institutions.add(thisInst);		
		}
    	return new ResponseEntity<Iterator<Institution>> (institutions.iterator(),HttpStatus.OK);
    }
}