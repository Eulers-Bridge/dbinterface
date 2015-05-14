package com.eulersbridge.iEngage.core.services;

import org.springframework.security.access.prepost.PreAuthorize;

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
import com.eulersbridge.iEngage.security.SecurityConstants;


//All methods are guaranteed to return something, null will never be returned.
public interface CountryService {
    @PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"')")
	public CountryCreatedEvent createCountry(CreateCountryEvent createCountryEvent);
    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public ReadEvent readCountry(ReadCountryEvent readCountryEvent);
    @PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"')")
	public UpdatedEvent updateCountry(UpdateCountryEvent updateCountryEvent);
    @PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"')")
	public DeletedEvent deleteCountry(DeleteCountryEvent deleteCountryEvent);
    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public CountrysReadEvent readCountrys(ReadAllEvent readCountrysEvent);

}
