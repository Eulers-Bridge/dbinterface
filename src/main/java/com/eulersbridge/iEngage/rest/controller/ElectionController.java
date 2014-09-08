package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.Elections.ElectionDetail;
import com.eulersbridge.iEngage.core.events.Elections.ReadElectionEvent;
import com.eulersbridge.iEngage.core.events.Elections.RequestReadElectionEvent;
import com.eulersbridge.iEngage.core.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.eulersbridge.iEngage.rest.domain.Election;

//import com.eulersbridge.iEngage.database.domain.Election;
//import com.eulersbridge.iEngage.database.repository.ElectionRepository;


@RestController
@RequestMapping("/api")
public class ElectionController {

//	@Autowired ElectionRepository repo;
    @Autowired
    ElectionService electionService;

	public ElectionController() {
		// TODO Auto-generated constructor stub
	}

    private static Logger LOG = LoggerFactory.getLogger(ElectionController.class);


//    @RequestMapping(value="/api/election/{year}/{start}/{end}/{votingStart}/{votingEnd}")
//    public @ResponseBody Election saveElection(
//            @PathVariable String year, @PathVariable String start, @PathVariable String end,@PathVariable String votingStart,@PathVariable String votingEnd)
//    {
//    	if (LOG.isInfoEnabled()) LOG.info(year+" attempting to save election. ");
//    	Election election=new Election(year,start,end,votingStart,votingEnd);
//		Election test = repo.save(election);
//		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
//		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
//		Election result = repo.findOne(test.getNodeId());
//    	return result;
//    }
//
//    @RequestMapping(value="/api/election/{year}")
//    public @ResponseBody Election findElection(
//            @PathVariable String year)
//    {
//    	if (LOG.isInfoEnabled()) LOG.info(year+" attempting to retrieve election. ");
//		Result <Election> elections = repo.findAll();
//		Iterator<Election> iter=elections.iterator();
//		Election election=null;
//		while (iter.hasNext())
//		{
//			Election res=iter.next();
//			if (res.getYear().equals(year))
//				election=res;
//			if (LOG.isDebugEnabled()) LOG.debug("res = "+res);
//		}
//		if (LOG.isDebugEnabled()) LOG.debug("election = "+election);
//		if (LOG.isDebugEnabled()) LOG.debug("Count = "+repo.count());
//    	return election;
//    }

    @RequestMapping(method = RequestMethod.GET ,value = "/election/{electionId}")
    public @ResponseBody ResponseEntity<Election> findElection(@PathVariable Long electionId)
    {
        if (LOG.isInfoEnabled()) LOG.info(electionId+" attempting to get election. ");
        RequestReadElectionEvent requestReadElectionEvent= new RequestReadElectionEvent(electionId);
        ReadElectionEvent readElectionEvent= electionService.requestReadElection(requestReadElectionEvent);
        if (readElectionEvent.isEntityFound()){
            Election election = Election.fromElectionDetail(readElectionEvent.getElectionDetail());
            return new ResponseEntity<Election>(election, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
        }
    }
}