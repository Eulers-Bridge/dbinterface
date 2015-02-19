package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.*;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.services.ForumQuestionService;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.ForumQuestion;
import com.eulersbridge.iEngage.rest.domain.ForumQuestions;
import com.eulersbridge.iEngage.rest.domain.LikeInfo;
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
public class ForumQuestionController {
    @Autowired
    ForumQuestionService forumQuestionService;
    @Autowired
    UserService userService;
    @Autowired
    LikesService likesService;

    public ForumQuestionController()
    {
    }

    private static Logger LOG = LoggerFactory.getLogger(ForumQuestionController.class);

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.FORUM_QUESTION_LABEL)
    public @ResponseBody
    ResponseEntity<ForumQuestion> createForumQuestion(@RequestBody ForumQuestion forumQuestion)
    {
        if (LOG.isInfoEnabled()) LOG.info("attempting to create forumQuestion "+forumQuestion);
        CreateForumQuestionEvent createForumQuestionEvent = new CreateForumQuestionEvent(forumQuestion.toForumQuestionDetails());
        ForumQuestionCreatedEvent forumQuestionCreatedEvent = forumQuestionService.createForumQuestion(createForumQuestionEvent);
       
		ResponseEntity<ForumQuestion> response;
		if (null == forumQuestionCreatedEvent)
		{
			response = new ResponseEntity<ForumQuestion>(HttpStatus.BAD_REQUEST);
		}
		else if ((null == forumQuestionCreatedEvent.getDetails())
				|| (null == forumQuestionCreatedEvent.getDetails().getNodeId()))
		{
			response = new ResponseEntity<ForumQuestion>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			ForumQuestion result = ForumQuestion.fromForumQuestionDetails((ForumQuestionDetails) forumQuestionCreatedEvent
					.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("forumQuestion"+result.toString());
			return new ResponseEntity<ForumQuestion>(result, HttpStatus.CREATED);
		}
		return response;

    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.FORUM_QUESTIONS_LABEL+"/{ownerId}")
    public @ResponseBody
    ResponseEntity<ForumQuestions> findForumQuestions(@PathVariable(value = "") Long ownerId
    		,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize
			)
    {
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve photoAlbums for owner " + ownerId + '.');
		
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;

		ResponseEntity<ForumQuestions> response;

		ForumQuestionsReadEvent event = forumQuestionService.findForumQuestions(
				new ReadForumQuestionsEvent(ownerId), sortDirection, pageNumber,
				pageLength);

		if (!event.isEntityFound())
		{
			response = new ResponseEntity<ForumQuestions>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<ForumQuestion> fqIter = ForumQuestion.toForumQuestionsIterator(event.getForumQuestions().iterator());
			ForumQuestions fQuestions = ForumQuestions.fromForumQuestionsIterator(fqIter,
					event.getTotalEvents(), event.getTotalPages());
			response = new ResponseEntity<ForumQuestions>(fQuestions, HttpStatus.OK);
		}
		return response;
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.FORUM_QUESTION_LABEL+"/{forumQuestionId}")
    public @ResponseBody
    ResponseEntity<ForumQuestion> readForumQuestion(@PathVariable Long forumQuestionId){
        if (LOG.isInfoEnabled()) LOG.info(forumQuestionId+" attempting to get forumQuestion. ");
        ReadForumQuestionEvent readForumQuestionEvent = new ReadForumQuestionEvent(forumQuestionId);
        ReadEvent forumQuestionReadEvent = forumQuestionService.readForumQuestion(readForumQuestionEvent);
        if(forumQuestionReadEvent.isEntityFound()){
            ForumQuestion forumQuestion = ForumQuestion.fromForumQuestionDetails((ForumQuestionDetails)forumQuestionReadEvent.getDetails());
            return new ResponseEntity<ForumQuestion>(forumQuestion, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<ForumQuestion>(HttpStatus.NOT_FOUND);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.FORUM_QUESTION_LABEL+"/{forumQuestionId}")
    public @ResponseBody
    ResponseEntity<ForumQuestion> updateForumQuestion(@PathVariable Long forumQuestionId, @RequestBody ForumQuestion forumQuestion){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update forumQuestion. " + forumQuestionId);
        UpdatedEvent forumQuestionUpdatedEvent = forumQuestionService.updateForumQuestion(new UpdateForumQuestionEvent(forumQuestionId, forumQuestion.toForumQuestionDetails()));
        if ((null != forumQuestionUpdatedEvent))
        {
            if (LOG.isDebugEnabled()) LOG.debug("forumQuestionUpdatedEvent - "+forumQuestionUpdatedEvent);
            if(forumQuestionUpdatedEvent.isEntityFound())
            {
                ForumQuestion result = ForumQuestion.fromForumQuestionDetails((ForumQuestionDetails) forumQuestionUpdatedEvent.getDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<ForumQuestion>(result, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<ForumQuestion>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<ForumQuestion>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.FORUM_QUESTION_LABEL+"/{forumQuestionId}")
    public @ResponseBody
    ResponseEntity<Boolean> deleteForumQuestion(@PathVariable Long forumQuestionId)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete forumQuestion. " + forumQuestionId);
		ResponseEntity<Boolean> response;
		DeletedEvent forumQuestionDeletedEvent = forumQuestionService.deleteForumQuestion(new DeleteForumQuestionEvent(forumQuestionId));
		
		if (forumQuestionDeletedEvent.isDeletionCompleted())
			response = new ResponseEntity<Boolean>(
					forumQuestionDeletedEvent.isDeletionCompleted(), HttpStatus.OK);
		else if (forumQuestionDeletedEvent.isEntityFound())
			response = new ResponseEntity<Boolean>(
					forumQuestionDeletedEvent.isDeletionCompleted(), HttpStatus.GONE);
		else response = new ResponseEntity<Boolean>(
				forumQuestionDeletedEvent.isDeletionCompleted(), HttpStatus.NOT_FOUND);
		return response;
    }

    //like
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.FORUM_QUESTION_LABEL+"/{forumQuestionId}/likedBy/{email}/")
    public @ResponseBody ResponseEntity<Boolean> like(@PathVariable Long forumQuestionId,@PathVariable String email)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to have "+email+" like forumQuestion. "+forumQuestionId);
        LikedEvent likedEvent = likesService.like(new LikeEvent(forumQuestionId, email));
        ResponseEntity<Boolean> response;
        if (!likedEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!likedEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restNews=likedEvent.isResultSuccess();
            response = new ResponseEntity<Boolean>(restNews,HttpStatus.OK);
        }
        return response;
    }

    //unlike
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.FORUM_QUESTION_LABEL+"/{forumQuestionId}/unlikedBy/{email}/")
    public @ResponseBody ResponseEntity<Boolean> unlike(@PathVariable Long forumQuestionId,@PathVariable String email)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to have "+email+" unlike forumQuestion. "+forumQuestionId);
        LikedEvent unlikedEvent = likesService.unlike(new LikeEvent(forumQuestionId, email));
        ResponseEntity<Boolean> response;
        if (!unlikedEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!unlikedEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restNews = unlikedEvent.isResultSuccess();
            response = new ResponseEntity<Boolean>(restNews,HttpStatus.OK);
        }
        return response;
    }

    //likes
    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.FORUM_QUESTION_LABEL+"/{forumQuestionId}" + ControllerConstants.LIKES_LABEL)
    public @ResponseBody ResponseEntity<Iterator<LikeInfo>> findLikes(
            @PathVariable Long forumQuestionId,
            @RequestParam(value="direction",required=false,defaultValue=ControllerConstants.DIRECTION) String direction,
            @RequestParam(value="page",required=false,defaultValue=ControllerConstants.PAGE_NUMBER) String page,
            @RequestParam(value="pageSize",required=false,defaultValue=ControllerConstants.PAGE_LENGTH) String pageSize)
    {
        int pageNumber = 0;
        int pageLength = 10;
        pageNumber = Integer.parseInt(page);
        pageLength = Integer.parseInt(pageSize);
        if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve liked users from forumQuestion "+forumQuestionId+'.');
        Direction sortDirection = Direction.DESC;
        if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
        LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(new LikesLikeableObjectEvent(forumQuestionId), sortDirection, pageNumber, pageLength);
        Iterator<LikeInfo> likes = User.toLikesIterator(likeableObjectLikesEvent.getUserDetails().iterator());
        if (likes.hasNext() == false){
            ReadEvent readPollEvent=forumQuestionService.readForumQuestion(new ReadForumQuestionEvent(forumQuestionId));
            if (!readPollEvent.isEntityFound())
                return new ResponseEntity<Iterator<LikeInfo>>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
    }
}
