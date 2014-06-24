package com.eulersbridge.iEngage.rest.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

@RestController

public class InstitutionController 
{
	@Autowired InstitutionRepository repo;

	public InstitutionController() {
		// TODO Auto-generated constructor stub
	}



    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/institution/{name}")
    public @ResponseBody Long logout(
            @PathVariable String name) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(name+" attempting to retrieve institution. ");
    	return repo.count();
//Institution inst=repo.findByPropertyValue("name", name);
    }
    
    
}