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

import com.eulersbridge.iEngage.database.domain.Student;
import com.eulersbridge.iEngage.database.repository.StudentRepository;

@RestController
public class StudentController {

	@Autowired StudentRepository repo;
	public StudentController() {
		// TODO Auto-generated constructor stub
	}

    private static Logger LOG = LoggerFactory.getLogger(StudentController.class);

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method=RequestMethod.PUT,value="/student/{email}/{lastName}/{firstName}/{phoneNumber}/{personality}/{nationality}/{yearOfBirth}/{gender}/{password}")
    public @ResponseBody Student addStudent(
            @PathVariable String email, @PathVariable String lastName,@PathVariable String firstName,@PathVariable String phoneNumber, 
            @PathVariable String personality, @PathVariable String nationality,@PathVariable String yearOfBirth,
            @PathVariable String gender, @PathVariable String password) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(email+" attempting to save student. ");
    	Student student=new Student(email,lastName,firstName,personality,nationality,yearOfBirth,gender,password);
		Student test = repo.save(student);
		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
		Student result = repo.findOne(test.getNodeId());
    	return result;
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/student")
    public @ResponseBody Student findStudent(
    		@RequestParam(value="email", required=true) String email) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(email+" attempting to retrieve student. ");
		Result <Student> students = repo.findAll();
		Iterator<Student> iter=students.iterator();
		Student student=null;
		while (iter.hasNext())
		{
			Student res=iter.next();
			if (res.getEmail().equals(email))
				student=res;
			if (LOG.isDebugEnabled()) LOG.debug("res = "+res);
		}
		if (LOG.isDebugEnabled()) LOG.debug("student = "+student);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
    	return student;
    }
	    
	    
}

