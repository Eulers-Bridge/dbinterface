package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

public class RequestReadInstitutionEvent extends RequestReadEvent 
{
	  private Long id;

	  public RequestReadInstitutionEvent(Long id) {
	    this.id = id;
	  }

	  public Long getId() {
	    return id;
	  }

}
