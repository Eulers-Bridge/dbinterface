package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.Elections.ElectionDetails;
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

    public ElectionDetails toElectionDetail()
    {
        if (LOG.isTraceEnabled()) LOG.trace("toElectionDetail()");

        ElectionDetails electionDetails = new ElectionDetails();
        if (LOG.isTraceEnabled()) LOG.trace("election "+this);
        electionDetails.setElectionId(this.getNodeId());
        electionDetails.setTitle(this.getTitle());
        electionDetails.setStart(this.getStart());
        electionDetails.setEnd(this.getEnd());
        electionDetails.setStartVoting(this.getVotingStart());
        electionDetails.setEndVoting(this.getVotingEnd());
        if (LOG.isTraceEnabled()) LOG.trace("electionDetail; "+ electionDetails);
        return electionDetails;
    }

    public static Election fromElectionDetails(ElectionDetails electionDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromElectionDetails()");
        Election election = new Election();
        if (LOG.isTraceEnabled()) LOG.trace("electionDetails "+electionDetails);
        election.setNodeId(electionDetails.getElectionId());
        election.setTitle(electionDetails.getTitle());
        election.setStart(electionDetails.getStart());
        election.setEnd(electionDetails.getEnd());
        election.setVotingStart(electionDetails.getStartVoting());
        election.setVotingEnd(electionDetails.getEndVoting());
        if (LOG.isTraceEnabled()) LOG.trace("election "+election);
        return election;
    }
}
