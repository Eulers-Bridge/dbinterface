package com.eulersbridge.iEngage.rest.controller;


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

import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.User;

@RestController


public class SignUpController {

    @Autowired UserService userService;

	public SignUpController() {
		// TODO Auto-generated constructor stub
	}

    private static Logger LOG = LoggerFactory.getLogger(SignUpController.class);


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
    @RequestMapping(method=RequestMethod.POST,value="/api/signUp/{institutionId}")
    public @ResponseBody ResponseEntity<User> saveNewUser(
            @RequestBody User user,
            @PathVariable Long institutionId
            ) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save user "+user);
//    	Institution inst=instRepo.findOne(institutionId);
    	institutionId=user.getInstitutionId();
    	UserCreatedEvent userEvent=userService.signUpNewUser(new CreateUserEvent(user.toUserDetails()));
/*    	if (inst!=null)
    	{
    		user.setInstitution(inst);
/*    		Transaction tx=graphDatabaseService.beginTx();
    		try
    		{
*    			User test = userRepo.save(user);
/*    			tx.success();
    		}
    		catch (Exception e)
    		{
    			tx.failure();
    		}
    		finally
    		{
    			tx.finish();
    		}
*    		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
    		if (LOG.isDebugEnabled()) LOG.debug("Count = "+userRepo.count());
    		result = userRepo.findOne(test.getNodeId());
    	}
    	else
    	{
    		result=user;
    	}
*/
    	if (userEvent.getKey()==null)
    	{
    		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    	}
    	User restUser=User.fromUserDetails(userEvent.getUserDetails());
    	return new ResponseEntity<User>(restUser,HttpStatus.OK);
    }
    
    
    @RequestMapping(value="/api/displayParams/{institutionId}")
    public @ResponseBody boolean displayDetails(
            @RequestBody User user,
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