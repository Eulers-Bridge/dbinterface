/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.StudentYear;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.InstitutionMemoryRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleMemoryRepository;
import com.eulersbridge.iEngage.database.repository.StudentYearMemoryRepository;
import com.eulersbridge.iEngage.database.repository.StudentYearRepository;
import com.eulersbridge.iEngage.database.repository.UserMemoryRepository;

/**
 * @author Greg Newitt
 *
 */
public class NewsEventHandlerTest 
{
	NewsArticleMemoryRepository testRepo;
	UserMemoryRepository userRepo;
	NewsEventHandler newsService;
	InstitutionMemoryRepository instRepo;
	StudentYearRepository syRepo;
	
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
		Map<Long, NewsArticle> newsArticles=DatabaseDataFixture.populateNewsArticles();
		Map<Long, User> users=DatabaseDataFixture.populateUsers();
		userRepo=new UserMemoryRepository(users);
		Map<Long,Institution> institutions=DatabaseDataFixture.populateInstitutions();
		instRepo=new InstitutionMemoryRepository(institutions);
		Map<Long, StudentYear> years=DatabaseDataFixture.populateStudentYears();
		syRepo=new StudentYearMemoryRepository(years);
		testRepo=new NewsArticleMemoryRepository(newsArticles);
		newsService=new NewsEventHandler(testRepo,userRepo,instRepo,syRepo);
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
		NewsService newsService=new NewsEventHandler(testRepo,userRepo,instRepo,syRepo);
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
		nADs=new NewsArticleDetails();
		nADs.setDate(new Date().getTime());
		nADs.setCreatorEmail("gnewitt@hotmail.com");
		nADs.setContent("Per ardua ad astra.");
		nADs.setTitle("Per ardua ad astra.");
		nADs.setInstitutionId((long)1);
		createNewsArticleEvent=new CreateNewsArticleEvent(nADs);
		NewsArticleCreatedEvent nace = newsService.createNewsArticle(createNewsArticleEvent);
//		nace.
		assertNotNull("News article created event null.",nace);
		ReadNewsArticleEvent rane=newsService.requestReadNewsArticle(new RequestReadNewsArticleEvent(nace.getNewsArticleId()));
		NewsArticleDetails nADs2=rane.getReadNewsArticleDetails();
		assertEquals("Content not equal",nADs.getContent(),nADs2.getContent());
		assertEquals("Creator email not equal",nADs.getCreatorEmail(),nADs2.getCreatorEmail());
		assertEquals("Dates don't match",nADs.getDate(),nADs2.getDate());
		assertEquals("News Article Ids not equal",nace.getNewsArticleId(),nADs2.getNewsArticleId());
		assertEquals("Titles not the same.",nADs.getTitle(),nADs2.getTitle());
		assertEquals("Pictures not the same.",nADs.getPicture(),nADs2.getPicture());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#requestReadUser(com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent)}.
	 */
	@Test
	public void testRequestReadNewsArticle() 
	{
		RequestReadNewsArticleEvent rnae=new RequestReadNewsArticleEvent(new Long(1));
		assertEquals("1 == 1",rnae.getNewsArticleId(),new Long(1));
		ReadNewsArticleEvent rane=newsService.requestReadNewsArticle(rnae);
		assertNotNull("Null read news article event returned.",rane);
		assertEquals("article ids do not match.",rane.getNewsArticleId(),rnae.getNewsArticleId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#updateUser(com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent)}.
	 */
	@Test
	public void testUpdateNewsArticle() 
	{
		NewsArticleDetails nADs;
		nADs=new NewsArticleDetails();
		nADs.setNewsArticleId((long)1);
		nADs.setContent("Blah blah");
		nADs.setTitle("Whatever");
		Set<String> picture=null;
		nADs.setPicture(picture);
		nADs.setDate(new Date().getTime());
		
		UpdateNewsArticleEvent updateNewsArticleEvent=new UpdateNewsArticleEvent(nADs.getNewsArticleId(), nADs);
		NewsArticleUpdatedEvent nude = newsService.updateNewsArticle(updateNewsArticleEvent);
		assertNotNull("Null event returned",nude);
		assertEquals("Content was not updated.",nude.getNewsArticleDetails().getContent(),nADs.getContent());
		assertEquals("Title not updated.",nude.getNewsArticleDetails().getTitle(),nADs.getTitle());
		assertEquals("Timestamp not updated.",nADs.getDate(),nude.getNewsArticleDetails().getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#deleteUser(com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent)}.
	 */
	@Test
	public void testDeleteNewsArticle() 
	{
		Long articleId=(long) 1;
		DeleteNewsArticleEvent deleteNewsArticleEvent=new DeleteNewsArticleEvent(articleId);
		NewsArticleDeletedEvent nUDe = newsService.deleteNewsArticle(deleteNewsArticleEvent);
		assertNotNull("Null event returned",nUDe);
		ReadNewsArticleEvent rane=newsService.requestReadNewsArticle(new RequestReadNewsArticleEvent(articleId));
		assertFalse("Entity was not deleted.",rane.isEntityFound());
	}

	@Test
	public void testShouldReadNewsArticles()
	{
		Long syId=(long)1;
		Long instId=(long)1;
		ReadNewsArticlesEvent rnae=new ReadNewsArticlesEvent(instId, syId);
		NewsArticlesReadEvent nare=newsService.readNewsArticles(rnae);
		assertNotNull(nare);
		Iterable <NewsArticleDetails> artDets=nare.getArticles();
		Iterator <NewsArticleDetails> iter=artDets.iterator();
		int count=0;
		while(iter.hasNext())
		{
			count++;
			NewsArticleDetails dets=iter.next();
		}
		assertEquals(count,2);
	}
		
	@Test
	public void testShouldReadNewsArticlesWithSYID1Only()
	{
		Iterable<String> picture=null;
		NewsArticle na=DatabaseDataFixture.populateNewsArticle("Different year test", "Testing to see if a different year will be picked up.", picture, DatabaseDataFixture.populateUserGnewitt2(), Calendar.getInstance(), null, DatabaseDataFixture.populateStudentYear2013());
		testRepo.save(na);
		Long syId=(long)1;
		Long instId=(long)1;
		
		ReadNewsArticlesEvent rnae=new ReadNewsArticlesEvent(instId, syId);
		NewsArticlesReadEvent nare=newsService.readNewsArticles(rnae);
		assertNotNull(nare);
		Iterable <NewsArticleDetails> artDets=nare.getArticles();
		Iterator <NewsArticleDetails> iter=artDets.iterator();
		int count=0;
		while(iter.hasNext())
		{
			count++;
			NewsArticleDetails dets=iter.next();
		}
		assertEquals(count,2);
	}
	
	@Test
	public void testShouldReadNewsArticlesWithSYID2Only()
	{
		Iterable<String> picture=null;
		NewsArticle na=DatabaseDataFixture.populateNewsArticle("Different year test", "Testing to see if a different year will be picked up.", picture, DatabaseDataFixture.populateUserGnewitt2(), Calendar.getInstance(), null, DatabaseDataFixture.populateStudentYear2013());
		testRepo.save(na);
		Long syId=(long)2;
		Long instId=(long)1;

		ReadNewsArticlesEvent rnae=new ReadNewsArticlesEvent(instId, syId);
		NewsArticlesReadEvent nare=newsService.readNewsArticles(rnae);
		assertNotNull(nare);
		Iterable <NewsArticleDetails> artDets=nare.getArticles();
		Iterator <NewsArticleDetails> iter=artDets.iterator();
		int count=0;
		NewsArticleDetails dets=null;
		while(iter.hasNext())
		{
			count++;
			dets=iter.next();
		}
		assertEquals(count,1);
		assertEquals(dets.getTitle(), "Different year test");
		assertEquals(dets.getContent(), "Testing to see if a different year will be picked up.");

		
		
	}
}
