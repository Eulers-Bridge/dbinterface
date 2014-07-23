package com.eulersbridge.iEngage.core.services;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.countrys.CountryCreatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDeletedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryUpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CreateCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.DeleteCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.UpdateCountryEvent;
import com.eulersbridge.iEngage.rest.domain.Country;


//All methods are guaranteed to return something, null will never be returned.
public interface CountryService {
	public CountryCreatedEvent createCountry(CreateCountryEvent createCountryEvent);
	public CountryReadEvent readCountry(ReadCountryEvent readCountryEvent);
	public CountryUpdatedEvent updateCountry(UpdateCountryEvent updateCountryEvent);
	public CountryDeletedEvent deleteCountry(DeleteCountryEvent deleteCountryEvent);
	public Iterator<Country> getCountrys();

}
