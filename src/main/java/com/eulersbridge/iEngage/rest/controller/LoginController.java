package com.eulersbridge.iEngage.rest.controller;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.catalina.authenticator.AuthenticatorBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.domain.Login;
import com.eulersbridge.iEngage.core.domain.Logout;
import com.eulersbridge.iEngage.core.events.users.LogInUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserLogInEvent;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.Response;
import com.eulersbridge.iEngage.rest.domain.SignUp;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class LoginController 
{
    @Autowired UserService userService;

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value=ControllerConstants.LOGIN_LABEL,method=RequestMethod.GET)
  	public @ResponseBody Response login() 
    {
    	SecurityContext context=SecurityContextHolder.getContext();
    	Authentication authentication=context.getAuthentication();
    	String username=authentication.getName();
    	String password="*******";
    	Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
    	if (authentication.getCredentials()!=null) password=authentication.getCredentials().toString();
    	if (LOG.isInfoEnabled()) LOG.info(username+" attempting to login. ");
    	if (LOG.isDebugEnabled()) LOG.debug("auth - "+authentication+" username - "+username+" credentails - "+password+" roles - "+roles);
    	LogInUserEvent evt=new LogInUserEvent(username,roles);
    	UserLogInEvent retEvt=userService.login(evt);
    	Login login=new Login(counter.incrementAndGet(),username,password);
    	Response response=login.process();
		return response;
    }
    
    @RequestMapping(value=ControllerConstants.LOGOUT_LABEL)
    public @ResponseBody Response logout() 
    {
    	String username=SecurityContextHolder.getContext().getAuthentication().getName();
    	if (LOG.isInfoEnabled()) LOG.info(username+" attempting to logout. ");
    	Logout logout=new Logout(counter.incrementAndGet(),username);
    	SecurityContextHolder.clearContext();
    	Response response=logout.process();
        return response;
    }
}