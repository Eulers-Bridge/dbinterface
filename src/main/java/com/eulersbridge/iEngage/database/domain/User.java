package com.eulersbridge.iEngage.database.domain;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.eulersbridge.iEngage.core.events.users.UserDetails;

@NodeEntity
public class User 
{
	@GraphId Long nodeId;
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String nationality;
	private String yearOfBirth;
	private String personality;
	private String password;
	private boolean accountVerified=false;
	@RelatedTo(type = "USER_OF", direction=Direction.OUTGOING)
	@Fetch private Institution institution; 
	
    private static Logger LOG = LoggerFactory.getLogger(User.class);
    
	public User()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public User(String email,String firstName,String lastName,String gender, String nationality, String yearOfBirth, String personality, String password)
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
	
	public boolean comparePassword(String password)
	{
		return password.equals(this.password);
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

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	
//	public Password getPassword()
	public String getPassword()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getPassword() = "+password);
		return password;
	}
	
//	public void setPassword(Password password) 
	public void setPassword(String password) 
	{
		this.password = password;
	}

	/**
	 * @return the verified
	 */
	public boolean isAccountVerified() {
		return accountVerified;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setAccountVerified(boolean accountVerified) {
		this.accountVerified = accountVerified;
	}
	
	public UserDetails toUserDetails() 
	{
		    UserDetails details = new UserDetails();

		    BeanUtils.copyProperties(this, details);

		    return details;
	}

	  public static User fromUserDetails(UserDetails userDetails) {
		    User user = new User();

		    BeanUtils.copyProperties(userDetails, user);

		    return user;
		  }
	  
	  public boolean equals(User user2)
	  {
		  if (nodeId.equals(user2.nodeId)) return true;
		  else return false;
	  }

}
