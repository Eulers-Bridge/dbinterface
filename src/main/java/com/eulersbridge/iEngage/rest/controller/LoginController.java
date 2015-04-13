package com.eulersbridge.iEngage.rest.controller;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.domain.Logout;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.users.LoginDetails;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.NewsService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.LogIn;
import com.eulersbridge.iEngage.rest.domain.Response;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class LoginController 
{
    @Autowired UserService userService;
	@Autowired NewsService newsService;

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final AtomicLong counter = new AtomicLong();


    @RequestMapping(value=ControllerConstants.LOGIN_LABEL,method=RequestMethod.GET)
  	public @ResponseBody ResponseEntity<LogIn>  login() 
    {
    	SecurityContext context=SecurityContextHolder.getContext();
    	Authentication authentication=context.getAuthentication();
    	String username=authentication.getName();
    	String password="*******";
    	Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
    	if (authentication.getCredentials()!=null) password=authentication.getCredentials().toString();
    	if (LOG.isInfoEnabled()) LOG.info(username+" attempting to login. ");
    	if (LOG.isDebugEnabled()) LOG.debug("auth - "+authentication+" username - "+username+" credentails - "+password+" roles - "+roles);
    	
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve user. "+username);
		ReadUserEvent userEvent=userService.readUser(new RequestReadUserEvent(username));
		if (!userEvent.isEntityFound())
		{
			return new ResponseEntity<LogIn>(HttpStatus.NOT_FOUND);
		}

		UserDetails userDetails=(UserDetails) userEvent.getDetails();
		
		int pageNumber=0;
		int pageLength=10;
		pageNumber=Integer.parseInt(ControllerConstants.PAGE_NUMBER);
		pageLength=Integer.parseInt(ControllerConstants.PAGE_LENGTH);

		Long institutionId=userDetails.getInstitutionId();
		Long userId=userDetails.getNodeId();
		ReadAllEvent rnae=new ReadAllEvent(institutionId);
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve news articles from institutionId. "+institutionId);
		Direction sortDirection=Direction.DESC;
		NewsArticlesReadEvent articleEvent=newsService.readNewsArticles(rnae,sortDirection, pageNumber,pageLength);
  	
		if (!articleEvent.isEntityFound())
		{
			return new ResponseEntity<LogIn>(HttpStatus.NOT_FOUND);
		}		
		
		LoginDetails result=new LoginDetails(articleEvent.getArticles().iterator(), userDetails,userId);
		
		LogIn response=LogIn.fromLoginDetails(result);
		
		return new ResponseEntity<LogIn>(response,HttpStatus.OK);
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