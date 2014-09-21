package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.polls.ReadPollEvent;
import com.eulersbridge.iEngage.core.events.polls.RequestReadPollEvent;
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

}
