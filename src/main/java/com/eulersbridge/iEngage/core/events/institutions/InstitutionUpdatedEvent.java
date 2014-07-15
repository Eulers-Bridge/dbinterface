package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class InstitutionUpdatedEvent extends UpdatedEvent 
{
	private Long id;
	private InstitutionDetails institutionDetails;
	protected boolean countryFound = true;

	public InstitutionUpdatedEvent(Long id, InstitutionDetails institutionDetails) 
	{
	    this.id = id;
	    this.institutionDetails = institutionDetails;
	}

	public InstitutionUpdatedEvent(Long id)
	{
	    this.id = id;
	}

	public Long getId()
	{
	    return id;
	}

	public InstitutionDetails getInstDetails()
	{
	    return institutionDetails;
	}

	public static InstitutionUpdatedEvent countryNotFound(Long id) 
	{
		InstitutionUpdatedEvent ev = new InstitutionUpdatedEvent(id);
	    ev.countryFound=false;
	    return ev;
	}
	
	public boolean isCountryFound() 
	{
		return countryFound;
	}


}
