package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.CreateEvent;

public class UserSignUpEvent extends CreateEvent 
{

	UserDetails userDetails;
	
	public UserSignUpEvent(String email, UserDetails userDetails) 
	{
		this.userDetails=userDetails;
		userDetails.setEmail(email);
	}

	public UserSignUpEvent(UserDetails userDetails) 
	{
		this.userDetails=userDetails;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public static UserSignUpEvent notFound(String email) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
