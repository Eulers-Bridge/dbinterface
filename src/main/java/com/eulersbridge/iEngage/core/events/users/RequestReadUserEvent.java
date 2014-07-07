package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

public class RequestReadUserEvent extends RequestReadEvent 
{
	  private String email;

	  public RequestReadUserEvent(String email) {
	    this.email = email;
	  }

	  public String getEmail() {
	    return email;
	  }

}
