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
    private String question;

    private static Logger LOG = LoggerFactory.getLogger(ForumQuestion.class);

    public ForumQuestion()
    {
    	if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static ForumQuestion fromForumQuestionDetails(ForumQuestionDetails forumQuestionDetails){
        ForumQuestion forumQuestion = new ForumQuestion();
        String simpleName = ForumQuestion.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        forumQuestion.setForumQuestionId(forumQuestionDetails.getNodeId());
        forumQuestion.setQuestion(forumQuestionDetails.getQuestion());

	    // {!begin selfRel}
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).withSelfRel());
	    // {!end selfRel}
	    // {!begin previous}
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
	    // {!end previous}
	    // {!begin next}
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
	    // {!end next}
	    // {!begin likedBy}
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).slash(RestDomainConstants.LIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.LIKEDBY_LABEL));
	    // {!end likedBy}
	    // {!begin unlikedBy}
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).slash(RestDomainConstants.UNLIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.UNLIKEDBY_LABEL));
	    // {!end unlikedBy}
	    // {!begin likes}
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name).slash(forumQuestion.forumQuestionId).slash(RestDomainConstants.LIKES).withRel(RestDomainConstants.LIKES_LABEL));
	    // {!end likes}
	    // {!begin readAll}
        forumQuestion.add(linkTo(ForumQuestionController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}
        return forumQuestion;
    }

    public ForumQuestionDetails toForumQuestionDetails(){
        ForumQuestionDetails forumQuestionDetails = new ForumQuestionDetails();
        forumQuestionDetails.setNodeId(this.getForumQuestionId());
        forumQuestionDetails.setQuestion(getQuestion());

        return forumQuestionDetails;
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }

    public void setForumQuestionId(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
