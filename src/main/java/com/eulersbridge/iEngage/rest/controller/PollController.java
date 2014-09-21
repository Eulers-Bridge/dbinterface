package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.core.services.PollService;
import com.eulersbridge.iEngage.rest.domain.Poll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping("/api")
public class PollController {

    @Autowired
    PollService pollService;

    public PollController(){}

    private static Logger LOG = LoggerFactory.getLogger(PollController.class);

    //Get
    @RequestMapping(method = RequestMethod.GET, value = "/poll/{pollId}")
    public @ResponseBody
    ResponseEntity<Poll> findPoll(@PathVariable Long pollId){
        if (LOG.isInfoEnabled()) LOG.info(pollId+" attempting to get poll. ");
        RequestReadPollEvent requestReadPollEvent = new RequestReadPollEvent(pollId);
        ReadPollEvent readPollEvent = pollService.requestReadPoll(requestReadPollEvent);
        if(readPollEvent.isEntityFound()){
            Poll poll = Poll.fromPollDetails(readPollEvent.getPollDetails());
            return new ResponseEntity<Poll>(poll, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Poll>(HttpStatus.NOT_FOUND);
        }
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = "/poll")
    public @ResponseBody
    ResponseEntity<Poll> createPoll(@RequestBody Poll poll){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create poll "+poll);
        CreatePollEvent createPollEvent = new CreatePollEvent(poll.toPollDetails());
        PollCreatedEvent pollCreatedEvent = pollService.createPoll(createPollEvent);
        if(pollCreatedEvent.getPollId() == null){
            return new ResponseEntity<Poll>(HttpStatus.BAD_REQUEST);
        }
        else{
            Poll result = Poll.fromPollDetails(pollCreatedEvent.getPollDetails());
            if (LOG.isDebugEnabled()) LOG.debug("poll"+result.toString());
            return new ResponseEntity<Poll>(result, HttpStatus.OK);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = "/poll/{pollId}")
    public @ResponseBody
    ResponseEntity<Poll> updatePoll(@PathVariable Long pollId, @RequestBody Poll poll){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update poll. " + pollId);
        PollUpdatedEvent pollUpdatedEvent = pollService.updatePoll(new UpdatePollEvent(pollId, poll.toPollDetails()));
        if ((null != pollUpdatedEvent )&& (LOG.isDebugEnabled())) LOG.debug("pollUpdatedEvent - "+pollUpdatedEvent);
        if(pollUpdatedEvent.isEntityFound()){
            Poll resultPoll = Poll.fromPollDetails(pollUpdatedEvent.getPollDetails());
            if (LOG.isDebugEnabled()) LOG.debug("resultPoll = "+resultPoll);
            return new ResponseEntity<Poll>(resultPoll, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Poll>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = "/poll/{pollId}")
    public @ResponseBody
    ResponseEntity<Boolean> deletePoll(@PathVariable Long pollId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete poll. " + pollId);
        PollDeletedEvent pollDeletedEvent = pollService.deletePoll(new DeletePollEvent(pollId));
        Boolean isDeletionCompleted = Boolean.valueOf(pollDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }
}
