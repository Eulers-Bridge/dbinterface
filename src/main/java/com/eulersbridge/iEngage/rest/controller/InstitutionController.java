package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
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

    private static Logger LOG = LoggerFactory.getLogger(InstitutionController.class);


    @RequestMapping(value="/institution/{name}/{campus}/{state}/{country}")
    public @ResponseBody Institution logout(
            @PathVariable String name, @PathVariable String campus,@PathVariable String state,@PathVariable String country) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(name+" attempting to save institution. ");
    	Institution institution=new Institution(name,campus,state,country);
		Institution test = repo.save(institution);
		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
		Institution result = repo.findOne(test.getNodeId());
    	return result;
//Institution inst=repo.findByPropertyValue("name", name);
    }
    
    @RequestMapping(value="/institution/{name}")
    public @ResponseBody Institution logout(
            @PathVariable String name) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(name+" attempting to retrieve institution. ");
		Result <Institution> institutions = repo.findAll();
		Iterator<Institution> iter=institutions.iterator();
		Institution institution=null;
		while (iter.hasNext())
		{
			Institution res=iter.next();
			if (res.getName().equals(name))
				institution=res;
			if (LOG.isDebugEnabled()) LOG.debug("res = "+res);
		}
		if (LOG.isDebugEnabled()) LOG.debug("institution = "+institution);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
    	return institution;
//Institution inst=repo.findByPropertyValue("name", name);
    }
    
    
}