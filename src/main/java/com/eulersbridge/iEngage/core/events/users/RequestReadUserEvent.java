package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

public class RequestReadUserEvent extends RequestReadEvent
{
	private String email;

	public RequestReadUserEvent(String email)
	{
		super(null);
		this.email = email;
	}

	public RequestReadUserEvent(Long id)
	{
		super(id);
		this.email = null;
	}

	public String getEmail()
	{
		return email;
	}

}
