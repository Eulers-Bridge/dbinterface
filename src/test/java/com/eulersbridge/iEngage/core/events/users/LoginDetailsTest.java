/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;

/**
 * @author Emma
 *
 */
public class LoginDetailsTest 
{
	LoginDetails dets;
	Iterator<NewsArticleDetails> articles;
	UserDetails user;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		articles=null;
		user=RestDataFixture.customEmailUser();
		dets = new LoginDetails(articles,user);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.LoginDetails#LoginDetails(java.util.Iterator, com.eulersbridge.iEngage.core.events.users.UserDetails)}.
	 */
	@Test
	public final void testLoginDetails() 
	{
		assertNotNull("login details not created.",dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.LoginDetails#getArticles()}.
	 */
	@Test
	public final void testGetArticles() 
	{
		assertEquals("Articles does not match expected.",articles,dets.getArticles());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.LoginDetails#getUser()}.
	 */
	@Test
	public final void testGetUser() {
		assertEquals("User does not match expected.",user,dets.getUser());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.LoginDetails#setArticles(java.util.Iterator)}.
	 */
	@Test
	public final void testSetArticles()
	{
		Iterator<NewsArticleDetails> articles2=null;
		dets.setArticles(articles2);
		assertEquals("Articles does not match expected.",articles2,dets.getArticles());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.LoginDetails#setUser(com.eulersbridge.iEngage.core.events.users.UserDetails)}.
	 */
	@Test
	public final void testSetUser() 
	{
		UserDetails user2=RestDataFixture.customEmailUser("other@isegoria.com");
		dets.setUser(user2);
		assertEquals("User does not match expected.",user2,dets.getUser());
	}

}
