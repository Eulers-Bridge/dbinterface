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
public class TestInstitutionDomain
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
	 * Test method for {@link InstitutionDomain#University(int, java.lang.String)}.
	 */
	@Test
	public final void testShouldCreateUniversityObject() 
	{
		InstitutionDomain testObj=new InstitutionDomain(new Long(1), "University of Melbourne");
		assertEquals("University constructor does not return a class of type university.",InstitutionDomain.class,testObj.getClass());
	}

	/**
	 * Test method for {@link InstitutionDomain#getUniversityId()}.
	 */
	@Test
	public final void testShouldGetUniversityId() 
	{
		Long instId=new Long(1);
		InstitutionDomain testObj=new InstitutionDomain(instId, "University of Melbourne");
		assertEquals("Id returned does not match id object created with.",instId,testObj.getInstitutionId());
	}

	/**
	 * Test method for {@link InstitutionDomain#getUniversityName()}.
	 */
	@Test
	public final void testShouldGetUniversityName() 
	{
		String name="University of Melbourne";
		InstitutionDomain testObj=new InstitutionDomain(new Long(1), name);
		assertEquals("Name returned does not match name object created with.",name,testObj.getName());
	}

}
