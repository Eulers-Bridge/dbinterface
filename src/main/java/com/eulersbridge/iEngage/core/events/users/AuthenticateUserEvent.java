/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

/**
 * @author Greg Newitt
 *
 */
public class AuthenticateUserEvent 
{
	String userName;
	String password;

	public AuthenticateUserEvent(String name, String password) 
	{
		this.userName=name;
		this.password=password;
	}

	/**
	 * @return the name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

}
