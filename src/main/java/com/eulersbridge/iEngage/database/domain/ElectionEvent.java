package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElectionEvent {

	private String name;
	private String location;
	private String date;
	private String time;
	
    private static Logger LOG = LoggerFactory.getLogger(ElectionEvent.class);

	public ElectionEvent() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public ElectionEvent(String name,String location,String date, String time)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+name+','+location+','+date+','+time+')');
		this.name=name;
		this.location=location;
		this.date=date;
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
	
	public String getDate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getDate() = "+date);
		return date;
	}
	
	public String getTime()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getTime() = "+time);
		return time;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ name = ");
		String retValue;
		buff.append(name);
		buff.append(", location = ");
		buff.append(location);
		buff.append(", date = ");
		buff.append(date);
		buff.append(", time = ");
		buff.append(time);
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	


}
