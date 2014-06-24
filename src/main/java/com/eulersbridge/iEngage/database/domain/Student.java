package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Student 
{
	@GraphId Long nodeId;
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String nationality;
	private String yearOfBirth;
	private String personality;
	
    private static Logger LOG = LoggerFactory.getLogger(Student.class);
    
	public Student()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public Student(String email,String firstName,String lastName,String gender, String nationality, String yearOfBirth, String personality)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+email+','+firstName+','+lastName+','+gender+','+
														  nationality+','+yearOfBirth+','+personality+')');
		this.email=email;
		this.firstName=firstName;
		this.lastName=lastName;
		this.gender=gender;
		this.nationality=nationality;
		this.yearOfBirth=yearOfBirth;
		this.personality=personality;
	}
	
	public String getEmail()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getEmail() = "+email);
		return email;
	}

	public String getFirstName()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getFirstName() = "+firstName);
		return firstName;
	}
	
	public String getLastName()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getLastName() = "+lastName);
		return lastName;
	}
	
	public String getGender()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getGender() = "+gender);
		return gender;
	}
	
	public String getNationality()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getNationality() = "+nationality);
		return nationality;
	}
	
	public String getYearOfBirth()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getYearOfBirth() = "+yearOfBirth);
		return yearOfBirth;
	}
	
	public String getPersonality()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getPersonality() = "+personality);
		return personality;
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
		buff.append(", email = ");
		buff.append(getEmail());
		buff.append(", firstName = ");
		buff.append(getFirstName());
		buff.append(", lastName = ");
		buff.append(getLastName());
		buff.append(", gender = ");
		buff.append(getGender());
		buff.append(", nationality = ");
		buff.append(getNationality());
		buff.append(", yearOfBirth = ");
		buff.append(getYearOfBirth());
		buff.append(", personality = ");
		buff.append(getPersonality());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	

}
