package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

public class UpdateCountryEvent extends UpdateEvent
{
	public UpdateCountryEvent(Long id, CountryDetails countryDetails)
	{
		super(id, countryDetails);
	}
}
