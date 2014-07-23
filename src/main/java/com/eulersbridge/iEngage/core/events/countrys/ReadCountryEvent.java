package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

public class ReadCountryEvent extends RequestReadEvent 
{
	  private Long id;

	  public ReadCountryEvent(Long id) 
	  {
	    this.id = id;
	  }

	  public Long getId() 
	  {
	    return id;
	  }

}
