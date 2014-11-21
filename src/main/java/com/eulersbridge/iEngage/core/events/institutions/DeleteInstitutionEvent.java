package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

public class DeleteInstitutionEvent extends DeleteEvent 
{
	public DeleteInstitutionEvent(final Long institutionId) 
	{
		super(institutionId);
	}

	public Long getId() 
	{
		    return getNodeId();
	}
}