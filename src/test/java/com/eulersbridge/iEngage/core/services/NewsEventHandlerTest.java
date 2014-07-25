/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.NewsArticleMemoryRepository;

/**
 * @author Greg Newitt
 *
 */
public class NewsEventHandlerTest 
{
	NewsArticleMemoryRepository testRepo;
	NewsEventHandler newsService;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
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
		HashMap<Long, NewsArticle> newsArticles=new HashMap<Long, NewsArticle>();
		User creator=new User("gnewitt@hotmail.com", "Greg", "Newitt", "Male", "Australian", "1971", "None", "test123");
		Iterable<String> picture=null;
		NewsArticle initialArticle=new NewsArticle("Test Article", "Contents of the Test Article", picture, Calendar.getInstance(), creator);
		newsArticles.put(new Long(1), initialArticle);
		testRepo=new NewsArticleMemoryRepository(newsArticles);
		newsService=new NewsEventHandler(testRepo);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#NewsEventHandler(com.eulersbridge.iEngage.database.repository.NewsArticleRepository)}.
	 */
	@Test
	public void testNewsEventHandler() 
	{
		NewsService newsService=new NewsEventHandler(testRepo);
		assertNotNull("newsService not being created by constructor.",newsService);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#createNewsArticle(com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent)}.
	 */
	@Test
	public void testCreateNewsArticle() 
	{
		CreateNewsArticleEvent createNewsArticleEvent;
		NewsArticleDetails nADs;
		Long id=new Long(2);
		nADs=new NewsArticleDetails(id);
		nADs.setDate(new Date().getTime());
		nADs.setCreatorEmail("gnewitt@hotmail.com");
		nADs.setContent("Per ardua ad astra.");
		nADs.setTitle("Per ardua ad astra.");
		createNewsArticleEvent=new CreateNewsArticleEvent(nADs);
		NewsArticleCreatedEvent nace = newsService.createNewsArticle(createNewsArticleEvent);
		if (null==nace) fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#requestReadUser(com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent)}.
	 */
	@Test
	public void testRequestReadUser() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#updateUser(com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent)}.
	 */
	@Test
	public void testUpdateUser() 
	{
		NewsArticleDetails nADs;
		nADs=new NewsArticleDetails(new Long(1));
		nADs.setContent("Blah blah");
		nADs.setTitle("Whatever");
		Iterable<String> picture=null;
		nADs.setPicture(picture);
		nADs.setDate(new Date().getTime());
		
		UpdateNewsArticleEvent updateNewsArticleEvent=new UpdateNewsArticleEvent(nADs.getNewsArticleId(), nADs);
		NewsArticleUpdatedEvent nude = newsService.updateUser(updateNewsArticleEvent);
		if (null==nude) fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#deleteUser(com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent)}.
	 */
	@Test
	public void testDeleteUser() 
	{
		fail("Not yet implemented");
	}

}
