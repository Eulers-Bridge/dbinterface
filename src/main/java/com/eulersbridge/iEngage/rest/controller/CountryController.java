package com.eulersbridge.iEngage.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryCreatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.core.events.countrys.CountryUpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountrysReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CreateCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.DeleteCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountrysEvent;
import com.eulersbridge.iEngage.core.events.countrys.UpdateCountryEvent;
import com.eulersbridge.iEngage.core.services.CountryService;
import com.eulersbridge.iEngage.rest.domain.Country;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class CountryController 
{
	@Autowired CountryService countryService;

    private static Logger LOG = LoggerFactory.getLogger(CountryController.class);
    /**
     * Is passed all the necessary data to update a country.
     * Or potentially create a new one.
     * The request must be a PUT with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting country object. 
     * 
     * @param id the id of the country to be updated.
     * @param country the country object passed across as JSON.
     * @return the country object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.COUNTRY_LABEL+"/{countryId}")
    public @ResponseBody ResponseEntity<Country> alterCountry(@PathVariable Long countryId,
    		@RequestBody Country country) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to edit country. "+countryId);
    	if (LOG.isInfoEnabled()) LOG.info("country. "+country);
    	Country restCountry=null;
    	ResponseEntity<Country> result;
    	UpdateCountryEvent updEvt=new UpdateCountryEvent(countryId,country.toCountryDetails());
    	if (LOG.isDebugEnabled()) LOG.debug("updateEvt = "+updEvt);
    	CountryUpdatedEvent countryEvent=countryService.updateCountry(updEvt);

    	if ((null==countryEvent))
    	{
    		if (LOG.isDebugEnabled())
    		{
	    			LOG.debug("instEvent = null");
    		}
    		result=new ResponseEntity<Country>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else
    	{
    		restCountry=Country.fromCountryDetails(countryEvent.getCountryDetails());
    		result=new ResponseEntity<Country>(restCountry,HttpStatus.OK);
    	}
    	return result;
    }
    
    /**
     * Is passed all the necessary data to read a country from the database.
     * The request must be a GET with the country id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the country object read from the database.
     * 
     * @param countryid the country id of the country object to be read.
     * @return the country object.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.COUNTRY_LABEL+"/{countryId}")
	public @ResponseBody ResponseEntity<Country> findCountry(@PathVariable Long countryId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve institution. "+countryId);
		Country restCountry=null;
    	ResponseEntity<Country> result;
		ReadEvent instEvent=countryService.readCountry(new ReadCountryEvent(countryId));
  	
		if (!instEvent.isEntityFound())
		{
			result=new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
		}
		else
		{
			restCountry=Country.fromCountryDetails((CountryDetails)instEvent.getDetails());
			result=new ResponseEntity<Country>(restCountry,HttpStatus.OK);
		}
		return result;
	}
    
    /**
     * Is passed all the necessary data to delete a country.
     * The request must be a DELETE with the country id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the deleted country object.
     * 
     * @param countryid the country idmof the country object to be deleted.
     * @return the country object deleted.
     * 

	*/
	@RequestMapping(method=RequestMethod.DELETE,value=ControllerConstants.COUNTRY_LABEL+"/{countryId}")
	public @ResponseBody ResponseEntity<Country> deleteCountry(@PathVariable Long countryId) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to delete institution. "+countryId);
		Country restCountry=null;
    	ResponseEntity<Country> result;
		DeletedEvent countryEvent=countryService.deleteCountry(new DeleteCountryEvent(countryId));
  	
		if (!countryEvent.isEntityFound())
		{
			result=new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
		}
		else
		{
			restCountry=Country.fromCountryDetails((CountryDetails)countryEvent.getDetails());
			result=new ResponseEntity<Country>(restCountry,HttpStatus.OK);
		}
		return result;
	}
    
    
    /**
     * Is passed all the necessary data to create a new country.
     * The request must be a POST with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting country object.
     * 
     * @param country the country object passed across as JSON.
     * @return the country object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.POST,value=ControllerConstants.COUNTRY_LABEL)
    public @ResponseBody ResponseEntity<Country> createCountry(@RequestBody Country country) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save institution "+country);
    	Country restCountry=null;
    	ResponseEntity<Country> result;
    	CountryCreatedEvent countryEvent=countryService.createCountry(new CreateCountryEvent(country.toCountryDetails()));

    	if ((countryEvent==null)||(countryEvent.getId()==null))
    	{
    		result=new ResponseEntity<Country>(HttpStatus.BAD_REQUEST);
    	}
    	else
    	{
    		restCountry=Country.fromCountryDetails((CountryDetails)countryEvent.getDetails());
    		result=new ResponseEntity<Country>(restCountry,HttpStatus.CREATED);
    	}
		return result;
    }
    
    @RequestMapping(value="/displayCountryParams")
    public @ResponseBody ResponseEntity<Boolean> displayDetails(@RequestBody Country country) 
    {
    	if (LOG.isInfoEnabled()) 
    		LOG.info("country = "+country);
    	return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }


    @RequestMapping(value=ControllerConstants.COUNTRYS_LABEL)
    public @ResponseBody ResponseEntity<Iterator<Country>> getCountrys() 
    {
    	if (LOG.isInfoEnabled()) LOG.info(" attempting to retrieve countrys. ");
    	
		ReadCountrysEvent rce=new ReadCountrysEvent(null);
		CountrysReadEvent cre = countryService.readCountrys(rce);
		Iterator <CountryDetails> iter=cre.getCountrys().iterator();
		ArrayList <Country> countrys=new ArrayList<Country>();
		while(iter.hasNext())
		{
			CountryDetails dets=iter.next();
			Country thisCountry=Country.fromCountryDetails(dets);
			countrys.add(thisCountry);		
		}
    	return new ResponseEntity<Iterator<Country>> (countrys.iterator(),HttpStatus.OK);
    }

}
