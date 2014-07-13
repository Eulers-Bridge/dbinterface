package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

public class UpdateUserEvent extends UpdateEvent 
{
	  private String email;
	  private UserDetails userDetails;

	  public UpdateUserEvent(String email, UserDetails userDetails) {
	    this.email = email;
	    this.userDetails = userDetails;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public UserDetails getUserDetails() {
	    return userDetails;
	  }

}
