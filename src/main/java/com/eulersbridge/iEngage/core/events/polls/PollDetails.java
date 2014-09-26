package com.eulersbridge.iEngage.core.events.polls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PollDetails {
    private Long pollId;
    private String question;
    private String answers;
    private Long start;
    private Long duration;

    private static Logger LOG = LoggerFactory.getLogger(PollDetails.class);

    public PollDetails(){}

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
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getPollId());
        buff.append(", question = ");
        buff.append(getQuestion());
        buff.append(", answers = ");
        buff.append(getAnswers());
        buff.append(", start = ");
        buff.append(getStart());
        buff.append(", duration = ");
        buff.append(getDuration());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (obj ==this) return true;
        if(!(obj instanceof PollDetails)) return false;
        PollDetails pollDetails = (PollDetails) obj;
        if (pollDetails.getPollId() != null){
            if(pollDetails.getPollId().equals(getPollId()))
                return true;
        }
        return false;
    }
}
