package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

public class DeleteCountryEvent extends DeleteEvent 
{
	public DeleteCountryEvent(final Long countryId) 
	{
		super(countryId);
	}

	public Long getId() 
	{
		    return getNodeId();
	}
}