package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.Elections.*;
import com.eulersbridge.iEngage.core.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Get
    @RequestMapping(method = RequestMethod.GET, value = "/election/{electionId}")
    public @ResponseBody ResponseEntity<Election> findElection(@PathVariable Long electionId)
    {
        if (LOG.isInfoEnabled()) LOG.info(electionId+" attempting to get election. ");
        RequestReadElectionEvent requestReadElectionEvent= new RequestReadElectionEvent(electionId);
        ReadElectionEvent readElectionEvent= electionService.requestReadElection(requestReadElectionEvent);
        if (readElectionEvent.isEntityFound()){
            Election election = Election.fromElectionDetails(readElectionEvent.getElectionDetails());
            return new ResponseEntity<Election>(election, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
        }
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = "/election")
    public @ResponseBody ResponseEntity<Election> createElection(@RequestBody Election election){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create election "+election);
        ElectionCreatedEvent electionCreatedEvent = electionService.createElection(new CreateElectionEvent(election.toElectionDetails()));
        if(electionCreatedEvent.getElectionId() == null){
            return new ResponseEntity<Election>(HttpStatus.BAD_REQUEST);
        }
        else {
            Election result = Election.fromElectionDetails(electionCreatedEvent.getElectionDetails());
            if (LOG.isDebugEnabled()) LOG.debug("election"+result.toString());
            return new ResponseEntity<Election>(result, HttpStatus.OK);
        }
    }

    //Get Previous
    @RequestMapping(method = RequestMethod.GET, value = "/election/{electionId}/previous")
    public @ResponseBody ResponseEntity<Election> findPreviousElection(@PathVariable Long electionId){
        if (LOG.isInfoEnabled()) LOG.info("attempting to get previous election");
        RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(electionId);
        ReadElectionEvent readElectionEvent = electionService.readPreviousElection(requestReadElectionEvent);
        if (readElectionEvent.isEntityFound()){
            Election election = Election.fromElectionDetails(readElectionEvent.getElectionDetails());
            return new ResponseEntity<Election>(election, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
        }
    }

    //Get Next
    @RequestMapping(method = RequestMethod.GET, value = "/election/{electionId}/next")
    public @ResponseBody ResponseEntity<Election> findNextElection(@PathVariable Long electionId){
        if (LOG.isInfoEnabled()) LOG.info("attempting to get next election");
        RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(electionId);
        ReadElectionEvent readElectionEvent = electionService.readNextElection(requestReadElectionEvent);
        if (readElectionEvent.isEntityFound()){
            Election election = Election.fromElectionDetails(readElectionEvent.getElectionDetails());
            return new ResponseEntity<Election>(election, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = "/election/{electionId}")
    public @ResponseBody ResponseEntity<Boolean> deleteElection(@PathVariable Long electionId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete election. " + electionId);
        ElectionDeletedEvent electionDeletedEvent = electionService.deleteElection(new DeleteElectionEvent(electionId));
        Boolean isDeletionCompleted = Boolean.valueOf(electionDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = "/election/{electionId}")
    public @ResponseBody ResponseEntity<Election> updateElection(@PathVariable Long electionId, @RequestBody Election election)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update election. " + electionId);
        ElectionUpdatedEvent electionUpdatedEvent= electionService.updateElection(new UpdateElectionEvent(electionId, election.toElectionDetails()));
        if ((null!=electionUpdatedEvent))
        {
        	if (LOG.isDebugEnabled()) LOG.debug("electionUpdatedEvent - "+electionUpdatedEvent);
        	if(electionUpdatedEvent.isEntityFound())
        	{
        		Election restElection = Election.fromElectionDetails(electionUpdatedEvent.getElectionDetails());
        		if (LOG.isDebugEnabled()) LOG.debug("restElection = "+restElection);
        		return new ResponseEntity<Election>(restElection, HttpStatus.OK);
        	}
            else
            {
                return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<Election>(HttpStatus.BAD_REQUEST);
        }
    }
}