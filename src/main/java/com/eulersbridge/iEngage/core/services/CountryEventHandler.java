package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.core.events.countrys.CountryCreatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDeletedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.core.events.countrys.CountryReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryUpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CreateCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.DeleteCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.UpdateCountryEvent;
import com.eulersbridge.iEngage.core.events.institutions.CreateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionCreatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDeletedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent;
import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.repository.CountryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

public class CountryEventHandler implements CountryService {

    private static Logger LOG = LoggerFactory.getLogger(CountryEventHandler.class);

    private CountryRepository countryRepository;
    
    public CountryEventHandler(final CountryRepository countryRepo) 
    {
      this.countryRepository = countryRepo;
    }
    
	@Override
	@Transactional
	public CountryCreatedEvent createCountry(
			CreateCountryEvent createCountryEvent) 
	{
		CountryDetails newCountry=createCountryEvent.getDetails();
    	if (LOG.isDebugEnabled()) LOG.debug("Country Details :"+newCountry);
    	Country countryToInsert=Country.fromCountryDetails(newCountry);
    	CountryCreatedEvent result;   	
    	
    	Country createdCountry=null;
    	if (LOG.isDebugEnabled()) LOG.debug("countryToInsert :"+countryToInsert);
    	createdCountry = countryRepository.save(countryToInsert);
		//TODO what happens if this fails?
		if (LOG.isDebugEnabled()) LOG.debug("created country = "+createdCountry);
    		
        result=new CountryCreatedEvent(createdCountry.getNodeId(),createdCountry.toCountryDetails());
    	return result;
	}

	@Override
	public CountryReadEvent readCountry(
			ReadCountryEvent readCountryEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("readCountry("+readCountryEvent.getId()+")");
	    Country country = countryRepository.findOne(readCountryEvent.getId());

	    if (country == null) 
	    {
	      return CountryReadEvent.notFound(readCountryEvent.getId());
	    }

//	    template.fetch(user.getInstitution());

	    CountryDetails result=country.toCountryDetails();
	    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
	    return new CountryReadEvent(readCountryEvent.getId(), result);
	}

	@Override
	public CountryUpdatedEvent updateCountry(
			UpdateCountryEvent updateCountryEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("updateCountry("+updateCountryEvent.getId()+")");
		CountryDetails updCountry=updateCountryEvent.getCountryDetails();
		Country result=null,countryToUpdate=Country.fromCountryDetails(updCountry);
		countryToUpdate.setNodeId(updateCountryEvent.getId());
    	if (LOG.isDebugEnabled()) LOG.debug("Country Details :"+updCountry);
    	if (LOG.isDebugEnabled()) LOG.debug("countryToUpdate :"+countryToUpdate);
		result = countryRepository.save(countryToUpdate);
		if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
    	return new CountryUpdatedEvent(result.getNodeId(),result.toCountryDetails());

	}

	@Override
	@Transactional
	public CountryDeletedEvent deleteCountry(
			DeleteCountryEvent deleteCountryEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("deleteCountry("+deleteCountryEvent.getId()+")");
	    Country country=countryRepository.findOne(deleteCountryEvent.getId());
	    if (country==null)
	    {
	    	return CountryDeletedEvent.notFound(deleteCountryEvent.getId());
	    }
	    countryRepository.delete(country.getNodeId());
	    return new CountryDeletedEvent(deleteCountryEvent.getId(),country.toCountryDetails());
	}

	@Override
	public Iterator<com.eulersbridge.iEngage.rest.domain.Country> getCountrys() 
	{
		Result<Country> returned=countryRepository.findAll();
		ArrayList<com.eulersbridge.iEngage.rest.domain.Country> countryList=new ArrayList<com.eulersbridge.iEngage.rest.domain.Country>();
		Iterator<Country> iter=returned.iterator();
		while (iter.hasNext())
		{
			Country country=iter.next();
			CountryDetails dets=country.toCountryDetails();
			
			com.eulersbridge.iEngage.rest.domain.Country restCountry=com.eulersbridge.iEngage.rest.domain.Country.fromCountryDetails(dets);
			countryList.add(restCountry);
		}
		return countryList.iterator();
	}

}
