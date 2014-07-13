package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class UserCreatedEvent extends CreatedEvent 
{
	private UserDetails userDetails;
	private String email;
	protected boolean instituteFound = true;

	public UserCreatedEvent(String email, UserDetails userDetails) 
	{
		this.userDetails=userDetails;
		this.email=email;
	}

	public UserCreatedEvent(String email) 
	{
		this.email=email;
	}

	public String getEmail() {
		return email;
	}

	public void setKey(String email) {
		this.email = email;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public UserDetails getUserDetails() 
	{
	    return userDetails;
	}
	

	public static UserCreatedEvent instituteNotFound(String email) 
	{
		UserCreatedEvent ev = new UserCreatedEvent(email);
	    ev.instituteFound=false;
	    return ev;
	}
	
	public boolean isInstituteFound() 
	{
		return instituteFound;
	}


}
