/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

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
public class TestInstitution 
{

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
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Institution#University(int, java.lang.String)}.
	 */
	@Test
	public final void testShouldCreateUniversityObject() 
	{
		Institution testObj=new Institution(new Long(1), "University of Melbourne");
		if (testObj.getClass()!=Institution.class)
		fail("University constructor does not return a class of type university."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Institution#getUniversityId()}.
	 */
	@Test
	public final void testShouldGetUniversityId() 
	{
		Institution testObj=new Institution(new Long(1), "University of Melbourne");
		if (testObj.getInstitutionId().compareTo(new Long(1))!=0)
			fail("Id returned does not match id object created with.");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Institution#getUniversityName()}.
	 */
	@Test
	public final void testShouldGetUniversityName() 
	{
		String name="University of Melbourne";
		Institution testObj=new Institution(new Long(1), name);
		if (!testObj.getName().equals(name))
			fail("Name returned does not match name object created with.");
	}

}
