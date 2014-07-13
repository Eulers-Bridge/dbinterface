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

import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;

@RestController

public class ElectionController {

	@Autowired ElectionRepository repo;

	public ElectionController() {
		// TODO Auto-generated constructor stub
	}

    private static Logger LOG = LoggerFactory.getLogger(ElectionController.class);


    @RequestMapping(value="/api/election/{year}/{start}/{end}/{votingStart}/{votingEnd}")
    public @ResponseBody Election saveElection(
            @PathVariable String year, @PathVariable String start, @PathVariable String end,@PathVariable String votingStart,@PathVariable String votingEnd) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(year+" attempting to save election. ");
    	Election election=new Election(year,start,end,votingStart,votingEnd);
		Election test = repo.save(election);
		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
		Election result = repo.findOne(test.getNodeId());
    	return result;
    }
    
    @RequestMapping(value="/api/election/{year}")
    public @ResponseBody Election findElection(
            @PathVariable String year) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(year+" attempting to retrieve election. ");
		Result <Election> elections = repo.findAll();
		Iterator<Election> iter=elections.iterator();
		Election election=null;
		while (iter.hasNext())
		{
			Election res=iter.next();
			if (res.getYear().equals(year))
				election=res;
			if (LOG.isDebugEnabled()) LOG.debug("res = "+res);
		}
		if (LOG.isDebugEnabled()) LOG.debug("election = "+election);
		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
    	return election;
    }
    
    
}