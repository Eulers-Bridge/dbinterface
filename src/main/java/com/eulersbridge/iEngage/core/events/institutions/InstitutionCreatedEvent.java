package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

public class InstitutionCreatedEvent extends CreatedEvent 
{
	private Long id;
	protected boolean countryFound = true;

	public InstitutionCreatedEvent(Long id, InstitutionDetails instDetails) 
	{
		super(instDetails);
		this.id=id;
	}

	public InstitutionCreatedEvent(Long id) 
	{
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setKey(Long id) {
		this.id = id;
	}

	public static InstitutionCreatedEvent countryNotFound(Long id) 
	{
		InstitutionCreatedEvent ev = new InstitutionCreatedEvent(id);
	    ev.countryFound=false;
	    return ev;
	}
	
	public boolean isCountryFound() 
	{
		return countryFound;
	}
}
