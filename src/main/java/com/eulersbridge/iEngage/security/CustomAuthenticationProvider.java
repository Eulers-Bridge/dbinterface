/**
 * 
 */
package com.eulersbridge.iEngage.security;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.eulersbridge.iEngage.core.events.users.AuthenticateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent;
import com.eulersbridge.iEngage.core.services.UserService;

/**
 * @author Greg Newitt
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider 
{
	UserService userService;
	
	CustomAuthenticationProvider()
	{
		super();
	}
	public CustomAuthenticationProvider(UserService userService)
	{
		super();
		this.userService=userService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException 
	{
		String name=authentication.getName();
		String password=authentication.getCredentials().toString();
		
		AuthenticateUserEvent authEvt=new AuthenticateUserEvent(name,password);
		
		UserAuthenticatedEvent evt = userService.authenticateUser(authEvt);
		if (evt.isAuthenticated())
		{
			return new UsernamePasswordAuthenticationToken(name, password, evt.getGrantedAuths());
		}
		else
		{
			throw new BadCredentialsException("Unable to auth against third party systems.");
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) 
	{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public void setUserService(UserService userService)
	{
		this.userService=userService;
	}
}
