package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Volunteer {
	
	private String id;
	private String title;
	private String description;
	
	private static Logger LOG = LoggerFactory.getLogger(Volunteer.class);
	
	public Volunteer()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public Volunteer(String id, String title, String description)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+id+','+title+','+description+')');
		this.id=id;
		this.title=title;
		this.description=description;
	}
	
	public String getId()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getId() = "+id);
		return id;
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
		StringBuffer buff=new StringBuffer("[ id = ");
		String retValue;
		buff.append(id);
		buff.append(", title = ");
		buff.append(title);
		buff.append(", description = ");
		buff.append(description);
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}

}
