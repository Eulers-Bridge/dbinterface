package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class UserDeletedEvent extends DeletedEvent
{
	private String email;

	private UserDeletedEvent(String email)
	{
		// TODO fix shonky below.
		super(1l);
		this.email = email;
	}

	public UserDeletedEvent(String email, UserDetails details)
	{
		super(1l,details);
		this.email=email;
	}

	public String getEmail()
	{
		return email;
	}

	public static UserDeletedEvent deletionForbidden(String email,
			UserDetails details)
	{
		UserDeletedEvent ev = new UserDeletedEvent(email, details);
		ev.entityFound = true;
		ev.deletionCompleted = false;
		return ev;
	}

	public static UserDeletedEvent notFound(String email)
	{
		UserDeletedEvent ev = new UserDeletedEvent(email);
		ev.entityFound = false;
		ev.deletionCompleted = false;
		return ev;
	}

}
