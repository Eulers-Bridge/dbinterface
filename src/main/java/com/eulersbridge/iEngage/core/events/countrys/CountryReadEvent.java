package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.ReadEvent;

public class CountryReadEvent extends ReadEvent 
{

	private Long id;
	private CountryDetails countryDetails;
	
	  public CountryReadEvent(Long id) {
	    this.id = id;
	  }

	  public CountryReadEvent(Long id, CountryDetails countryDetails) {
	    this.id = id;
	    this.countryDetails = countryDetails;
	  }

	  public Long getId() {
	    return id;
	  }

	  public CountryDetails getCountryDetails() {
	    return countryDetails;
	  }

	  public static CountryReadEvent notFound(Long id) 
	  {
		  CountryReadEvent ev = new CountryReadEvent(id);
	    ev.entityFound=false;
	    return ev;
	  }

}
