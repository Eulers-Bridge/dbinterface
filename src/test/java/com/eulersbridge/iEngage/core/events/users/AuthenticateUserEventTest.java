/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

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
public class AuthenticateUserEventTest 
{
	AuthenticateUserEvent authEvt;
	String user;
	String password;
	
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
		password="test";
		user="gnewitt";
		authEvt=new AuthenticateUserEvent(user, password);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.AuthenticateUserEvent#AuthenticateUserEvent(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAuthenticateUserEvent() 
	{
		AuthenticateUserEvent authEvt2 = new AuthenticateUserEvent(user, password);
		assertNotNull("Coulnd not instantiate.",authEvt2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.AuthenticateUserEvent#getUserName()}.
	 */
	@Test
	public void testGetUserName() 
	{
		assertEquals("User name did not match.",authEvt.getUserName(),user);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.AuthenticateUserEvent#getPassword()}.
	 */
	@Test
	public void testGetPassword() 
	{
		assertEquals("Password did not match.",authEvt.getPassword(),password);
	}

}
