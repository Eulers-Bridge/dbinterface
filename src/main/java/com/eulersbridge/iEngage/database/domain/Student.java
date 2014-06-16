package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Student 
{
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
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ email = ");
		String retValue;
		buff.append(email);
		buff.append(", firstName = ");
		buff.append(firstName);
		buff.append(", lastName = ");
		buff.append(lastName);
		buff.append(", gender = ");
		buff.append(gender);
		buff.append(", nationality = ");
		buff.append(nationality);
		buff.append(", yearOfBirth = ");
		buff.append(yearOfBirth);
		buff.append(", personality = ");
		buff.append(personality);
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	

}
