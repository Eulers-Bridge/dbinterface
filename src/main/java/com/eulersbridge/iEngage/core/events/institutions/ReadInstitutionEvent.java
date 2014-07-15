package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class ReadInstitutionEvent extends ReadEvent 
{

	private Long id;
	private InstitutionDetails institutionDetails;
	
	  public ReadInstitutionEvent(Long id) {
	    this.id = id;
	  }

	  public ReadInstitutionEvent(Long id, InstitutionDetails institutionDetails) {
	    this.id = id;
	    this.institutionDetails = institutionDetails;
	  }

	  public Long getId() {
	    return id;
	  }

	  public InstitutionDetails getInstitutionDetails() {
	    return institutionDetails;
	  }

	  public static ReadInstitutionEvent notFound(Long id) 
	  {
		  ReadInstitutionEvent ev = new ReadInstitutionEvent(id);
	    ev.entityFound=false;
	    return ev;
	  }

}
