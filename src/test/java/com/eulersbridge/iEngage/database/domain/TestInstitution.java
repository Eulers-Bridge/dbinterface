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

/**
 * @author Greg Newitt
 *
 */
public class TestInstitution {

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
		Institution testObj=new Institution("University of Melbourne","Parkville","VIC","Australia");
		if (testObj.getClass()!=Institution.class)
			fail("University constructor does not return a class of type institution."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getName()}.
	 */
	@Test
	public final void testGetName() 
	{
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		String country="Australia";
		Institution testObj=new Institution(name,campus,state,country);
		if (testObj.getName()!=name)
			fail("getName() does not return the value class was constructed with."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName() 
	{
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		String country="Australia";
		Institution testObj=new Institution(name,campus,state,country);
		String name2="Monash University";
		testObj.setName(name2);
		if (testObj.getName()!=name2)
			fail("getName() does not return the value that setter should have set it to."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getCampus()}.
	 */
	@Test
	public final void testGetCampus() 
	{
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		String country="Australia";
		Institution testObj=new Institution(name,campus,state,country);
		if (testObj.getCampus()!=campus)
			fail("getCampus() does not return the value class was constructed with."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setCampus(java.lang.String)}.
	 */
	@Test
	public final void testSetCampus() {
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		String country="Australia";
		Institution testObj=new Institution(name,campus,state,country);
		String campus2="Clayton";
		testObj.setCampus(campus2);
		if (testObj.getCampus()!=campus2)
			fail("getCampus() does not return the value that setter should have set it to."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getState()}.
	 */
	@Test
	public final void testGetState() {
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		String country="Australia";
		Institution testObj=new Institution(name,campus,state,country);
		if (testObj.getState()!=state)
			fail("getState() does not return the value class was constructed with."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setState(java.lang.String)}.
	 */
	@Test
	public final void testSetState() {
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		String country="Australia";
		Institution testObj=new Institution(name,campus,state,country);
		String state2="NSW";
		testObj.setState(state2);
		if (testObj.getState()!=state2)
			fail("getState() does not return the value that setter should have set it to."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#getCountry()}.
	 */
	@Test
	public final void testGetCountry() 
	{
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		String country="Australia";
		Institution testObj=new Institution(name,campus,state,country);
		if (testObj.getCountry()!=country)
			fail("getCountry() does not return the value class was constructed with."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#setCountry(java.lang.String)}.
	 */
	@Test
	public final void testSetCountry() {
		String name="University of Melbourne";
		String campus="Parkville";
		String state="VIC";
		String country="Australia";
		Institution testObj=new Institution(name,campus,state,country);
		String country2="UK";
		testObj.setCountry(country2);
		if (testObj.getCountry()!=country2)
			fail("getCountry() does not return the value that setter should have set it to."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Institution#toString()}.
	 */
	@Test
	public final void testToString() {
	}

}
