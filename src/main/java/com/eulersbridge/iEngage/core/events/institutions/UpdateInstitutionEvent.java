package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

public class UpdateInstitutionEvent extends UpdateEvent
{
	public UpdateInstitutionEvent(Long id, InstitutionDetails institutionDetails)
	{
		super(id,institutionDetails);
	}
}
