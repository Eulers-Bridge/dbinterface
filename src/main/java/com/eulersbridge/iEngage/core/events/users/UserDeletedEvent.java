package com.eulersbridge.iEngage.core.events.users;


import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class UserDeletedEvent extends DeletedEvent
{
	  private String email;
	  private UserDetails details;
	  private boolean deletionCompleted;

	  private UserDeletedEvent(String email) 
	  {
		    this.email = email;
	  }

	  public UserDeletedEvent(String email, UserDetails details) {
	    this.email = email;
	    this.details = details;
	    this.deletionCompleted = true;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public UserDetails getDetails() {
	    return details;
	  }

	  public boolean isDeletionCompleted() {
	    return deletionCompleted;
	  }

	  public static UserDeletedEvent deletionForbidden(String email, UserDetails details) {
	    UserDeletedEvent ev = new UserDeletedEvent(email, details);
	    ev.entityFound=true;
	    ev.deletionCompleted=false;
	    return ev;
	  }

	  public static UserDeletedEvent notFound(String email) {
	    UserDeletedEvent ev = new UserDeletedEvent(email);
	    ev.entityFound=false;
	    return ev;
	  }

}
