/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.institutions.CreateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionCreatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDeletedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionsReadEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionsEvent;
import com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.CreateNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;
import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import com.eulersbridge.iEngage.database.repository.CountryMemoryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionMemoryRepository;
import com.eulersbridge.iEngage.database.repository.NewsFeedMemoryRepository;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class InstitutionEventHandlerTest 
{
	InstitutionService instService;
	InstitutionMemoryRepository testInstRepo;
	CountryMemoryRepository testCountryRepo;
	NewsFeedMemoryRepository testSYRepo;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		
		HashMap<Long, Country> countrys=DatabaseDataFixture.populateCountries();
		testCountryRepo=new CountryMemoryRepository(countrys);

		HashMap<Long, Institution> institutions=DatabaseDataFixture.populateInstitutions();
		testInstRepo=new InstitutionMemoryRepository(institutions);
		
		HashMap<Long, NewsFeed> years=DatabaseDataFixture.populateStudentYears();
		testSYRepo=new NewsFeedMemoryRepository(years);

		instService=new InstitutionEventHandler(testInstRepo, testCountryRepo, testSYRepo);
	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#InstitutionEventHandler(com.eulersbridge.iEngage.database.repository.InstitutionRepository, com.eulersbridge.iEngage.database.repository.CountryRepository)}.
	 */
	@Test
	public void testInstitutionEventHandler() {
		InstitutionService instService2=new InstitutionEventHandler(testInstRepo, testCountryRepo,testSYRepo);
		assertNotNull("Constructor did not create service.",instService2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#createInstitution(com.eulersbridge.iEngage.core.events.institutions.CreateInstitutionEvent)}.
	 */
	@Test
	public void testCreateInstitution() {
		CreateInstitutionEvent createInstitutionEvent;
		InstitutionDetails nADs;
		Long id=new Long(2);
		nADs=new InstitutionDetails(id);
		nADs.setName("RMIT University");
		nADs.setCampus("Melbourne");
		nADs.setState("Victoria");
		nADs.setCountryName("Australia");
		createInstitutionEvent=new CreateInstitutionEvent(nADs);
		InstitutionCreatedEvent nace = instService.createInstitution(createInstitutionEvent);
		if (null==nace) fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#requestReadInstitution(com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent)}.
	 */
	@Test
	public void testRequestReadInstitution() {
		RequestReadInstitutionEvent rnae=new RequestReadInstitutionEvent(new Long(1));
		assertEquals("1 == 1",rnae.getId(),new Long(1));
		ReadInstitutionEvent rane=instService.requestReadInstitution(rnae);
		if (null==rane)
			fail("Not yet implemented");
	}

	@Test
	public void testRequestReadNonExistentInstitutionShouldReturnNotFound() {
		RequestReadInstitutionEvent rnae=new RequestReadInstitutionEvent(new Long(19));
		assertEquals("19 == 19",rnae.getId(),new Long(19));
		ReadInstitutionEvent rane=instService.requestReadInstitution(rnae);
		assertNotNull("Not yet implemented",rane);
		assertFalse("Non-existent entity should not be found.",rane.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#updateInstitution(com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent)}.
	 */
	@Test
	public void testUpdateInstitution() 
	{
		InstitutionDetails nADs;
		nADs=new InstitutionDetails(new Long(1));
		nADs.setCampus("Ballarat");
		nADs.setCountryName("Australia");
		nADs.setName("Ballarat University");
		nADs.setState("Victoria");
		
		UpdateInstitutionEvent updateInstitutionEvent=new UpdateInstitutionEvent(nADs.getInstitutionId(), nADs);
		InstitutionUpdatedEvent nude = instService.updateInstitution(updateInstitutionEvent);
		if (null==nude) fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#deleteInstitution(com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent)}.
	 */
	@Test
	public void testDeleteInstitution() 
	{
		DeleteInstitutionEvent deleteInstitutionEvent=new DeleteInstitutionEvent(new Long(1));
		InstitutionDeletedEvent nUDe = instService.deleteInstitution(deleteInstitutionEvent);
		assertNotNull("Institution Deleted Event returned null.",nUDe);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#deleteInstitution(com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent)}.
	 */
	@Test
	public void testDeleteNonExistentInstitutionShouldReturnNotFound() 
	{
		DeleteInstitutionEvent deleteInstitutionEvent=new DeleteInstitutionEvent(new Long(19));
		InstitutionDeletedEvent nUDe = instService.deleteInstitution(deleteInstitutionEvent);
		assertNotNull("Institution Deleted Event returned null.",nUDe);
		assertFalse("Entity allegedly found!",nUDe.isEntityFound());
		assertFalse("No entity, so deletetion could not have been completed.",nUDe.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#getInstitutions()}.
	 */
	@Test
	public void testReadInstitutions() 
	{
		ReadInstitutionsEvent rie=new ReadInstitutionsEvent();
		InstitutionsReadEvent institutions=instService.readInstitutions(rie);
		if (null==institutions) fail("Not yet implemented");
		rie=new ReadInstitutionsEvent((long)1);
		institutions=instService.readInstitutions(rie);
		if (null==institutions) fail("Not yet implemented");
	}
	
	@Test
	public void testCreateStudentYear()
	{
		NewsFeedDetails newsFeedDetails=new NewsFeedDetails((long)1);
		CreateNewsFeedEvent csye=new CreateNewsFeedEvent(newsFeedDetails);
		NewsFeedCreatedEvent syce=instService.createNewsFeed(csye);
		assertNotNull("Student year created event was null.",syce);
	}

}
