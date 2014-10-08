package com.eulersbridge.iEngage.core.events.generalInfo;

import java.util.Iterator;

public class GeneralInfoDetails 
{
	Iterator <GiCountry> countries;
	
	public GeneralInfoDetails(Iterator <GiCountry> countries)
	{
		this.countries=countries;
	}

	/**
	 * @return the countries
	 */
	public Iterator<GiCountry> getCountries() {
		return countries;
	}

	/**
	 * @param countries the countries to set
	 */
	public void setCountries(Iterator<GiCountry> countries) {
		this.countries = countries;
	}
}
