package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

public class UpdateCountryEvent extends UpdateEvent 
{
	  private Long id;
	  private CountryDetails countryDetails;

	  public UpdateCountryEvent(Long id, CountryDetails countryDetails) {
	    this.id = id;
	    this.countryDetails = countryDetails;
	    this.countryDetails.setCountryId(id);
	  }

	  public Long getId() {
	    return id;
	  }

	  public CountryDetails getCountryDetails() {
	    return countryDetails;
	  }


}
