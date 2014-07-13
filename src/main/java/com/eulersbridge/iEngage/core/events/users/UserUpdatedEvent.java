package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class UserUpdatedEvent extends UpdatedEvent 
{
	private String email;
	private UserDetails userDetails;
	protected boolean instituteFound = true;

	public UserUpdatedEvent(String email, UserDetails userDetails) 
	{
	    this.email = email;
	    this.userDetails = userDetails;
	}

	public UserUpdatedEvent(String email)
	{
	    this.email = email;
	}

	public String getEmail()
	{
	    return email;
	}

	public UserDetails getUserDetails()
	{
	    return userDetails;
	}

	public static UserUpdatedEvent instituteNotFound(String email) 
	{
		UserUpdatedEvent ev = new UserUpdatedEvent(email);
	    ev.instituteFound=false;
	    return ev;
	}
	
	public boolean isInstituteFound() 
	{
		return instituteFound;
	}

}
