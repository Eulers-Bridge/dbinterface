package com.eulersbridge.iEngage.core.events.countrys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreateEvent;

public class CreateCountryEvent extends CreateEvent 
{
	CountryDetails countryDetails;
	
    private static Logger LOG = LoggerFactory.getLogger(CreateCountryEvent.class);
	
	public CreateCountryEvent(Long id, CountryDetails countryDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateCountryEvent("+id+","+countryDetails+") = ");
		this.countryDetails=countryDetails;
		countryDetails.setCountryId(id);
	}

	public CreateCountryEvent(CountryDetails countryDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateInstitutionEvent(countryDetails) = ");
		this.countryDetails=countryDetails;
	}

	public CountryDetails getDetails() {
		return countryDetails;
	}

	public void setInstitutionDetails(CountryDetails countryDetails) {
		this.countryDetails = countryDetails;
	}


}
