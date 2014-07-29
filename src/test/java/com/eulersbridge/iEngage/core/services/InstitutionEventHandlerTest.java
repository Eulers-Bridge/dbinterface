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
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent;
import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.repository.CountryMemoryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionMemoryRepository;
import com.eulersbridge.iEngage.database.domain.Institution;

/**
 * @author Greg Newitt
 *
 */
public class InstitutionEventHandlerTest 
{
	InstitutionService instService;
	InstitutionMemoryRepository testInstRepo;
	CountryMemoryRepository testCountryRepo;
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
		HashMap<Long, Country> countrys=new HashMap<Long, Country>();
		Country initialCountry=new Country();
		initialCountry.setCountryName("Australia");
		countrys.put(new Long(1), initialCountry);
		initialCountry.setNodeId(new Long(1));
		testCountryRepo=new CountryMemoryRepository(countrys);

		HashMap<Long, Institution> institutions=new HashMap<Long, Institution>();
		Institution initialInst=new Institution();
		initialInst.setName("University of Melbourne");
		initialInst.setCampus("Parkville");
		initialInst.setState("Victoria");
		initialInst.setCountry(initialCountry);
		institutions.put(new Long(1), initialInst);
		testInstRepo=new InstitutionMemoryRepository(institutions);

		instService=new InstitutionEventHandler(testInstRepo, testCountryRepo);
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
		InstitutionService instService2=new InstitutionEventHandler(testInstRepo, testCountryRepo);
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
		if (null==nUDe)	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.InstitutionEventHandler#getInstitutions()}.
	 */
	@Test
	public void testGetInstitutions() {
		fail("Not yet implemented");
	}

}