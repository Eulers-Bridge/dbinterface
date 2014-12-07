package com.eulersbridge.iEngage.rest.domain;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.rest.controller.PollController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Poll extends ResourceSupport{
    private Long nodeId;
    private String question;
    private String answers;
    private Long start;
    private Long duration;
    private Long ownerId;
    private Long creatorId;

    private static Logger LOG = LoggerFactory.getLogger(Poll.class);

    public static Poll fromPollDetails(PollDetails pollDetails){
        Poll poll = new Poll();
        String simpleName=Poll.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        poll.setNodeId(pollDetails.getPollId());
        poll.setQuestion(pollDetails.getQuestion());
        poll.setAnswers(pollDetails.getAnswers());
        poll.setStart(pollDetails.getStart());
        poll.setDuration(pollDetails.getDuration());
        poll.setOwnerId(pollDetails.getOwnerId());
        poll.setCreatorId(pollDetails.getCreatorId());

	    // {!begin selfRel}
        poll.add(linkTo(PollController.class).slash(name).slash(poll.getNodeId()).withSelfRel());
	    // {!end selfRel}
	    // {!begin previous}
        poll.add(linkTo(PollController.class).slash(name).slash(poll.nodeId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
	    // {!end previous}
	    // {!begin next}
        poll.add(linkTo(PollController.class).slash(name).slash(poll.nodeId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
	    // {!end next}
	    // {!begin likedBy}
        poll.add(linkTo(PollController.class).slash(name).slash(poll.nodeId).slash(RestDomainConstants.LIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.LIKEDBY_LABEL));
	    // {!end likedBy}
	    // {!begin unlikedBy}
        poll.add(linkTo(PollController.class).slash(name).slash(poll.nodeId).slash(RestDomainConstants.UNLIKEDBY).slash(RestDomainConstants.USERID).withRel(RestDomainConstants.UNLIKEDBY_LABEL));
	    // {!end unlikedBy}
	    // {!begin likes}
        poll.add(linkTo(PollController.class).slash(name).slash(poll.nodeId).slash(RestDomainConstants.LIKES).withRel(RestDomainConstants.LIKES_LABEL));
	    // {!end likes}
	    // {!begin readAll}
        poll.add(linkTo(PollController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}

        return poll;
    }

    public PollDetails toPollDetails(){
        PollDetails pollDetails = new PollDetails();
//        BeanUtils.copyProperties(pollDetails, this);
        pollDetails.setPollId(this.getNodeId());
        pollDetails.setQuestion(this.getQuestion());
        pollDetails.setAnswers(this.getAnswers());
        pollDetails.setStart(this.getStart());
        pollDetails.setDuration(this.getDuration());
        pollDetails.setOwnerId(getOwnerId());
        pollDetails.setCreatorId(getCreatorId());
        if (LOG.isTraceEnabled()) LOG.trace("pollDetails "+pollDetails);
        return pollDetails;
    }

	public static Iterator<Poll> toPollsIterator( Iterator<PollDetails> iter)
	{
		if (null==iter) return null;
		ArrayList <Poll> polls=new ArrayList<Poll>();
		while(iter.hasNext())
		{
			PollDetails dets=iter.next();
			Poll thisPoll=Poll.fromPollDetails(dets);
			Link self = thisPoll.getLink("self");
			thisPoll.removeLinks();
			thisPoll.add(self);
			polls.add(thisPoll);		
		}
		return polls.iterator();
	}
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long pollId) {
        this.nodeId = pollId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

	/**
	 * @return the ownerId
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Long newsFeedId)
	{
		this.ownerId = newsFeedId;
	}

	/**
	 * @return the creatorId
	 */
	public Long getCreatorId()
	{
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(Long creatorId)
	{
		this.creatorId = creatorId;
	}
}
