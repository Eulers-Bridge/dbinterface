package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Election 
{
	private String year;
	private String start;
	private String end;
	
    private static Logger LOG = LoggerFactory.getLogger(Election.class);

    public Election() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public Election(String year,String start,String end)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+year+','+start+','+end+')');
		this.year=year;
		this.start=start;
		this.end=end;
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
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ year = ");
		String retValue;
		buff.append(year);
		buff.append(", start = ");
		buff.append(start);
		buff.append(", end = ");
		buff.append(end);
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	


}
