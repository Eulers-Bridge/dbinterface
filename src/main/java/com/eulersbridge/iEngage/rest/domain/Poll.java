package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.rest.controller.PollController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Poll extends ResourceSupport{
    private Long pollId;
    private String title;
    private Long electionStart;
    private Long start;
    private Long duration;

    private static Logger LOG = LoggerFactory.getLogger(Poll.class);

    public static Poll fromPollDetails(PollDetails pollDetails){
        Poll poll = new Poll();
        String simpleName=Poll.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        poll.setPollId(pollDetails.getPollId());
        poll.setTitle(pollDetails.getTitle());
        poll.setElectionStart(pollDetails.getElectionStart());
        poll.setStart(pollDetails.getStart());
        poll.setDuration(pollDetails.getDuration());

        poll.add(linkTo(PollController.class).slash(name).slash(poll.getPollId()).withSelfRel());
        poll.add(linkTo(PollController.class).slash(name).slash(poll.pollId).slash("previous").withRel("Previous"));
        poll.add(linkTo(PollController.class).slash(name).slash(poll.pollId).slash("next").withRel("Next"));

        return poll;
    }

    public PollDetails toPollDetails(){
        PollDetails pollDetails = new PollDetails();
//        BeanUtils.copyProperties(pollDetails, this);
        pollDetails.setPollId(this.getPollId());
        pollDetails.setTitle(this.getTitle());
        pollDetails.setElectionStart(this.getElectionStart());
        pollDetails.setStart(this.getStart());
        pollDetails.setDuration(this.getDuration());
        if (LOG.isTraceEnabled()) LOG.trace("pollDetails "+pollDetails);
        return pollDetails;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getElectionStart() {
        return electionStart;
    }

    public void setElectionStart(Long electionStart) {
        this.electionStart = electionStart;
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
}
