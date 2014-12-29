/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails;
import com.eulersbridge.iEngage.rest.controller.PollController;

/**
 * @author Greg Newitt
 *
 */
public class PollAnswer extends ResourceSupport
{
    private Long nodeId;
    private Integer answerIndex;
    private Long timeStamp;
    private Long answererId;
    private Long pollId;

    private static Logger LOG = LoggerFactory.getLogger(Poll.class);

    public static PollAnswer fromPollAnswerDetails(PollAnswerDetails pollDetails){
        PollAnswer pollAnswer = new PollAnswer();
        String simpleName=Poll.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        pollAnswer.setNodeId(pollDetails.getNodeId());
        pollAnswer.setPollId(pollDetails.getPollId());
        pollAnswer.setAnswererId(pollDetails.getAnswererId());
        pollAnswer.setAnswerIndex(pollDetails.getAnswerIndex());
        pollAnswer.setTimeStamp(pollDetails.getTimeStamp());

	    // {!begin selfRel}
        pollAnswer.add(linkTo(PollController.class).slash(name).slash(pollAnswer.getNodeId()).withSelfRel());
	    // {!end selfRel}
	    // {!begin previous}
        pollAnswer.add(linkTo(PollController.class).slash(name).slash(pollAnswer.nodeId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
	    // {!end previous}
	    // {!begin next}
        pollAnswer.add(linkTo(PollController.class).slash(name).slash(pollAnswer.nodeId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
	    // {!end next}
	    // {!begin likedBy}
        pollAnswer.add(linkTo(PollController.class).slash(name).slash(pollAnswer.nodeId).slash(RestDomainConstants.LIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.LIKEDBY_LABEL));
	    // {!end likedBy}
	    // {!begin unlikedBy}
        pollAnswer.add(linkTo(PollController.class).slash(name).slash(pollAnswer.nodeId).slash(RestDomainConstants.UNLIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.UNLIKEDBY_LABEL));
	    // {!end unlikedBy}
	    // {!begin likes}
        pollAnswer.add(linkTo(PollController.class).slash(name).slash(pollAnswer.nodeId).slash(RestDomainConstants.LIKES).withRel(RestDomainConstants.LIKES_LABEL));
	    // {!end likes}
	    // {!begin readAll}
        pollAnswer.add(linkTo(PollController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}

        return pollAnswer;
    }

    public PollAnswerDetails toPollAnswerDetails()
    {
    	PollAnswerDetails pollDetails = new PollAnswerDetails(nodeId, answererId, pollId, answerIndex, null);
        pollDetails.setNodeId(this.getNodeId());
        pollDetails.setPollId(this.getPollId());
        pollDetails.setAnswererId(this.getAnswererId());
        pollDetails.setAnswerIndex(this.getAnswerIndex());
        pollDetails.setTimeStamp(this.getTimeStamp());
        if (LOG.isTraceEnabled()) LOG.trace("pollAnswerDetails "+pollDetails);
        return pollDetails;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long pollId) {
        this.nodeId = pollId;
    }

	/**
	 * @return the answererId
	 */
	public Long getAnswererId()
	{
		return answererId;
	}

	/**
	 * @param answererId the answererId to set
	 */
	public void setAnswererId(Long answererId)
	{
		this.answererId = answererId;
	}

	/**
	 * @return the pollId
	 */
	public Long getPollId()
	{
		return pollId;
	}

	/**
	 * @param pollId the pollId to set
	 */
	public void setPollId(Long pollId)
	{
		this.pollId = pollId;
	}

	/**
	 * @return the answerIndex
	 */
	public Integer getAnswerIndex()
	{
		return answerIndex;
	}

	/**
	 * @param answerIndex the answerIndex to set
	 */
	public void setAnswerIndex(Integer answerIndex)
	{
		this.answerIndex = answerIndex;
	}

	/**
	 * @return the timeStamp
	 */
	public Long getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

}
