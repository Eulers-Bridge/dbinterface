package com.eulersbridge.iEngage.rest.domain;

import org.junit.*;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

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
		Country testObj=new Country(new Long(61), name, Arrays.asList(unis));
		assertEquals("Country constructor does not return a class of type Country.",Country.class,testObj.getClass()); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Country#getCountryId()}.
	 */
	@Test
	public final void testShouldGetCountryId() 
	{
		Institution unis[]={new Institution(new Long(1), "University of Melbourne")};
		Long countryId=new Long(61);
		String name="Australia";
		Country testObj=new Country(countryId, name, Arrays.asList(unis));
		assertEquals("Id returned does not match id object created with.",countryId,testObj.getCountryId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Country#getCountryName()}.
	 */
	@Test
	public final void testShouldGetCountryName() 
	{
		Institution unis[]={new Institution(new Long(1), "University of Melbourne")};
		String name="Australia";
		Country testObj=new Country(new Long(61), name, Arrays.asList(unis));
		assertEquals("Name returned does not match name object created with.",name,testObj.getCountryName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Country#getCountryName()}.
	 */
	@Test
	public final void testShouldGetUniversityObjects() 
	{
		Institution institutions[]={new Institution(new Long(1), "University of Melbourne")};
		String name="Australia";
		Country testObj=new Country(new Long(61), name, Arrays.asList(institutions));
		assertEquals("Name returned does not match name object created with.",institutions[0],testObj.getInstitutions().iterator().next());
	}


}
