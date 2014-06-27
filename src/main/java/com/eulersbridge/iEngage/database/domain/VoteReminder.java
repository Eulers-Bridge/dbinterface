package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class VoteReminder {

	@GraphId Long nodeId;
	private String date;
	private String time;
	private String location;
	
    private static Logger LOG = LoggerFactory.getLogger(VoteReminder.class);

    public VoteReminder() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public VoteReminder(String date,String time,String location)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+date+','+time+','+location+')');
		this.date=date;
		this.time=time;
		this.location=location;
	}
	
	public Long getNodeId()
	{
		return nodeId;
	}
	
	public String getDate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getYear() = "+date);
		return date;
	}

	public String getTime()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getStart() = "+time);
		return time;
	}
	
	public String getLocation()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getEnd() = "+location);
		return location;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", date = ");
		buff.append(date);
		buff.append(", time = ");
		buff.append(time);
		buff.append(", location = ");
		buff.append(location);
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	


}
