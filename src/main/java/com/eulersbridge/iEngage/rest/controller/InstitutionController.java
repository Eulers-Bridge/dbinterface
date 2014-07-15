package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

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

import com.eulersbridge.iEngage.core.events.institutions.CreateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionCreatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDeletedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionUpdatedEvent;
import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.rest.domain.Institution;

@RestController
@RequestMapping("/api")
public class InstitutionController 
{
//	@Autowired InstitutionService instService;

    private static Logger LOG = LoggerFactory.getLogger(InstitutionController.class);

	public InstitutionController() 
	{
		// TODO Auto-generated constructor stub
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
    
    @RequestMapping(method=RequestMethod.PUT,value="/institution/{institutionId}")
    public @ResponseBody ResponseEntity<Institution> alterInstitution(@PathVariable Long institutionId,
    		@RequestBody Institution inst) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to edit institution. "+inst.getId());
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
    	
//TODO    	InstitutionUpdatedEvent instEvent=instService.updateInstitution(new UpdateInstitutionEvent(institutionId,inst.toInstitutionDetails()));

/*    	if (!instEvent.isCountryFound())
    	{
    		result=new ResponseEntity<Institution>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
//    	Institution restInst=Institution.fromInstDetails(instEvent.getInstDetails());
*/      	result=new ResponseEntity<Institution>(restInst,HttpStatus.OK);
//    	}
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
	@RequestMapping(method=RequestMethod.GET,value="/institution/{institutionId}")
	public @ResponseBody ResponseEntity<Institution> findInstitution(@PathVariable Long institutionId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve institution. "+institutionId);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
//TODO		ReadInstitutionEvent instEvent=instService.requestReadInstitution(new RequestReadInstitutionEvent(institutionId));
  	
/*		if (!instEvent.isEntityFound())
		{
			result=new ResponseEntity<Institution>(HttpStatus.NOT_FOUND);
		}
		Institution restInst=Institution.fromInstDetails(instEvent.getInstitutionDetails());
*/		result=new ResponseEntity<Institution>(restInst,HttpStatus.OK);
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
	@RequestMapping(method=RequestMethod.DELETE,value="/institution/{institutionId}")
	public @ResponseBody ResponseEntity<Institution> deleteInstitution(@PathVariable Long institutionId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to delete institution. "+institutionId);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
//TODO		InstitutionDeletedEvent instEvent=instService.deleteInstitution(new DeleteInstitutionEvent(institutionId));
  	
/*		if (!instEvent.isEntityFound())
		{
			result=new ResponseEntity<Institution>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Institution restInst=Institution.fromInstDetails(instEvent.getDetails());
*/			result=new ResponseEntity<Institution>(restInst,HttpStatus.OK);
//		}
		return result;
	}
    
    
    /**
     * Is passed all the necessary data to create a new user.
     * The request must be a POST with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting user object.
     * There will also be a relationship set up with the 
     * institution the user belongs to.
     * 
     * @param user the user object passed across as JSON.
     * @return the user object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.POST,value="/institution")
    public @ResponseBody ResponseEntity<Institution> createInstitution(@RequestBody Institution inst) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save institution "+inst);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
//TODO    	InstitutionCreatedEvent instEvent=instService.createInstitution(new CreateInstitutionEvent(inst.toInstitutionDetails()));

/*    	if (instEvent.getId()==null)
    	{
    		result=new ResponseEntity<Institution>(HttpStatus.BAD_REQUEST);
    	}
    	else if (!instEvent.isCountryFound())
    	{
    		result=new ResponseEntity<Institution>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
    		Institution restInst=Institution.fromInstDetails(instEvent.getInstitutionDetails());
*/    		result=new ResponseEntity<Institution>(restInst,HttpStatus.OK);
//    	}
		return result;
    }
    
    @RequestMapping(value="/displayInstParams")
    public @ResponseBody ResponseEntity<Boolean> displayDetails(@RequestBody Institution inst) 
    {
    	if (LOG.isInfoEnabled()) 
    		LOG.info("inst = "+inst);
    	return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }


    @RequestMapping(value="/api/institutions")
    public @ResponseBody Institution getInstitutions() 
    {
    	if (LOG.isInfoEnabled()) LOG.info(" attempting to retrieve institutions. ");
    	
//		Iterator<Institution> iter=institutions.iterator();
		Institution institution=null;
/*		while (iter.hasNext())
		{
			Institution res=iter.next();
			if (res.getName().equals(name))
				institution=res;
			if (LOG.isDebugEnabled()) LOG.debug("res = "+res);
		}
		if (LOG.isDebugEnabled()) LOG.debug("institution = "+institution);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
*/    	return institution;
//Institution inst=repo.findByPropertyValue("name", name);
    }
    
    
}