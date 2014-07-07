package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class ReadUserEvent extends ReadEvent 
{

	private String email;
	private UserDetails readUserDetails;
	
	  public ReadUserEvent(String email) {
	    this.email = email;
	  }

	  public ReadUserEvent(String email, UserDetails readUserDetails) {
	    this.email = email;
	    this.readUserDetails = readUserDetails;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public UserDetails getReadUserDetails() {
	    return readUserDetails;
	  }

	  public static ReadUserEvent notFound(String email) 
	  {
	  	ReadUserEvent ev = new ReadUserEvent(email);
	    ev.entityFound=false;
	    return ev;
	  }

}
