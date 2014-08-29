package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

public class UpdateInstitutionEvent extends UpdateEvent 
{
	  private Long id;
	  private InstitutionDetails institutionDetails;

	  public UpdateInstitutionEvent(Long id, InstitutionDetails institutionDetails) {
	    this.id = id;
	    this.institutionDetails = institutionDetails;
	    this.institutionDetails.setInstitutionId(id);
	  }

	  public Long getId() {
	    return id;
	  }

	  public InstitutionDetails getInstDetails() {
	    return institutionDetails;
	  }


}
