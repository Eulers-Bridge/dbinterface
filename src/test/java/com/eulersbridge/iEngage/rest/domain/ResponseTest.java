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
public class ResponseTest {

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
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Response#Response(boolean, java.lang.String)}.
	 */
	@Test
	public final void testShouldConstructResponseObject() 
	{
		Response testObj=new Response(true,"Successful");
		if (testObj.getClass()!=Response.class)
			fail("Class created is not of type Response."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Response#getSuccess()}.
	 */
	@Test
	public final void testShouldReturnTrueIfSuccess() 
	{
		boolean res=true;
		String errMesg="Successful";
		Response testObj=new Response(res,errMesg);
		if (testObj.getSuccess()!=res)
			fail("Result not returning true."); 
		
		res=false;
		testObj=new Response(res,errMesg);
		if (testObj.getSuccess()!=res)
			fail("Result not returning false."); 
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Response#getErrorReason()}.
	 */
	@Test
	public final void testShouldReturnErrorReason() 
	{
		boolean res=true;
		String errMesg="Successful";
		Response testObj=new Response(res,errMesg);
		if (!testObj.getErrorReason().equals(errMesg))
			fail("Error Message not matching"+errMesg+"."); 
		
		res=false;
		errMesg="failure";
		testObj=new Response(res,errMesg);
		if (!testObj.getErrorReason().equals(errMesg))
			fail("Error Message not matching"+errMesg+"."); 
	}

}
