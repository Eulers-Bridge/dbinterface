package com.eulersbridge.iEngage.core.events.users;

public class VerifyUserAccountEvent {
	
	private String email;
	private String token;
	
	public VerifyUserAccountEvent(String email, String token)
	{
		this.email = email;
		this.token = token;
	}
	
	public String getEmail() {
	    return email;
	  }

	  public String getToken() {
	    return token;
	  }

}
