package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

public class UpdateUserEvent extends UpdateEvent
{
	private String email;

	public UpdateUserEvent(String email, UserDetails userDetails)
	{
		super(1l,userDetails);
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}
}
