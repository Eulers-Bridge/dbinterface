package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

public class InstitutionDeletedEvent extends DeletedEvent
{
	private InstitutionDeletedEvent(Long id)
	{
		super(id);
	}

	public InstitutionDeletedEvent(Long id, InstitutionDetails details)
	{
		super(id,details);
	}
}
