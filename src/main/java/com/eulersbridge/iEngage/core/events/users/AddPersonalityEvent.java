/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

/**
 * @author Greg Newitt
 *
 */
public class AddPersonalityEvent 
{
	String email;
	PersonalityDetails details;
	/**
	 * @param email
	 * @param details
	 */
	public AddPersonalityEvent(String email, PersonalityDetails details) 
	{
		super();
		this.email = email;
		this.details = details;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the details
	 */
	public PersonalityDetails getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(PersonalityDetails details) {
		this.details = details;
	}
	
	
}
