package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class CountryUpdatedEvent extends UpdatedEvent 
{
	public CountryUpdatedEvent(Long id, CountryDetails countryDetails) 
	{
	    super(id,countryDetails);
	}

	public CountryUpdatedEvent(Long id)
	{
	    super(id);
	}
}
