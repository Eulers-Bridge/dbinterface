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
public class Institution 
{
	@GraphId Long nodeId;
	String name;
	String campus;
	String state;
	@RelatedTo(type = DatabaseDomainConstants.INSTITUTIONS_LABEL, direction=Direction.BOTH) @Fetch
	private	Country  country; 
	@RelatedTo(type = DatabaseDomainConstants.USERS_LABEL, direction=Direction.OUTGOING)
	Iterable<User> students;
	@RelatedTo(type=DatabaseDomainConstants.HAS_STUDENT_YEAR_LABEL, direction=Direction.OUTGOING)
	private Iterable<StudentYear> studentYears;
	
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
	public Iterable<StudentYear> getStudentYears() {
		return studentYears;
	}

	/**
	 * @param studentYears the studentYears to set
	 */
	public void setStudentYears(Iterable<StudentYear> studentYears) {
		this.studentYears = studentYears;
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
	    if (LOG.isTraceEnabled()) LOG.trace("instDetails "+details);

	    return details;
	}

	  public static Institution fromInstDetails(InstitutionDetails instDetails) 
	  {
		    if (LOG.isTraceEnabled()) LOG.trace("fromInstDetails()");

		    Institution inst = new Institution();
		    if (LOG.isTraceEnabled()) LOG.trace("instDetails "+instDetails);
		    inst.nodeId=instDetails.getInstitutionId();
		    inst.name=instDetails.getName();
		    inst.campus=instDetails.getCampus();
		    inst.country=new Country();
		    inst.country.setCountryName(instDetails.getCountryName());
		    inst.state=instDetails.getState();
		    if (LOG.isTraceEnabled()) LOG.trace("inst "+inst);

		    return inst;
		  }
	  
	@Override  
	public boolean equals(Object other)
	{
		if (null == other) return false;
		if (other == this) return true;
		if (!(other instanceof Institution)) return false;
		Institution institution2=(Institution) other;

		if ((nodeId!=null)&&(nodeId.equals(institution2.nodeId))) return true;
		return false;
	}
}
