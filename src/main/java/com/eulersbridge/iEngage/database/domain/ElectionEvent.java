package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class ElectionEvent {

	@GraphId Long nodeId;
	private String name;
	private String location;
	private Calendar date;
	private String description;
	private String picture;
	private Volunteer[] volunteers;
	private Calendar created;
	private Calendar modified;
	
    private static Logger LOG = LoggerFactory.getLogger(ElectionEvent.class);

	public ElectionEvent() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public ElectionEvent(String name,String location,Calendar date, String description, String picture, Volunteer[] volunteers, Calendar created, Calendar modified)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+name+','+location+','+date+','+description+','+picture+','+volunteers+','+created+','+modified+')');
		this.name=name;
		this.location=location;
		this.date=date;
		this.description=description;
		this.picture=picture;
		this.volunteers=volunteers;
		this.created=created;
		this.modified=modified;
	}
	
	public Long getNodeId()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getId() = "+nodeId);
		return nodeId;
	}
	
	public String getName()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getName() = "+name);
		return name;
	}

	public String getLocation()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getLocation() = "+location);
		return location;
	}
	
	public Calendar getDate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getDate() = "+date);
		return date;
	}
	
	public long getTime()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getTime() = "+date);
		return date.getTimeInMillis();
	}
	
	public String getDescription()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getDescription() = "+description);
		return description;
	}
	
	public String getPicture()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getPicture() = "+picture);
		return description;
	}
	
	public Volunteer[] getVolunteeers()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getVolunteeers() = "+volunteers);
		return volunteers;
	}
	
	public Calendar getCreated()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getCreated() = "+created);
		return created;
	}
	
	public Calendar getModified()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getModified() = "+modified);
		return modified;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(nodeId);
		buff.append(", name = ");
		buff.append(name);
		buff.append(", location = ");
		buff.append(location);
		buff.append(", date = ");
		buff.append(date);
		buff.append(", time = ");
		buff.append(date.HOUR_OF_DAY+":"+date.MINUTE);
		buff.append(", description = ");
		buff.append(description);
		buff.append(", picture = ");
		buff.append(picture);
		buff.append(", volunteers = {");
		for(Volunteer v : volunteers)
		{
			v.toString();
			buff.append(",");
		}
		buff.append("}, created = ");
		buff.append(created);
		buff.append(", modified = ");
		buff.append(modified);
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	


}
