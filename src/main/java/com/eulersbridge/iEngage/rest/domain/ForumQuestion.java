package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails;
import com.eulersbridge.iEngage.rest.controller.ForumQuestionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * @author Yikai Gong
 */

public class ForumQuestion extends ResourceSupport {
    private Long forumQuestionId;
    //TODO Add other properties

    private static Logger LOG = LoggerFactory.getLogger(ForumQuestion.class);

    public ForumQuestion(){}

    public static ForumQuestion fromForumQuestionDetails(ForumQuestionDetails forumQuestionDetails){
        ForumQuestion forumQuestion = new ForumQuestion();
        String simpleName = ForumQuestion.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        forumQuestion.setForumQuestionId(forumQuestionDetails.getForumQuestionId());
        //TODO Add other properties

        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).withSelfRel());
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).slash("previous").withRel("Previous"));
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).slash("next").withRel("Next"));
        return forumQuestion;
    }

    public ForumQuestionDetails toForumQuestionDetails(){
        ForumQuestionDetails forumQuestionDetails = new ForumQuestionDetails();
        forumQuestionDetails.setForumQuestionId(this.getForumQuestionId());
        //TODO Add other properties

        return forumQuestionDetails;
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }

    public void setForumQuestionId(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }
}
