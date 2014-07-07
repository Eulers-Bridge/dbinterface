package com.eulersbridge.iEngage.rest.controller;

//import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.rest.domain.CountriesFactory;
import com.eulersbridge.iEngage.rest.domain.Country;

@RestController
@RequestMapping("/api")
public class GeneralInfoController {

	    private static Logger LOG = LoggerFactory.getLogger(GeneralInfoController.class);

//	    @Secured({ "ROLE_USER" })
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
	    
	    

}
