package com.eulersbridge.iEngage.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.rest.domain.CountriesFactory;
import com.eulersbridge.iEngage.rest.domain.Country;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class GeneralInfoController {

	    private static Logger LOG = LoggerFactory.getLogger(GeneralInfoController.class);
        @Autowired	    CountriesFactory countryFactory;
//	    @Secured({ "ROLE_USER" })
	    @RequestMapping("/general-info2")
	    public @ResponseBody Country[] generalInfo()
	    {
	    	if (LOG.isInfoEnabled()) LOG.info("general info called. ");
	    	
	    	Country[] retValue=countryFactory.getCountries();
	    	return retValue;
	    }
	    
	    

}
