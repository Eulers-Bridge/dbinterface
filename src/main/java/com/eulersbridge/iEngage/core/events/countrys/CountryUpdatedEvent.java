package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class CountryUpdatedEvent extends UpdatedEvent 
{
	private Long id;
	private CountryDetails countryDetails;

	public CountryUpdatedEvent(Long id, CountryDetails countryDetails) 
	{
	    this.id = id;
	    this.countryDetails = countryDetails;
	}

	public CountryUpdatedEvent(Long id)
	{
	    this.id = id;
	}

	public Long getId()
	{
	    return id;
	}

	public CountryDetails getCountryDetails()
	{
	    return countryDetails;
	}
}
