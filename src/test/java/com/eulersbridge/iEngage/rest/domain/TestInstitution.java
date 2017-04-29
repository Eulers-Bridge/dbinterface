/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import org.junit.*;

import static org.junit.Assert.assertEquals;

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
		assertEquals("University constructor does not return a class of type university.",Institution.class,testObj.getClass()); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Institution#getUniversityId()}.
	 */
	@Test
	public final void testShouldGetUniversityId() 
	{
		Long instId=new Long(1);
		Institution testObj=new Institution(instId, "University of Melbourne");
		assertEquals("Id returned does not match id object created with.",instId,testObj.getInstitutionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Institution#getUniversityName()}.
	 */
	@Test
	public final void testShouldGetUniversityName() 
	{
		String name="University of Melbourne";
		Institution testObj=new Institution(new Long(1), name);
		assertEquals("Name returned does not match name object created with.",name,testObj.getName());
	}

}
