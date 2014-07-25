/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
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
		testRepo=new NewsArticleMemoryRepository(new HashMap<Long, NewsArticle>());
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
		Long id=new Long(1);
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
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#deleteUser(com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent)}.
	 */
	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

}
