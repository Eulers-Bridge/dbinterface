package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class CountryReadEvent extends ReadEvent
{
	public CountryReadEvent(Long id)
	{
		super(id);
	}

	public CountryReadEvent(Long id, CountryDetails countryDetails)
	{
		super(id, countryDetails);
	}
}
