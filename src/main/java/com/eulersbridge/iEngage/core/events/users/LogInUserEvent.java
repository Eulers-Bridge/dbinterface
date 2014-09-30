/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Greg Newitt
 *
 */
public class LogInUserEvent 
{
	String username;
	Collection<? extends GrantedAuthority> roles;
	/**
	 * @param username
	 * @param roles
	 */
	public LogInUserEvent(String username,
			Collection<? extends GrantedAuthority> roles) 
	{
		super();
		this.username = username;
		this.roles = roles;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the roles
	 */
	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}
}
