/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

/**
 * @author Greg Newitt
 *
 */
public class PersonalityAddedEvent 
{
	private boolean userFound;
	private PersonalityDetails personalityDetails;
	
	/**
	 * 
	 */
	public PersonalityAddedEvent() 
	{
		super();
		userFound=true;
	}

	/**
	 * @param userFound
	 * @param personalityDetails
	 */
	public PersonalityAddedEvent(PersonalityDetails personalityDetails) 
	{
		this();
		this.personalityDetails = personalityDetails;
	}

	/**
	 * @return the personalityDetails
	 */
	public PersonalityDetails getPersonalityDetails() {
		return personalityDetails;
	}

	/**
	 * @param personalityDetails the personalityDetails to set
	 */
	public void setPersonalityDetails(PersonalityDetails personalityDetails) {
		this.personalityDetails = personalityDetails;
	}

	/**
	 * @param userFound the userFound to set
	 */
	public void setUserFound(boolean userFound) {
		this.userFound = userFound;
	}

	public boolean isUserFound()
	{
		return userFound;
	}
	public static PersonalityAddedEvent userNotFound()
	{
		PersonalityAddedEvent res=new PersonalityAddedEvent();
		res.setUserFound(false);
		return res;
	}
}
