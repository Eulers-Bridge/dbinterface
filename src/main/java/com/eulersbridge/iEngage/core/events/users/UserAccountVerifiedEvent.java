package com.eulersbridge.iEngage.core.events.users;

public class UserAccountVerifiedEvent {
	
	private String email;
	private UserDetails userDetails;
	private boolean accountVerified = false;
	private VerificationErrorType verificationErrorType; 
	
	public UserAccountVerifiedEvent(String email)
	{
		this.email = email;
	}
	
	public UserAccountVerifiedEvent(String email, UserDetails userDetails)
	{
		this.email = email;
		this.userDetails = userDetails;
	}
	
	public UserAccountVerifiedEvent(String email, UserDetails userDetails, boolean accountVerified)
	{
		this.email = email;
		this.userDetails = userDetails;
		this.accountVerified = accountVerified;
	}
	
	public String getEmail()
	{
	    return email;
	}

	public UserDetails getUserDetails()
	{
	    return userDetails;
	}
	
	public boolean isAccountVerified()
	{
		return accountVerified;
	}
	
	public VerificationErrorType getVerificationError()
	{
		return verificationErrorType;
	}
	
	public void setVerificationError(VerificationErrorType verificationErrorType)
	{
		this.accountVerified = false;
		this.verificationErrorType = verificationErrorType;
	}
	
	public enum VerificationErrorType 
    {
        tokenExpired, tokenAlreadyUsed, tokenDoesntExists, userNotFound, tokenTypeMismatch, tokenUserMismatch
    }
}
