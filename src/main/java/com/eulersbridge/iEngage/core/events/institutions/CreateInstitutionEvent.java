package com.eulersbridge.iEngage.core.events.institutions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;

public class CreateInstitutionEvent extends CreateEvent 
{
	InstitutionDetails instDetails;
	
    private static Logger LOG = LoggerFactory.getLogger(CreateInstitutionEvent.class);
	
	public CreateInstitutionEvent(Long id, InstitutionDetails instDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateUserEvent("+id+","+instDetails+") = ");
		this.instDetails=instDetails;
		instDetails.setInstitutionId(id);
	}

	public CreateInstitutionEvent(InstitutionDetails instDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateInstitutionEvent(instDetails) = ");
		this.instDetails=instDetails;
	}

	public InstitutionDetails getDetails() {
		return instDetails;
	}

	public void setInstitutionDetails(InstitutionDetails instDetails) {
		this.instDetails = instDetails;
	}


}
