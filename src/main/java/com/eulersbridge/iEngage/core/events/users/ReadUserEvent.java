package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class ReadUserEvent extends ReadEvent
{

	private String email;

	public ReadUserEvent(String email)
	{
		super(1l);
		this.email = email;
	}

	public ReadUserEvent(String email, UserDetails readUserDetails)
	{
		super(1l,readUserDetails);
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

	public UserDetails getReadUserDetails()
	{
		return (UserDetails)getDetails();
	}

	public static ReadUserEvent notFound(String id)
	{
		ReadUserEvent ev = new ReadUserEvent(id);
		ev.entityFound = false;
		return ev;
	}

}
