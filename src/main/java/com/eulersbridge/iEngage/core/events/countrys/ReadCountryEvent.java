package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

public class ReadCountryEvent extends RequestReadEvent 
{
	  public ReadCountryEvent(Long id) 
	  {
	    super(id);
	  }

}
