package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.CreateEvent;

public class CreateInstitutionEvent extends CreateEvent 
{
	public CreateInstitutionEvent(InstitutionDetails instDetails) 
	{
		super(instDetails);
	}
}
