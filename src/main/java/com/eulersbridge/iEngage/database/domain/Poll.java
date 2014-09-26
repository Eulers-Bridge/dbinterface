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
    private String question;
    private String answers;
    private Long start;
    private Long duration;

    private static Logger LOG = LoggerFactory.getLogger(Poll.class);

    public Poll(){}

    public PollDetails toPollDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toPollDetails()");
        PollDetails pollDetails = new PollDetails();
        pollDetails.setPollId(this.getPollId());
        pollDetails.setQuestion(this.getQuestion());
        pollDetails.setAnswers(this.getAnswers());
        pollDetails.setStart(this.getStart());
        pollDetails.setDuration(this.getDuration());
        if (LOG.isTraceEnabled()) LOG.trace("pollDetails; "+ pollDetails);
        return pollDetails;
    }

    public static Poll fromPollDetails(PollDetails pollDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromPollDetails()");
        Poll poll = new Poll();
        poll.setPollId(pollDetails.getPollId());
        poll.setQuestion(pollDetails.getQuestion());
        poll.setAnswers(pollDetails.getAnswers());
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

    @Override
    public String toString() {
        StringBuffer buff=new StringBuffer("[ nodeId = ");
        String retValue;
        buff.append(getPollId());
        buff.append(", question = ");
        buff.append(getQuestion());
        buff.append(", answers = ");
        buff.append(getAnswers());
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
