package com.eulersbridge.iEngage.core.events.users;


import com.eulersbridge.iEngage.core.events.DeleteEvent;

public class DeleteUserEvent extends DeleteEvent 
{
	  private final String email;

	  public DeleteUserEvent(final String email) {
	    this.email = email;
	  }

	  public String getEmail() {
	    return email;
	  }

}
