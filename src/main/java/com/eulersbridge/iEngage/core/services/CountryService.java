package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryCreatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountrysReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CreateCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.DeleteCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.UpdateCountryEvent;


//All methods are guaranteed to return something, null will never be returned.
public interface CountryService {
	public CountryCreatedEvent createCountry(CreateCountryEvent createCountryEvent);
	public ReadEvent readCountry(ReadCountryEvent readCountryEvent);
	public UpdatedEvent updateCountry(UpdateCountryEvent updateCountryEvent);
	public DeletedEvent deleteCountry(DeleteCountryEvent deleteCountryEvent);
	public CountrysReadEvent readCountrys(ReadAllEvent readCountrysEvent);

}
