package com.eulersbridge.iEngage.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @RequestMapping(value="/signUp/{email}")
    public @ResponseBody Student saveNewUser(
            @PathVariable String email) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(email+" attempting to save student. ");
    	String yearOfBirth="";
		String gender="";
		String personality="";
		String lastName="test";
		String firstName="test";
		String nationality="Oz";
		Student student=new Student(email,lastName,firstName,personality,nationality,yearOfBirth,gender);
    	Student test = repo.save(student);
		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
		Student result = repo.findOne(test.getNodeId());
    	return result;
    }
    
    
}