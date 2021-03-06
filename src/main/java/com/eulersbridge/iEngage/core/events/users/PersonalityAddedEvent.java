/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 *
 */
public class PersonalityAddedEvent extends CreatedEvent
{
	private boolean userFound;
	
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
		super(personalityDetails);
		if (personalityDetails.getNodeId()!=null)
			userFound=true;
	}

	/**
	 * @return the personalityDetails
	 */
	public PersonalityDetails getPersonalityDetails()
	{
		return (PersonalityDetails) super.getDetails();
	}

	/**
	 * @param personalityDetails the personalityDetails to set
	 */
	public void setPersonalityDetails(PersonalityDetails personalityDetails) {
		setDetails(personalityDetails);
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PersonalityAddedEvent [userFound=" + userFound
				+ ", toString()=" + super.toString() + "]";
	}

	public static PersonalityAddedEvent userNotFound()
	{
		PersonalityAddedEvent res=new PersonalityAddedEvent();
		res.setUserFound(false);
		return res;
	}
}
