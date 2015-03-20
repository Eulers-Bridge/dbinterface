package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class ReadUserEvent extends ReadEvent
{

	private String email;

	public ReadUserEvent(String email)
	{
		super(null);
		this.email = email;
	}

	public ReadUserEvent(String email, UserDetails readUserDetails)
	{
		super(null,readUserDetails);
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

	public static ReadUserEvent notFound(String id)
	{
		ReadUserEvent ev = new ReadUserEvent(id);
		ev.entityFound = false;
		return ev;
	}

}
