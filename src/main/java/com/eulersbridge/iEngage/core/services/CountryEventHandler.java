package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryCreatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.core.events.countrys.CountryReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryUpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountrysReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CreateCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.DeleteCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountrysEvent;
import com.eulersbridge.iEngage.core.events.countrys.UpdateCountryEvent;
import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.repository.CountryRepository;

public class CountryEventHandler implements CountryService 
{
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
		CountryDetails newCountry=(CountryDetails) createCountryEvent.getDetails();
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
	public ReadEvent readCountry(
			ReadCountryEvent readCountryEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("readCountry("+readCountryEvent.getNodeId()+")");
	    Country country = countryRepository.findOne(readCountryEvent.getNodeId());

	    if (country == null) 
	    {
	      return CountryReadEvent.notFound(readCountryEvent.getNodeId());
	    }

//	    template.fetch(user.getInstitution());

	    CountryDetails result=country.toCountryDetails();
	    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
	    return new CountryReadEvent(readCountryEvent.getNodeId(), result);
	}

	@Override
	public UpdatedEvent updateCountry(
			UpdateCountryEvent updateCountryEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("updateCountry("+updateCountryEvent.getNodeId()+")");
		CountryDetails updCountry=(CountryDetails) updateCountryEvent.getDetails();
		Country result=null,countryToUpdate=Country.fromCountryDetails(updCountry);
		countryToUpdate.setNodeId(updateCountryEvent.getNodeId());
    	if (LOG.isDebugEnabled()) LOG.debug("Country Details :"+updCountry);
    	if (LOG.isDebugEnabled()) LOG.debug("countryToUpdate :"+countryToUpdate);
		result = countryRepository.save(countryToUpdate);
		if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
    	return new CountryUpdatedEvent(result.getNodeId(),result.toCountryDetails());
	}

	@Override
	@Transactional
	public DeletedEvent deleteCountry(
			DeleteCountryEvent deleteCountryEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("deleteCountry("+deleteCountryEvent.getNodeId()+")");
	    Country country=countryRepository.findOne(deleteCountryEvent.getNodeId());
	    if (country==null)
	    {
	    	return DeletedEvent.notFound(deleteCountryEvent.getNodeId());
	    }
	    countryRepository.delete(country.getNodeId());
	    return new DeletedEvent(deleteCountryEvent.getNodeId(),country.toCountryDetails());
	}

	@Override
	public CountrysReadEvent readCountrys(ReadCountrysEvent rce) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("readCountrys()");
		Result<Country> returned=countryRepository.findAll();
		ArrayList<CountryDetails> countryList=new ArrayList<CountryDetails>();
		Iterator<Country> iter=returned.iterator();
		while (iter.hasNext())
		{
			Country country=iter.next();
			CountryDetails dets=country.toCountryDetails();
			countryList.add(dets);
		}
		CountrysReadEvent evt=new CountrysReadEvent(countryList);
		return evt;
	}

}
