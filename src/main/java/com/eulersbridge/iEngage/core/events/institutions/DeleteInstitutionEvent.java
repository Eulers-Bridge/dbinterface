package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

public class DeleteInstitutionEvent extends DeleteEvent 
{
	private final Long institutionId;

	public DeleteInstitutionEvent(final Long institutionId) 
	{
		this.institutionId=institutionId;
	}

	public Long getId() 
	{
		    return institutionId;
	}
}