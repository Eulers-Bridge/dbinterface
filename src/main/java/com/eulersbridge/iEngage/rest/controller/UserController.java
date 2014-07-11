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

import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.User;

//@Controller
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired UserService userService;
    
	public UserController() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UserController()");
	}

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method=RequestMethod.POST,value="/user")
    public @ResponseBody User addStudent(
    		@RequestBody User user) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(user.getEmail()+" attempting to save user. ");
//		User test = repo.save(user);
//		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
//		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
//		User result = repo.findOne(test.getNodeId());
		User result = user;
    	return result;
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/user/{email}")
//    @ResponseStatus(HttpStatus.OK);
    public @ResponseBody ResponseEntity<User> findUser(@PathVariable String email) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(email+" attempting to retrieve user. ");
    	ReadUserEvent userEvent=userService.requestReadUser(new RequestReadUserEvent(email));
    	
    	if (!userEvent.isEntityFound())
    	{
    		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    	}
    	User restUser=User.fromUserDetails(userEvent.getReadUserDetails());
    	return new ResponseEntity<User>(restUser,HttpStatus.OK);
    }	    
}

