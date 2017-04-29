/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.rest.controller.fixture.RestDataFixture;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class LoginDetailsTest 
{
	LoginDetails dets;
	Iterator<NewsArticleDetails> articles;
	UserDetails user;
	Long userId;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		ArrayList<NewsArticleDetails> nads=new ArrayList<NewsArticleDetails>();
		articles=nads.iterator();
		user=RestDataFixture.customEmailUser();
		userId=23l;
		dets = new LoginDetails(articles,user,userId);
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
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.LoginDetails#getUserId()}.
	 */
	@Test
	public final void testGetUserId()
	{
		assertEquals("UserId does not match expected.",userId,dets.getUserId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.LoginDetails#setUserId(java.util.Long)}.
	 */
	@Test
	public final void testSetUserId()
	{
		Long userId2=15l;
		dets.setUserId(userId2);
		assertEquals("userId does not match expected.",userId2,dets.getUserId());
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
