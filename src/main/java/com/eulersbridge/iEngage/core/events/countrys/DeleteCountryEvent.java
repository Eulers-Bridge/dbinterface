package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

public class DeleteCountryEvent extends DeleteEvent 
{
	private final Long countryId;

	public DeleteCountryEvent(final Long countryId) 
	{
		this.countryId=countryId;
	}

	public Long getId() 
	{
		    return countryId;
	}
}