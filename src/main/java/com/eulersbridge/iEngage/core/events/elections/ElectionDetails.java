package com.eulersbridge.iEngage.core.events.elections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ElectionDetails {
    private Long electionId;
    private String title;
    private Long start;
    private Long end;
    private Long startVoting;
    private Long endVoting;

    private static Logger LOG = LoggerFactory.getLogger(ElectionDetails.class);

    public ElectionDetails(){}

    public void setElectionId(Long electionId){
        this.electionId = electionId;
    }
    public Long getElectionId(){
        return this.electionId;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setStart(Long start){
        this.start = start;
    }
    public Long getStart(){
        return this.start;
    }

    public void setEnd(Long end){
        this.end = end;
    }
    public Long getEnd(){
        return this.end;
    }

    public void setStartVoting(Long startVoting){
        this.startVoting = startVoting;
    }
    public Long getStartVoting(){
        return this.startVoting;
    }

    public void setEndVoting(Long endVoting){
        this.endVoting = endVoting;
    }
    public Long getEndVoting(){
        return this.endVoting;
    }

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getElectionId());
        buff.append(", title = ");
        buff.append(getTitle());
        buff.append(", start = ");
        buff.append(getStart());
        buff.append(", end = ");
        buff.append(getEnd());
        buff.append(", startVoting = ");
        buff.append(getStartVoting());
        buff.append(", endVoting = ");
        buff.append(getEndVoting());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public boolean equals(Object other){
        if (null == other) return false;
        if (other ==this) return true;
        if(!(other instanceof ElectionDetails)) return false;
        ElectionDetails electionDetails2 = (ElectionDetails) other;
        if (electionDetails2.getElectionId() != null){
            if(electionDetails2.getElectionId().equals(getElectionId()))
                return true;
        }
        return false;
    }
}
