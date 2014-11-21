package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.PollService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.LikeInfo;
import com.eulersbridge.iEngage.rest.domain.Poll;

import com.eulersbridge.iEngage.rest.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class PollController {

    @Autowired
    PollService pollService;

    @Autowired
    UserService userService;

    @Autowired
    LikesService likesService;

    public PollController(){}

    private static Logger LOG = LoggerFactory.getLogger(PollController.class);

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.POLL_LABEL+"/{pollId}")
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
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.POLL_LABEL)
    public @ResponseBody
    ResponseEntity<Poll> createPoll(@RequestBody Poll poll){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create poll "+poll);
        CreatePollEvent createPollEvent = new CreatePollEvent(poll.toPollDetails());
        PollCreatedEvent pollCreatedEvent = pollService.createPoll(createPollEvent);
        if(pollCreatedEvent.getPollId() == null){
            return new ResponseEntity<Poll>(HttpStatus.BAD_REQUEST);
        }
        else{
            Poll result = Poll.fromPollDetails(pollCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("poll"+result.toString());
            return new ResponseEntity<Poll>(result, HttpStatus.OK);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.POLL_LABEL+"{pollId}")
    public @ResponseBody
    ResponseEntity<Poll> updatePoll(@PathVariable Long pollId, @RequestBody Poll poll){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update poll. " + pollId);
        PollUpdatedEvent pollUpdatedEvent = pollService.updatePoll(new UpdatePollEvent(pollId, poll.toPollDetails()));
        if (null != pollUpdatedEvent )
        {
       		if (LOG.isDebugEnabled()) LOG.debug("pollUpdatedEvent - "+pollUpdatedEvent);
        	if(pollUpdatedEvent.isEntityFound())
        	{
	            Poll resultPoll = Poll.fromPollDetails(pollUpdatedEvent.getPollDetails());
	            if (LOG.isDebugEnabled()) LOG.debug("resultPoll = "+resultPoll);
	            return new ResponseEntity<Poll>(resultPoll, HttpStatus.OK);
        	}
        	else
        	{
        		return new ResponseEntity<Poll>(HttpStatus.NOT_FOUND);
        	}
        }
        else
    		return new ResponseEntity<Poll>(HttpStatus.BAD_REQUEST);
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.POLL_LABEL+"/{pollId}")
    public @ResponseBody
    ResponseEntity<Boolean> deletePoll(@PathVariable Long pollId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete poll. " + pollId);
        PollDeletedEvent pollDeletedEvent = pollService.deletePoll(new DeletePollEvent(pollId));
        Boolean isDeletionCompleted = Boolean.valueOf(pollDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }

    //like
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{pollId}/likedBy/{email}/")
    public @ResponseBody ResponseEntity<Boolean> likePoll(@PathVariable Long pollId,@PathVariable String email)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to have "+email+" like poll. "+pollId);
        LikedEvent likedPollEvent = userService.like(new LikeEvent(pollId, email));
        ResponseEntity<Boolean> response;
        if (!likedPollEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!likedPollEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restNews=likedPollEvent.isResultSuccess();
            response = new ResponseEntity<Boolean>(restNews,HttpStatus.OK);
        }
        return response;
    }

    //unlike
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{pollId}/unlikedBy/{email}/")
    public @ResponseBody ResponseEntity<Boolean> unlikePoll(@PathVariable Long pollId,@PathVariable String email)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to have "+email+" unlike poll. "+pollId);
        LikedEvent unlikedPollEvent = userService.unlike(new LikeEvent(pollId, email));
        ResponseEntity<Boolean> response;
        if (!unlikedPollEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!unlikedPollEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restNews = unlikedPollEvent.isResultSuccess();
            response = new ResponseEntity<Boolean>(restNews,HttpStatus.OK);
        }
        return response;
    }

    //likes
    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.NEWS_ARTICLE_LABEL+"/{pollId}" + ControllerConstants.LIKES_LABEL)
    public @ResponseBody ResponseEntity<Iterator<LikeInfo>> findLikes(
            @PathVariable Long pollId,
            @RequestParam(value="direction",required=false,defaultValue=ControllerConstants.DIRECTION) String direction,
            @RequestParam(value="page",required=false,defaultValue=ControllerConstants.PAGE_NUMBER) String page,
            @RequestParam(value="pageSize",required=false,defaultValue=ControllerConstants.PAGE_LENGTH) String pageSize)
    {
        int pageNumber = 0;
        int pageLength = 10;
        pageNumber = Integer.parseInt(page);
        pageLength = Integer.parseInt(pageSize);
        if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve liked users from poll "+pollId+'.');
        Direction sortDirection = Direction.DESC;
        if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
        LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(new LikesLikeableObjectEvent(pollId), sortDirection, pageNumber, pageLength);
        Iterator<LikeInfo> likes = User.toLikesIterator(likeableObjectLikesEvent.getUserDetails().iterator());
        if (likes.hasNext() == false){
            ReadPollEvent readPollEvent=pollService.requestReadPoll(new RequestReadPollEvent(pollId));
            if (!readPollEvent.isEntityFound())
                return new ResponseEntity<Iterator<LikeInfo>>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
    }

}
