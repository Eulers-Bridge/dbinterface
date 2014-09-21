package com.eulersbridge.iEngage.core.events.polls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PollDetails {
    private Long pollId;
    private String title;
    private Long electionStart;
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
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getPollId());
        buff.append(", title = ");
        buff.append(getTitle());
        buff.append(", electionStart = ");
        buff.append(getElectionStart());
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
