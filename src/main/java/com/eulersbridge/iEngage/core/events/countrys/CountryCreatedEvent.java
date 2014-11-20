package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class CountryCreatedEvent extends CreatedEvent 
{
	private CountryDetails countryDetails;
	private Long id;

	public CountryCreatedEvent(Long id, CountryDetails countryDetails) 
	{
		this.countryDetails=countryDetails;
		this.id=id;
	}

	public CountryCreatedEvent(Long id) 
	{
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setKey(Long id) {
		this.id = id;
	}

	public void setCountryDetails(CountryDetails countryDetails) {
		this.countryDetails = countryDetails;
	}

	@Override
	public CountryDetails getDetails() 
	{
	    return countryDetails;
	}
	public CountryDetails getCountryDetails() 
	{
	    return countryDetails;
	}
}
