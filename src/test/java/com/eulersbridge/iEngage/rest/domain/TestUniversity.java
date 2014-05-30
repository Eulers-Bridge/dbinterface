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
public class TestUniversity 
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
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.University#University(int, java.lang.String)}.
	 */
	@Test
	public final void testShouldCreateUniversityObject() 
	{
		University testObj=new University(1, "University of Melbourne");
		if (testObj.getClass()!=University.class)
		fail("University constructor does not return a class of type university."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.University#getUniversityId()}.
	 */
	@Test
	public final void testShouldGetUniversityId() 
	{
		University testObj=new University(1, "University of Melbourne");
		if (testObj.getUniversityId()!=1)
			fail("Id returned does not match id object created with.");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.University#getUniversityName()}.
	 */
	@Test
	public final void testShouldGetUniversityName() 
	{
		String name="University of Melbourne";
		University testObj=new University(1, name);
		if (!testObj.getUniversityName().equals(name))
			fail("Name returned does not match name object created with.");
	}

}
