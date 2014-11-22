package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class CountryCreatedEvent extends CreatedEvent
{
	private Long id;

	public CountryCreatedEvent(Long id, CountryDetails countryDetails)
	{
		super(countryDetails);
		this.id = id;
	}

	public CountryCreatedEvent(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}

	public void setKey(Long id)
	{
		this.id = id;
	}
}
