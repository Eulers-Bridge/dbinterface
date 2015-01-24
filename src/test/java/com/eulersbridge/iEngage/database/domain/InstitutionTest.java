/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

/**
 * @author Greg Newitt
 *
 */
public class InstitutionTest {

    private static Logger LOG = LoggerFactory.getLogger(InstitutionTest.class);

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
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#Institution()}.
	 */
	@Test
	public final void testInstitution() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test Instituion Constructor");
		Institution testObj=new Institution();
		if (testObj.getClass()!=Institution.class)
			fail("University constructor does not return a class of type institution."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#Institution(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testInstitutionStringStringStringString() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test Instituion Constructor");
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution("University of Melbourne","Parkville","VIC",country);
		assertEquals("University constructor does not return a class of type institution.",testObj.getClass(),Institution.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getName()}.
	 */
	@Test
	public final void testGetName() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test getName()");
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution(name,campus,state,country);
		assertEquals("getName() does not return the value class was constructed with.",testObj.getName(),name);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test setName()");
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution(name,campus,state,country);
		String name2="Monash University";
		testObj.setName(name2);
		assertEquals("getName() does not return the value that setter should have set it to.",testObj.getName(),name2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getCampus()}.
	 */
	@Test
	public final void testGetCampus() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test getCampus()");
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution(name,campus,state,country);
		assertEquals("getCampus() does not return the value class was constructed with.",testObj.getCampus(),campus); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setCampus(java.lang.String)}.
	 */
	@Test
	public final void testSetCampus() {
		if (LOG.isDebugEnabled()) LOG.debug("Test setCampus()");
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution(name,campus,state,country);
		String campus2="Clayton";
		testObj.setCampus(campus2);
		assertEquals("getCampus() does not return the value that setter should have set it to.",testObj.getCampus(),campus2); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getState()}.
	 */
	@Test
	public final void testGetState() {
		if (LOG.isDebugEnabled()) LOG.debug("Test getState()");
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution(name,campus,state,country);
		assertEquals("getState() does not return the value class was constructed with.",testObj.getState(),state);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setState(java.lang.String)}.
	 */
	@Test
	public final void testSetState() {
		if (LOG.isDebugEnabled()) LOG.debug("Test setState()");
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution(name,campus,state,country);
		String state2="NSW";
		testObj.setState(state2);
		assertEquals("getState() does not return the value that setter should have set it to.",testObj.getState(),state2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getCountry()}.
	 */
	@Test
	public final void testGetCountry() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test getCountry()");
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution(name,campus,state,country);
		assertEquals("getCountry() does not return the value class was constructed with.",testObj.getCountry().getCountryName(),country.getCountryName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setCountry(java.lang.String)}.
	 */
	@Test
	public final void testSetCountry() {
		if (LOG.isDebugEnabled()) LOG.debug("Test setCountry()");
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		Country country=new Country();
		country.setCountryName("Australia");
		Institution testObj=new Institution(name,campus,state,country);
		Country country2=new Country();
		country2.setCountryName("Australia");
		testObj.setCountry(country2);
		assertEquals("getCountry() does not return the value that setter should have set it to.",testObj.getCountry().getCountryName(),country2.getCountryName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#toString()}.
	 */
	@Test
	public final void testToString() {
	}
	
	@Autowired InstitutionRepository repo;
	@Test @Transactional public void persistedInstitutionShouldBeRetrievableFromGraphDb()
	{
		Country country=new Country();
		country.setCountryName("Australia");
		Institution uniMelb = DatabaseDataFixture.populateInstUniMelb();
		// repo.findByPropertyValue("Name", "University of Melbourne");
//				template.save(new Institution("University of Melbourne","Parkville","VIC","Australia"));
		if (LOG.isDebugEnabled()) LOG.debug("uniMelb = "+uniMelb);
	}
	
	@Test
	public final void testToDetails()
	{
		Institution inst= DatabaseDataFixture.populateInstUniMelb();
		InstitutionDetails dets = inst.toInstDetails();
		assertEquals("Names don't match.", dets.getName(), inst.getName());
		assertEquals("Campuses don't match.", dets.getCampus(),inst.getCampus());
		assertEquals("States don't match.", dets.getState(),inst.getState());
		assertEquals("Countrys don't match.",dets.getCountryName(),inst.getCountry().getCountryName());
	}
	
	@Test
	public final void testFromDetails()
	{
		Institution inst = DatabaseDataFixture.populateInstUniMelb();
		Institution inst2 = Institution.fromInstDetails(inst.toInstDetails());
		assertEquals("Names don't match.", inst2.getName(), inst.getName());
		assertEquals("Campuses don't match.", inst2.getCampus(),inst.getCampus());
		assertEquals("States don't match.", inst2.getState(),inst.getState());
		assertEquals("Countrys don't match.",inst2.getCountry().getCountryName(),inst.getCountry().getCountryName());
	}

}
