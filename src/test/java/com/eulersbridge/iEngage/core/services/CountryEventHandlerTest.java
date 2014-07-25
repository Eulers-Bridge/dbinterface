package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.countrys.CountryCreatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDeletedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.core.events.countrys.CountryReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryUpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CreateCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.DeleteCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.UpdateCountryEvent;
import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.repository.CountryMemoryRepository;
/**
 * @author Greg Newitt
 *
 */
public class CountryEventHandlerTest 
{
	CountryMemoryRepository testRepo;
	CountryEventHandler countryService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
		HashMap<Long, Country> countrys=new HashMap<Long, Country>();
		Country initialCountry=new Country();
		initialCountry.setCountryName("Australia");
		countrys.put(new Long(1), initialCountry);
		testRepo=new CountryMemoryRepository(countrys);
		countryService=new CountryEventHandler(testRepo);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCountryEventHandler() 
	{
		CountryService countryService2=new CountryEventHandler(testRepo);
		assertNotNull("countryService not being created by constructor.",countryService2);
	}

	@Test
	public void testCreateCountry() 
	{
		CreateCountryEvent createCountryEvent;
		CountryDetails cDs;
		Long id=new Long(2);
		cDs=new CountryDetails(id);
		cDs.setCountryName("United Kingdom");
		createCountryEvent=new CreateCountryEvent(cDs);
		CountryCreatedEvent nace = countryService.createCountry(createCountryEvent);
		if (null==nace) fail("Not yet implemented");
	}

	@Test
	public void testReadCountry() 
	{
		ReadCountryEvent rnae=new ReadCountryEvent(new Long(1));
		assertEquals("1 == 1",rnae.getId(),new Long(1));
		CountryReadEvent rane=countryService.readCountry(rnae);
		if (null==rane)
			fail("Not yet implemented");
	}

	@Test
	public void testUpdateCountry() {
		CountryDetails cDs;
		cDs=new CountryDetails(new Long(1));
		cDs.setCountryName("New Zealand");
		
		UpdateCountryEvent updateCountryEvent=new UpdateCountryEvent(cDs.getCountryId(), cDs);
		CountryUpdatedEvent nude = countryService.updateCountry(updateCountryEvent);
		if (null==nude) fail("Not yet implemented");
	}

	@Test
	public void testDeleteCountry() {
		DeleteCountryEvent deleteCountryEvent=new DeleteCountryEvent(new Long(1));
		CountryDeletedEvent nUDe = countryService.deleteCountry(deleteCountryEvent);
		if (null==nUDe)	fail("Not yet implemented");
	}

	@Test
	public void testGetCountrys() 
	{
		Iterator<com.eulersbridge.iEngage.rest.domain.Country> countrys=countryService.getCountrys();
		if (null==countrys) fail("Not yet implemented");
	}

}
