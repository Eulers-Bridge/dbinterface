package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;

@RestController
public class UserController {

	@Autowired UserRepository repo;
	public UserController() {
		// TODO Auto-generated constructor stub
	}

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method=RequestMethod.PUT,value="/api/user/{email}/{lastName}/{firstName}/{phoneNumber}/{personality}/{nationality}/{yearOfBirth}/{gender}/{password}")
    public @ResponseBody User addStudent(
            @PathVariable String email, @PathVariable String lastName,@PathVariable String firstName,@PathVariable String phoneNumber, 
            @PathVariable String personality, @PathVariable String nationality,@PathVariable String yearOfBirth,
            @PathVariable String gender, @PathVariable String password) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(email+" attempting to save user. ");
    	User student=new User(email,lastName,firstName,personality,nationality,yearOfBirth,gender,password);
		User test = repo.save(student);
		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
		User result = repo.findOne(test.getNodeId());
    	return result;
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/api/user")
    public @ResponseBody User findStudent(
    		@RequestParam(value="email", required=true) String email) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(email+" attempting to retrieve user. ");
		Result <User> users = repo.findAll();
		Iterator<User> iter=users.iterator();
		User user=null;
		while (iter.hasNext())
		{
			User res=iter.next();
			if (res.getEmail().equals(email))
				user=res;
			if (LOG.isDebugEnabled()) LOG.debug("res = "+res);
		}
		if (LOG.isDebugEnabled()) LOG.debug("student = "+user);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
    	return user;
    }
	    
	    
}

