package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.email.EmailVerification;

public class UserCreatedEvent extends CreatedEvent 
{
	private String email;
	protected boolean instituteFound = true;
	private EmailVerification verifyEmail;
	private boolean userUnique=true;

	public UserCreatedEvent(String email, UserDetails userDetails, EmailVerification verifyEmail) 
	{
		super(userDetails);
		this.email=email;
		this.verifyEmail=verifyEmail;
	}

	public UserCreatedEvent(String email) 
	{
		this.email=email;
	}

	public String getEmail() {
		return email;
	}

	public void setKey(String email) {
		this.email = email;
	}

	public void setUserDetails(UserDetails userDetails) {
		setDetails(userDetails);
	}

	@Override
	public UserDetails getDetails() 
	{
	    return (UserDetails)super.getDetails();
	}
	
	public static UserCreatedEvent instituteNotFound(String email) 
	{
		UserCreatedEvent ev = new UserCreatedEvent(email);
	    ev.instituteFound=false;
	    return ev;
	}
	
	public boolean isInstituteFound() 
	{
		return instituteFound;
	}
	
	public boolean isUserUnique()
	{
		return userUnique;
	}

	public EmailVerification getVerificationEmail() 
	{
		return verifyEmail;
	}

	public void setVerificationEmail(EmailVerification verifyEmail) 
	{
		this.verifyEmail=verifyEmail;
	}

	public static UserCreatedEvent userNotUnique(String email) {
		UserCreatedEvent ev = new UserCreatedEvent(email);
	    ev.userUnique=false;
	    return ev;
	}


}
