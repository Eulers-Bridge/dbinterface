package com.eulersbridge.iEngage.rest.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.domain.Login;
import com.eulersbridge.iEngage.core.domain.Logout;
import com.eulersbridge.iEngage.rest.domain.Response;
import com.eulersbridge.iEngage.rest.domain.SignUp;

@RestController
public class LoginController {

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final AtomicLong counter = new AtomicLong();

/*    
//    @Secured({ "ROLE_USER" })
    @RequestMapping("/general-info")
    public @ResponseBody Country[] generalInfo()
    {
    	if (LOG.isInfoEnabled()) LOG.info("general info called. ");
    	ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("application-context.xml");
    	CountriesFactory cf=(CountriesFactory)ctx.getBean("countryFactory");
    	
    	Country[] retValue=cf.getCountries();
    	ctx.close();
    	return retValue;
    }
*/    
    @Secured({ "ROLE_USER" })
    @RequestMapping(value="/login",method=RequestMethod.GET)
  	public @ResponseBody Response login( @RequestParam(value="username", required=true) String username,
            @RequestParam(value="password", required=true) String password) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(username+" attempting to login. ");
    	Login login=new Login(counter.incrementAndGet(),username,password);
    	Response response=login.process();
		return response;
    }
    
    @RequestMapping(value="/logout/{username}")
    public @ResponseBody Response logout(
            @PathVariable String username) 
    {
    	if (LOG.isInfoEnabled()) LOG.info(username+" attempting to logout. ");
    	Logout logout=new Logout(counter.incrementAndGet(),username);
    	Response response=logout.process();
        return response;
    }
    
    @RequestMapping(value="/signup",method=RequestMethod.POST)
    public @ResponseBody Response signup(
            @RequestParam(value="email", required=true) String email,
            @RequestParam(value="fullName", required=true) String fullName,
            @RequestParam(value="password", required=true) String password,
            @RequestParam(value="countryID", required=true) String countryId,
            @RequestParam(value="universityID", required=true) String universityId
            ) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to signup "+email);
    	SignUp signup=new SignUp(email,fullName,password,countryId,universityId);
    	
    	Response response=signup.process();
    	
        return response;
    }
    
    
}