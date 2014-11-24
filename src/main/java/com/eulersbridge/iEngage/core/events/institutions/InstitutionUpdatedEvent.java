package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class InstitutionUpdatedEvent extends UpdatedEvent 
{
	public InstitutionUpdatedEvent(Long id, InstitutionDetails institutionDetails) 
	{
	    super(id,institutionDetails);
	}

	public InstitutionUpdatedEvent(Long id)
	{
	    super(id);
	}
}
