package com.eulersbridge.iEngage.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Student;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.StudentRepository;

@RestController


public class SignUpController {

	@Autowired StudentRepository studentRepo;
	@Autowired InstitutionRepository instRepo;

	public SignUpController() {
		// TODO Auto-generated constructor stub
	}

    private static Logger LOG = LoggerFactory.getLogger(ElectionController.class);


/**
 * Is passed all the necessary data to create a new user.
 * The request must be a PUT with the necessary parameters in the
 * attached data.
 * <p/>
 * This method will return the resulting user object which will now also have a userID
 * supplied by the Graph Database.  There will also be a relationship set up with the 
 * institution the user belongs to.
 * 
 * @param user the user object passed across as JSON.
 * @param institutionId the institution this user belongs to.
 * @return the user object returned by the Graph Database.
 * 

		*/
    @RequestMapping(method=RequestMethod.PUT,value="/signUp/{institutionId}")
    public @ResponseBody Student saveNewUser(
            @RequestBody Student user,
            @PathVariable Long institutionId
            ) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save user "+user);
    	Institution inst=instRepo.findOne(institutionId);
    	Student result;
    	if (inst!=null)
    	{
    		user.setInstitution(inst);
    		Student test = studentRepo.save(user);
    		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
    		if (LOG.isDebugEnabled()) LOG.debug("Count = "+studentRepo.count());
    		result = studentRepo.findOne(test.getNodeId());
    	}
    	else
    	{
    		result=user;
    	}
    	return result;
    }
    
    
    @RequestMapping(value="/displayParams/{institutionId}")
    public @ResponseBody boolean displayDetails(
            @RequestBody Student user,
            @PathVariable Long institutionId
            ) 
    {
    	if (LOG.isInfoEnabled()) 
    	{
    		LOG.info("user = "+user);
    		LOG.info("password = "+user.getPassword());
    		LOG.info("institutionId = "+institutionId);
    	}
    	return true;
    }
    
    
}