package com.eulersbridge.iEngage.rest.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCountry {

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
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Country#Country(int, java.lang.String,Institution[])}.
	 */
	@Test
	public final void testShouldCreateCountryObject() 
	{
		Institution unis[]={new Institution(new Long(1), "University of Melbourne")};
		String name="Australia";
		Country testObj=new Country(new Long(61), name, unis);
		if (testObj.getClass()!=Country.class)
			fail("Country constructor does not return a class of type Country."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Country#getCountryId()}.
	 */
	@Test
	public final void testShouldGetCountryId() 
	{
		Institution unis[]={new Institution(new Long(1), "University of Melbourne")};
		String name="Australia";
		Country testObj=new Country(new Long(61), name, unis);
		if (testObj.getCountryId()!=61)
			fail("Id returned does not match id object created with.");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Country#getCountryName()}.
	 */
	@Test
	public final void testShouldGetCountryName() 
	{
		Institution unis[]={new Institution(new Long(1), "University of Melbourne")};
		String name="Australia";
		Country testObj=new Country(new Long(61), name, unis);
		if (!testObj.getCountryName().equals(name))
			fail("Name returned does not match name object created with.");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Country#getCountryName()}.
	 */
	@Test
	public final void testShouldGetUniversityObjects() 
	{
		Institution unis[]={new Institution(new Long(1), "University of Melbourne")};
		String name="Australia";
		Country testObj=new Country(new Long(61), name, unis);
		if (!testObj.getUniversities()[0].equals(unis[0]))
			fail("Name returned does not match name object created with.");
	}


}
