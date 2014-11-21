package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class ReadInstitutionEvent extends ReadEvent
{
	public ReadInstitutionEvent(Long id)
	{
		super(id);
	}

	public ReadInstitutionEvent(Long id, InstitutionDetails institutionDetails)
	{
		super(id,institutionDetails);
	}

	public Long getId()
	{
		return getNodeId();
	}

	public InstitutionDetails getInstitutionDetails()
	{
		return (InstitutionDetails)getDetails();
	}
}
