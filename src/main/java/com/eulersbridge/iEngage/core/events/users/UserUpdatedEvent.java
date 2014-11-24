package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class UserUpdatedEvent extends UpdatedEvent 
{
	private String email;

	public UserUpdatedEvent(String email, UserDetails userDetails) 
	{
	    super(1l,userDetails);
	    this.email = email;
	}

	public UserUpdatedEvent(String email)
	{
		super(1l);
	    this.email = email;
	}

	public String getEmail()
	{
	    return email;
	}

	public static UserUpdatedEvent instituteNotFound(String email) 
	{
		UserUpdatedEvent ev = new UserUpdatedEvent(email);
	    ev.entityFound=false;
	    return ev;
	}	
}
