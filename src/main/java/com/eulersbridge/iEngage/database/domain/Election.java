package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.Elections.ElectionDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Election 
{
	@GraphId Long nodeId;
    private String title;
	private Long start;
	private Long end;
	private Long votingStart;
	private Long votingEnd;

    private static Logger LOG = LoggerFactory.getLogger(Election.class);

    public Election() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public Election(Long year, String title, Long start, Long end, Long votingStart,
                    Long votingEnd)
	{
		if (LOG.isDebugEnabled()) LOG.debug("Constructor("+year+','+start+','+end+','+votingStart+','+votingEnd+')');
		this.start=start;
		this.end=end;
		this.votingStart=votingStart;
		this.votingEnd=votingEnd;
	}

	public Long getStart()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getStart() = "+start);
		return start;
	}
	
	public Long getEnd()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getEnd() = "+end);
		return end;
	}
	
	public Long getVotingStart()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getVotingStart() = "+votingStart);
		return votingStart;
	}

    public String getTitle(){
        if (LOG.isDebugEnabled()) LOG.debug("getTitle() = "+title);
        return this.title;
    }
	
	public Long getVotingEnd()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getVotingEnd() = "+votingEnd);
		return votingEnd;
	}
	
	public Long getNodeId()
	{
		return nodeId;
	}

    public void setNodeId(Long nodeId){
        this.nodeId = nodeId;
    }

    public void setStart(Long start){
        this.start = start;
    }

    public void setEnd(Long end){
        this.end = end;
    }

    public void setVotingStart(Long votingStart){
        this.votingStart = votingStart;
    }

    public void setVotingEnd(Long votingEnd){
        this.votingEnd = votingEnd;
    }

    public void setTitle(String title){
        this.title = title;
    }
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", title = ");
		buff.append(getTitle());
		buff.append(", start = ");
		buff.append(getStart());
		buff.append(", end = ");
		buff.append(getEnd());
		buff.append(" , voting start = ");
		buff.append(getVotingStart());
		buff.append(", voting end = ");
		buff.append(getVotingEnd());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	

    public ElectionDetail toElectionDetail()
    {
        if (LOG.isTraceEnabled()) LOG.trace("toElectionDetail()");

        ElectionDetail electionDetail = new ElectionDetail();
        if (LOG.isTraceEnabled()) LOG.trace("election "+this);
        electionDetail.setElectionId(this.getNodeId());
        electionDetail.setTitle(this.getTitle());
        electionDetail.setStart(this.getStart());
        electionDetail.setEnd(this.getEnd());
        electionDetail.setStartVoting(this.getVotingStart());
        electionDetail.setEndVoting(this.getVotingEnd());
        if (LOG.isTraceEnabled()) LOG.trace("electionDetail; "+electionDetail);
        return electionDetail;
    }

    public Election fromElectionDetail(){
        //TODO
        Election election = new Election();
        return election;
    }
}
