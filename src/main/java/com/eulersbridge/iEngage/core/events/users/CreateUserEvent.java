package com.eulersbridge.iEngage.core.events.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreateEvent;

public class CreateUserEvent extends CreateEvent 
{

	UserDetails userDetails;
	
    private static Logger LOG = LoggerFactory.getLogger(CreateUserEvent.class);
	
	public CreateUserEvent(String email, UserDetails userDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateUserEvent("+email+","+userDetails+") = ");
		this.userDetails=userDetails;
		userDetails.setEmail(email);
	}

	public CreateUserEvent(UserDetails userDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateUserEvent(userDetails) = ");
		this.userDetails=userDetails;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
