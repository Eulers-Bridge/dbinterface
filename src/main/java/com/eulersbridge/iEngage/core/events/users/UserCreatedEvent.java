package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class UserCreatedEvent extends CreatedEvent 
{
	private UserDetails userDetails;
	private Long key;

	public UserCreatedEvent(Long nodeId, UserDetails userDetails) 
	{
		this.userDetails=userDetails;
		this.key=nodeId;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public UserDetails getUserDetails() 
	{
	    return userDetails;
	}

}
