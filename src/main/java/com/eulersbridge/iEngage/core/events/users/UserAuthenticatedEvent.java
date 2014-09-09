/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Greg Newitt
 *
 */
public class UserAuthenticatedEvent 
{
	List<GrantedAuthority> grantedAuths;
	boolean authenticated=true;
	
	public UserAuthenticatedEvent(List<GrantedAuthority> grantedAuths)
	{
		this.grantedAuths=grantedAuths;
	}
	
	public UserAuthenticatedEvent()
	{
	}
	
	public static UserAuthenticatedEvent badCredentials()
	{
		UserAuthenticatedEvent evt=new UserAuthenticatedEvent();
		evt.authenticated=false;
		return evt;
	}


	/**
	 * @return the grantedAuths
	 */
	public List<GrantedAuthority> getGrantedAuths() {
		return grantedAuths;
	}


	/**
	 * @return the authenticated
	 */
	public boolean isAuthenticated() {
		return authenticated;
	}
}
