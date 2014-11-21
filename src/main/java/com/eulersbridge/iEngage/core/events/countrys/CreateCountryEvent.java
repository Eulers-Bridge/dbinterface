package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.CreateEvent;

public class CreateCountryEvent extends CreateEvent 
{
	public CreateCountryEvent(CountryDetails countryDetails) 
	{
		super(countryDetails);
	}
}
