package com.eulersbridge.iEngage.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.database.domain.Student;
import com.eulersbridge.iEngage.database.repository.StudentRepository;

@RestController


public class SignUpController {

	@Autowired StudentRepository repo;

	public SignUpController() {
		// TODO Auto-generated constructor stub
	}

    private static Logger LOG = LoggerFactory.getLogger(ElectionController.class);


    @RequestMapping(method=RequestMethod.POST,value="/signUp/")
    public @ResponseBody Student saveNewUser(
            @RequestParam(value="email", required=true) String email,
            @RequestParam(value="firstName", required=true) String firstName,
            @RequestParam(value="lastName", required=true) String lastName,
            @RequestParam(value="gender", required=true) String gender,
            @RequestParam(value="nationality", required=true) String nationality,
            @RequestParam(value="yearOfBirth", required=true) String yearOfBirth,
            @RequestParam(value="personality", required=true) String personality,
            @RequestParam(value="password", required=true) String password,
            @RequestParam(value="institutionId", required=true) Long institutionId
            ) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(email+" attempting to save student. ");
		Student student=new Student(email,lastName,firstName,personality,nationality,yearOfBirth,gender,password);
    	Student test = repo.save(student);
		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
		Student result = repo.findOne(test.getNodeId());
    	return result;
    }
    
    
}