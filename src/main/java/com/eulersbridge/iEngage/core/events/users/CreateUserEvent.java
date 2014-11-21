package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.CreateEvent;

public class CreateUserEvent extends CreateEvent 
{

	UserDetails userDetails;
	
	public CreateUserEvent(UserDetails userDetails) 
	{
		super(userDetails);
	}
}
