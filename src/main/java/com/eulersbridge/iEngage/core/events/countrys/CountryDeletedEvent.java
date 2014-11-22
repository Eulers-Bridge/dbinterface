package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class CountryDeletedEvent extends DeletedEvent
{
	private CountryDeletedEvent(Long id)
	{
		super(id);
	}

	public CountryDeletedEvent(Long id, CountryDetails details)
	{
		super(id,details);
	}

}
