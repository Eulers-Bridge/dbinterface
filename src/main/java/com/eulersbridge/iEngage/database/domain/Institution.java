package com.eulersbridge.iEngage.database.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Institution 
{
	@GraphId Long nodeId;
	String name;
	String campus;
	String state;
	String country;
	@RelatedTo(type = "STUDENT_OF", direction=Direction.OUTGOING)
	Set<User> students;
	
    private static Logger LOG = LoggerFactory.getLogger(Institution.class);
	public Institution()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public Institution(String name,String campus,String state,String country)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+name+','+campus+','+state+','+country+')');
		this.name=name;
		this.campus=campus;
		this.state=state;
		this.country=country;
	}
	
	public String getName()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getName() = "+name);
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getCampus()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getCampus() = "+campus);
		return campus;
	}
	public void setCampus(String campus)
	{
		this.campus=campus;
	}
	public String getState()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getState() = "+state);
		return state;
	}
	public void setState(String state)
	{
		this.state=state;
	}
	public String getCountry()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getCountry() = "+country);
		return country;
	}
	public void setCountry(String country)
	{
		this.country=country;
	}
	
	public Long getNodeId()
	{
		return nodeId;
	}
	
	public void setNodeId(Long nodeId)
	{
		this.nodeId=nodeId;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", name = ");
		buff.append(getName());
		buff.append(", campus = ");
		buff.append(getCampus());
		buff.append(", state = ");
		buff.append(getState());
		buff.append(", country = ");
		buff.append(getCountry());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
}
