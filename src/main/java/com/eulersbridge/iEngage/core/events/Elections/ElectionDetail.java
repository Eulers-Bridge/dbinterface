package com.eulersbridge.iEngage.core.events.Elections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ElectionDetail {
    private Long electionId;
    private Long start;
    private Long end;
    private Long startVoting;
    private Long endVoting;

    private static Logger LOG = LoggerFactory.getLogger(ElectionDetail.class);

    public ElectionDetail(){}

    public void setElectionId(Long electionId){
        this.electionId = electionId;
    }
    public Long getElectionId(){
        return this.electionId;
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
        if(!(other instanceof ElectionDetail)) return false;
        ElectionDetail electionDetail2 = (ElectionDetail) other;
        if (electionDetail2.getElectionId() != null){
            if(electionDetail2.getElectionId().equals(getElectionId()))
                return true;
        }
        return false;
    }
}
