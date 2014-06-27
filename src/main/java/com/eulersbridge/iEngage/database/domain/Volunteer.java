package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Volunteer {
	
	@GraphId Long nodeId;
	private String title;
	private String description;
	
	private static Logger LOG = LoggerFactory.getLogger(Volunteer.class);
	
	public Volunteer()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public Volunteer(String title, String description)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+title+','+description+')');
		this.title=title;
		this.description=description;
	}
	
	public Long getNodeId()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getId() = "+nodeId);
		return nodeId;
	}
	
	public String getTitle()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getTitle() = "+title);
		return title;
	}
	
	public String getDescription()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getDescription() = "+description);
		return description;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", title = ");
		buff.append(getTitle());
		buff.append(", description = ");
		buff.append(getDescription());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}

}
