package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Election 
{
	@GraphId Long nodeId;
	private String year;
	private String start;
	private String end;
	private String votingStart;
	private String votingEnd;
	
    private static Logger LOG = LoggerFactory.getLogger(Election.class);

    public Election() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public Election(String year, String start, String end, String votingStart,
			String votingEnd) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Constructor("+year+','+start+','+end+','+votingStart+','+votingEnd+')');
		this.year=year;
		this.start=start;
		this.end=end;
		this.votingStart=votingStart;
		this.votingEnd=votingEnd;
	}

	public String getYear()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getYear() = "+year);
		return year;
	}

	public String getStart()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getStart() = "+start);
		return start;
	}
	
	public String getEnd()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getEnd() = "+end);
		return end;
	}
	
	public String getVotingStart()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getVotingStart() = "+votingStart);
		return votingStart;
	}
	
	public String getVotingEnd()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getVotingEnd() = "+votingEnd);
		return votingEnd;
	}
	
	public Long getNodeId()
	{
		return nodeId;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", year = ");
		buff.append(getYear());
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


}
