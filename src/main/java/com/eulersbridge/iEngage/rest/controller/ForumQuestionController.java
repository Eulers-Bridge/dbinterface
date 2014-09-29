package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.forumQuestions.CreateForumQuestionEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionCreatedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.ReadForumQuestionEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.RequestReadForumQuestionEvent;
import com.eulersbridge.iEngage.core.services.ForumQuestionService;
import com.eulersbridge.iEngage.rest.domain.ForumQuestion;
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
public class ForumQuestionController {
    @Autowired
    ForumQuestionService forumQuestionService;

    public ForumQuestionController() {
    }

    private static Logger LOG = LoggerFactory.getLogger(ForumQuestionController.class);

    //Create
    @RequestMapping(method = RequestMethod.POST, value = "/forum")
    public @ResponseBody
    ResponseEntity<ForumQuestion> createForumQuestion(@RequestBody ForumQuestion forumQuestion){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create forumQuestion "+forumQuestion);
        CreateForumQuestionEvent createForumQuestionEvent = new CreateForumQuestionEvent(forumQuestion.toForumQuestionDetails());
        ForumQuestionCreatedEvent forumQuestionCreatedEvent = forumQuestionService.createForumQuestion(createForumQuestionEvent);
        if(forumQuestionCreatedEvent.getForumQuestionId() == null){
            return new ResponseEntity<ForumQuestion>(HttpStatus.BAD_REQUEST);
        }
        else{
            ForumQuestion result = ForumQuestion.fromForumQuestionDetails(forumQuestionCreatedEvent.getForumQuestionDetails());
            if (LOG.isDebugEnabled()) LOG.debug("forumQuestion"+result.toString());
            return new ResponseEntity<ForumQuestion>(result, HttpStatus.OK);
        }
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = "/forum/{forumQuestionId}")
    public @ResponseBody
    ResponseEntity<ForumQuestion> findEvent(@PathVariable Long forumQuestionId){
        if (LOG.isInfoEnabled()) LOG.info(forumQuestionId+" attempting to get forumQuestion. ");
        RequestReadForumQuestionEvent requestReadForumQuestionEvent = new RequestReadForumQuestionEvent(forumQuestionId);
        ReadForumQuestionEvent readForumQuestionEvent = forumQuestionService.requestReadForumQuestion(requestReadForumQuestionEvent);
        if(readForumQuestionEvent.isEntityFound()){
            ForumQuestion forumQuestion = ForumQuestion.fromForumQuestionDetails(readForumQuestionEvent.getForumQuestionDetails());
            return new ResponseEntity<ForumQuestion>(forumQuestion, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<ForumQuestion>(HttpStatus.NOT_FOUND);
        }
    }

    //Update

    //Delete

}
