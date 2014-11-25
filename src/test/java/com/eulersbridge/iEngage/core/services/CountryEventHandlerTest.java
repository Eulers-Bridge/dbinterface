package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryCreatedEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.core.events.countrys.CountryReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CountrysReadEvent;
import com.eulersbridge.iEngage.core.events.countrys.CreateCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.DeleteCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountryEvent;
import com.eulersbridge.iEngage.core.events.countrys.ReadCountrysEvent;
import com.eulersbridge.iEngage.core.events.countrys.UpdateCountryEvent;
import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
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
		HashMap<Long, Country> countrys=DatabaseDataFixture.populateCountries();
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
		assertNotNull("Not yet implemented",nace);
	}

	@Test
	public void testReadCountry() 
	{
		ReadCountryEvent rnae=new ReadCountryEvent(new Long(1));
		assertEquals("1 == 1",rnae.getNodeId(),new Long(1));
		CountryReadEvent rane=(CountryReadEvent) countryService.readCountry(rnae);
		assertNotNull("Not yet implemented",rane);
	}

	@Test
	public void testReadNonExistentCountryShouldReturnNotFound() 
	{
		ReadCountryEvent rnae=new ReadCountryEvent(new Long(19));
		assertEquals("19 == 19",rnae.getNodeId(),new Long(19));
		ReadEvent rane=countryService.readCountry(rnae);
		assertNotNull("Not yet implemented",rane);
		assertFalse("Entity should not be found.",rane.isEntityFound());
	}

	@Test
	public void testUpdateCountry() {
		CountryDetails cDs;
		cDs=new CountryDetails(new Long(1));
		cDs.setCountryName("New Zealand");
		
		UpdateCountryEvent updateCountryEvent=new UpdateCountryEvent(cDs.getCountryId(), cDs);
		UpdatedEvent nude = countryService.updateCountry(updateCountryEvent);
		assertNotNull("Not yet implemented",nude);
	}

	@Test
	public void testDeleteCountry() {
		DeleteCountryEvent deleteCountryEvent=new DeleteCountryEvent(new Long(1));
		DeletedEvent nUDe = countryService.deleteCountry(deleteCountryEvent);
		assertNotNull("Not yet implemented",nUDe);
	}

	@Test
	public void testDeleteNonExistentCountryShouldReturnNotFound() 
	{
		DeleteCountryEvent deleteCountryEvent=new DeleteCountryEvent(new Long(19));
		DeletedEvent nUDe = countryService.deleteCountry(deleteCountryEvent);
		assertNotNull("Not yet implemented",nUDe);
		assertFalse("Entity can't be found...",nUDe.isEntityFound());
		assertFalse("Deletion has not completed!",nUDe.isDeletionCompleted());
	}

	@Test
	public void testReadCountrys() 
	{
		ReadCountrysEvent rce=new ReadCountrysEvent(null);
		CountrysReadEvent countrys=countryService.readCountrys(rce);
		assertNotNull("Not yet implemented",countrys);
	}

}
