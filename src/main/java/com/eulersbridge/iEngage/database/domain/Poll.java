package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Poll {
    @GraphId private Long pollId;
    private String title;
    private Long electionStart;
    private Long start;
    private Long duration;

    private static Logger LOG = LoggerFactory.getLogger(Poll.class);

    public Poll(){}

    public PollDetails toPollDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toPollDetails()");
        PollDetails pollDetails = new PollDetails();
        pollDetails.setPollId(this.getPollId());
        pollDetails.setTitle(this.getTitle());
        pollDetails.setElectionStart(this.getElectionStart());
        pollDetails.setStart(this.getStart());
        pollDetails.setDuration(this.getDuration());
        if (LOG.isTraceEnabled()) LOG.trace("pollDetails; "+ pollDetails);
        return pollDetails;
    }

    public static Poll fromPollDetails(PollDetails pollDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromPollDetails()");
        Poll poll = new Poll();
        poll.setPollId(pollDetails.getPollId());
        poll.setTitle(pollDetails.getTitle());
        poll.setElectionStart(pollDetails.getElectionStart());
        poll.setStart(pollDetails.getStart());
        poll.setDuration(pollDetails.getDuration());
        if (LOG.isTraceEnabled()) LOG.trace("poll "+poll);
        return poll;
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

    @Override
    public String toString() {
        StringBuffer buff=new StringBuffer("[ nodeId = ");
        String retValue;
        buff.append(getPollId());
        buff.append(", title = ");
        buff.append(getTitle());
        buff.append(", electionStart = ");
        buff.append(getElectionStart());
        buff.append(", start = ");
        buff.append(getStart());
        buff.append(" , duration = ");
        buff.append(getDuration());
        buff.append(" ]");
        retValue=buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }
}
