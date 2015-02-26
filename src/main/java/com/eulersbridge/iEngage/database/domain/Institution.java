package com.eulersbridge.iEngage.database.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;

@NodeEntity
public class Institution extends Likeable
{
	@GraphId Long nodeId;
	String name;
	String campus;
	String state;
	@RelatedTo(type = DatabaseDomainConstants.INSTITUTIONS_LABEL, direction=Direction.BOTH) @Fetch
	private	Country  country;
	@RelatedTo(type = DatabaseDomainConstants.USERS_LABEL, direction=Direction.OUTGOING)
	Iterable<User> students;
	@RelatedTo(type=DatabaseDomainConstants.HAS_NEWS_FEED_LABEL, direction=Direction.OUTGOING)
	private NewsFeed newsFeed;
	
    private static Logger LOG = LoggerFactory.getLogger(Institution.class);
	public Institution()
	{
		if (LOG.isDebugEnabled()) LOG.debug("Constructor");
	}
	
	public Institution(String name,String campus,String state,Country country)
	{
		if (LOG.isDebugEnabled()) LOG.debug("Constructor("+name+','+campus+','+state+','+country+')');
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
	public Country getCountry()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getCountry() = "+country);
		return country;
	}
	public void setCountry(Country country)
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
	
	/**
	 * @return the students
	 */
	public Iterable<User> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(Set<User> students) {
		this.students = students;
	}

	/**
	 * @return the studentYears
	 */
	public NewsFeed getNewsFeed() {
		return newsFeed;
	}

	/**
	 * @param studentYears the studentYears to set
	 */
	public void setNewsFeed(NewsFeed newsFeed) {
		this.newsFeed = newsFeed;
	}

	@Override
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
		buff.append(", newsFeed = ");
		buff.append(getNewsFeed());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
	
	public InstitutionDetails toInstDetails() 
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toInstDetails()");
	    
	    InstitutionDetails details = new InstitutionDetails(getNodeId());
	    if (LOG.isTraceEnabled()) LOG.trace("institution "+this);

	    BeanUtils.copyProperties(this, details);
	    details.setCountryName(getCountry().getCountryName());
	    if (getNewsFeed()!=null)
	    	details.setNewsFeedId(getNewsFeed().getNodeId());
	    if (LOG.isTraceEnabled()) LOG.trace("instDetails "+details);

	    return details;
	}

	public static Institution fromInstDetails(InstitutionDetails instDetails) 
	{
		Institution inst = null;
		if (instDetails!=null)
		{
			if (LOG.isTraceEnabled()) LOG.trace("fromInstDetails("+instDetails+")");
		
		    inst = new Institution();
		    inst.nodeId=instDetails.getNodeId();
		    inst.name=instDetails.getName();
		    inst.campus=instDetails.getCampus();
		    inst.country=new Country();
		    inst.country.setCountryName(instDetails.getCountryName());
		    inst.state=instDetails.getState();
		    if (LOG.isTraceEnabled()) LOG.trace("inst "+inst);
		}
		return inst;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (nodeId!=null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((campus == null) ? 0 : campus.hashCode());
			result = prime * result + ((country == null) ? 0 : country.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((newsFeed == null) ? 0 : newsFeed.hashCode());
			result = prime * result + ((state == null) ? 0 : state.hashCode());
			result = prime * result
					+ ((students == null) ? 0 : students.hashCode());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Institution other = (Institution) obj;
		if (nodeId != null) 
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else 
		{
			if (other.nodeId != null)
				return false;

			if (campus == null) {
				if (other.campus != null)
					return false;
			} else if (!campus.equals(other.campus))
				return false;
			if (country == null) {
				if (other.country != null)
					return false;
			} else if (!country.equals(other.country))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (newsFeed == null) {
				if (other.newsFeed != null)
					return false;
			} else if (!newsFeed.equals(other.newsFeed))
				return false;
			if (state == null) {
				if (other.state != null)
					return false;
			} else if (!state.equals(other.state))
				return false;
			if (students == null) {
				if (other.students != null)
					return false;
			} else if (!students.equals(other.students))
				return false;
		}
		return true;
	}
	  
}
