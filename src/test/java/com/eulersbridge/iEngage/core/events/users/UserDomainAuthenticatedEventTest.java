/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import org.junit.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class UserDomainAuthenticatedEventTest
{
	SimpleGrantedAuthority auth=new SimpleGrantedAuthority("ROLE_USER");
	List<GrantedAuthority> list;
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
		list=new ArrayList<GrantedAuthority>();
		list.add(auth);	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent#UserAuthenticatedEvent(java.util.List)}.
	 */
	@Test
	public void testUserAuthenticatedEventListOfGrantedAuthority() 
	{
		UserAuthenticatedEvent evt=new UserAuthenticatedEvent(list);
		assertNotNull("Not instantiated.",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent#UserAuthenticatedEvent()}.
	 */
	@Test
	public void testUserAuthenticatedEvent() 
	{
		UserAuthenticatedEvent evt=new UserAuthenticatedEvent();
		assertNotNull("Not instantiated.",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent#badCredentials()}.
	 */
	@Test
	public void testBadCredentials() 
	{
		UserAuthenticatedEvent badCreds=UserAuthenticatedEvent.badCredentials();
		assertEquals("Bad credentials authenticated.",badCreds.isAuthenticated(),false);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent#getGrantedAuths()}.
	 */
	@Test
	public void testGetGrantedAuths() 
	{
		UserAuthenticatedEvent evt=new UserAuthenticatedEvent(list);
		assertNotNull("Not instantiated.",evt);
		assertEquals("Authoritys in list not added into event.",evt.getGrantedAuths().get(0),auth);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent#isAuthenticated()}.
	 */
	@Test
	public void testIsAuthenticated() 
	{
		UserAuthenticatedEvent evt=new UserAuthenticatedEvent();
		assertNotNull("Not instantiated.",evt);
		assertEquals("IsAuthenticated erroneoulsy returning false.",evt.isAuthenticated(),true);
		evt=UserAuthenticatedEvent.badCredentials();
		assertNotNull("Not instantiated.",evt);
		assertEquals("IsAuthenticated erroneoulsy returning true.",evt.isAuthenticated(),false);
	}

}
