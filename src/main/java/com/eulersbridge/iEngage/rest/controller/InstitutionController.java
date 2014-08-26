package com.eulersbridge.iEngage.rest.controller;

import java.util.ArrayList;
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
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionsReadEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionsEvent;
import com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionCreatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDeletedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.studentYear.CreateStudentYearEvent;
import com.eulersbridge.iEngage.core.events.studentYear.ReadStudentYearEvent;
import com.eulersbridge.iEngage.core.events.studentYear.StudentYearCreatedEvent;
import com.eulersbridge.iEngage.core.events.studentYear.StudentYearReadEvent;
import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository.GeneralInfo;
import com.eulersbridge.iEngage.rest.domain.StudentYear;
import com.eulersbridge.iEngage.rest.domain.Institution;

@RestController
@RequestMapping("/api")
public class InstitutionController 
{
@Autowired InstitutionService instService;

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
    
    @RequestMapping(method=RequestMethod.PUT,value="/institution/{institutionId}")
    public @ResponseBody ResponseEntity<Institution> alterInstitution(@PathVariable Long institutionId,
    		@RequestBody Institution inst) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to edit institution. "+institutionId);
    	if (LOG.isInfoEnabled()) LOG.info("institution. "+inst);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
    	inst.setId(institutionId);
    	UpdateInstitutionEvent updEvt=new UpdateInstitutionEvent(institutionId,inst.toInstitutionDetails());
    	if (LOG.isDebugEnabled()) LOG.debug("updateEvt = "+updEvt);
    	InstitutionUpdatedEvent instEvent=instService.updateInstitution(updEvt);

    	if ((null==instEvent)||(!instEvent.isCountryFound()))
    	{
    		if (LOG.isDebugEnabled())
    		{
	    		if (null==instEvent)
	    		{
	    			LOG.debug("instEvent = null");
	    		}
	    		else
	    		{
	    			LOG.debug("countryFound = "+instEvent.isCountryFound());
	    		}
    		}
    		result=new ResponseEntity<Institution>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
    		restInst=Institution.fromInstDetails(instEvent.getInstDetails());
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
	@RequestMapping(method=RequestMethod.GET,value="/institution/{institutionId}")
	public @ResponseBody ResponseEntity<Institution> findInstitution(@PathVariable Long institutionId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve institution. "+institutionId);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
		ReadInstitutionEvent instEvent=instService.requestReadInstitution(new RequestReadInstitutionEvent(institutionId));
  	
		if (!instEvent.isEntityFound())
		{
			result=new ResponseEntity<Institution>(HttpStatus.NOT_FOUND);
		}
		else
		{
			restInst=Institution.fromInstDetails(instEvent.getInstitutionDetails());
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
	@RequestMapping(method=RequestMethod.DELETE,value="/institution/{institutionId}")
	public @ResponseBody ResponseEntity<Institution> deleteInstitution(@PathVariable Long institutionId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to delete institution. "+institutionId);
    	Institution restInst=null;
    	ResponseEntity<Institution> result;
		InstitutionDeletedEvent instEvent=instService.deleteInstitution(new DeleteInstitutionEvent(institutionId));
  	
		if (!instEvent.isEntityFound())
		{
			result=new ResponseEntity<Institution>(HttpStatus.NOT_FOUND);
		}
		else
		{
			restInst=Institution.fromInstDetails(instEvent.getDetails());
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
    
    @RequestMapping(method=RequestMethod.POST,value="/institution")
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
    		restInst=Institution.fromInstDetails(instEvent.getInstitutionDetails());
    		result=new ResponseEntity<Institution>(restInst,HttpStatus.OK);
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
    
    @RequestMapping(method=RequestMethod.POST,value="/institution/studentyear")
    public @ResponseBody ResponseEntity<StudentYear> createStudentYear(@RequestBody StudentYear sy) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save studentYear "+sy);
    	StudentYear restYear=null;
    	ResponseEntity<StudentYear> result;
    	StudentYearCreatedEvent syEvent=instService.createStudentYear(new CreateStudentYearEvent(sy.toStudentYearDetails()));

    	if (syEvent.getId()==null)
    	{
    		result=new ResponseEntity<StudentYear>(HttpStatus.BAD_REQUEST);
    	}
    	else if (!syEvent.isInstitutionFound())
    	{
    		result=new ResponseEntity<StudentYear>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
    		restYear=StudentYear.fromStudentYearDetails(syEvent.getStudentYearDetails());
    		result=new ResponseEntity<StudentYear>(restYear,HttpStatus.OK);
    	}
		return result;
    }
    
    /**
     * Is passed all the necessary data to read a student year.
     * The request must be a GET with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting student year object.
     * 
     * @param institutionid the institution id object passed across as JSON.
     * @param syid the institution id object passed across as JSON.
     * @return the institution object returned by the Graph Database.
     * 

	*/
   //TODO 
    @RequestMapping(method=RequestMethod.GET,value="/institution/{institutionId}/studentyear/{syId}")
    public @ResponseBody ResponseEntity<StudentYear> readStudentYear(@PathVariable Long institutionId,@PathVariable Long syId) 
    {
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve student year"+syId+" for . "+institutionId);
    	StudentYear restSY=null;
    	ResponseEntity<StudentYear> result;
		StudentYearReadEvent syEvent=instService.readStudentYear(new ReadStudentYearEvent(syId));
  	
		if (!syEvent.isEntityFound())
		{
			result=new ResponseEntity<StudentYear>(HttpStatus.NOT_FOUND);
		}
		else
		{
			restSY=StudentYear.fromStudentYearDetails(syEvent.getReadStudentYearDetails());
			result=new ResponseEntity<StudentYear>(restSY,HttpStatus.OK);
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

    @RequestMapping("/general-info")
    public @ResponseBody ResponseEntity<Iterator<GeneralInfo>> generalInfo()
    {
    	if (LOG.isInfoEnabled()) LOG.info("general info called. ");
    	Iterator<GeneralInfo> genInfo=instService.getGeneralInfo();
    	return new ResponseEntity<Iterator<GeneralInfo>>(genInfo,HttpStatus.OK);
    }
    

    @RequestMapping(value="/institutions")
    public @ResponseBody ResponseEntity<Iterator<Institution>> getInstitutions() 
    {
    	if (LOG.isInfoEnabled()) LOG.info(" attempting to retrieve institutions. ");
    	
		ReadInstitutionsEvent rie=new ReadInstitutionsEvent();
		return readInstitutions(rie);
    }
    
    @RequestMapping(value="/institutions/{countryId}")
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