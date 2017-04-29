/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		this.grantedAuths=new ArrayList<GrantedAuthority>();
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
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("grantedAuths - ");
		Iterator<GrantedAuthority> iter=grantedAuths.iterator();
		while (iter.hasNext())
		{
			buff.append(iter.next().toString()+',');
		}
		buff.append("\nauthenticated - "+isAuthenticated());
		return buff.toString();
	}
}
