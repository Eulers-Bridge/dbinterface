/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort.Direction;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.InstitutionMemoryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleMemoryRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

/**
 * @author Greg Newitt
 *
 */
public class NewsEventHandlerTest 
{
	NewsArticleMemoryRepository testRepo;
	NewsEventHandler newsService;
	InstitutionMemoryRepository instRepo;
	
    @Mock
    NewsArticleRepository newsRepos;
    @Mock
	InstitutionRepository institutionRepos;
    @Mock
	UserRepository userRepos;

    NewsEventHandler mockedNewsService;
    
	int page=0;
	int size=10;
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
		MockitoAnnotations.initMocks(this);

		mockedNewsService=new NewsEventHandler(newsRepos,userRepos,institutionRepos);
		
		Map<Long, NewsArticle> newsArticles=DatabaseDataFixture.populateNewsArticles();
		Map<Long,Institution> institutions=DatabaseDataFixture.populateInstitutions();
		instRepo=new InstitutionMemoryRepository(institutions);
		testRepo=new NewsArticleMemoryRepository(newsArticles);
		newsService=new NewsEventHandler(testRepo,userRepos,instRepo);
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
		NewsService newsService=new NewsEventHandler(testRepo,userRepos,instRepo);
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
		when(userRepos.findByEmail(any(String.class))).thenReturn(DatabaseDataFixture.populateUserGnewitt());
		NewsArticleCreatedEvent nace = newsService.createNewsArticle(createNewsArticleEvent);
		assertNotNull("News article created event null.",nace);
		ReadNewsArticleEvent rane=(ReadNewsArticleEvent) newsService.requestReadNewsArticle(new RequestReadNewsArticleEvent(nace.getNewsArticleId()));
		NewsArticleDetails nADs2=(NewsArticleDetails) rane.getDetails();
		assertEquals("Content not equal",nADs.getContent(),nADs2.getContent());
		assertEquals("Creator email not equal",nADs.getCreatorEmail(),nADs2.getCreatorEmail());
		assertEquals("Dates don't match",nADs.getDate(),nADs2.getDate());
		assertEquals("News Article Ids not equal",nace.getNewsArticleId(),nADs2.getNewsArticleId());
		assertEquals("Titles not the same.",nADs.getTitle(),nADs2.getTitle());
//TODO		assertEquals("Pictures not the same.",0,nADs2.getPicture().size());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#requestReadNewsArticle(com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent)}.
	 */
	@Test
	public void testRequestReadNewsArticle() 
	{
		RequestReadNewsArticleEvent rnae=new RequestReadNewsArticleEvent(new Long(1));
		assertEquals("1 == 1",rnae.getNodeId(),new Long(1));
		ReadNewsArticleEvent rane=(ReadNewsArticleEvent) newsService.requestReadNewsArticle(rnae);
		assertNotNull("Null read news article event returned.",rane);
		assertEquals("article ids do not match.",rane.getNodeId(),rnae.getNodeId());
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
		Iterable<PhotoDetails> picture=null;
		nADs.setPhotos(picture);
		nADs.setDate(new Date().getTime());
		
		UpdateNewsArticleEvent updateNewsArticleEvent=new UpdateNewsArticleEvent(nADs.getNewsArticleId(), nADs);
		UpdatedEvent nude = newsService.updateNewsArticle(updateNewsArticleEvent);
		assertNotNull("Null event returned",nude);
		assertEquals("Content was not updated.",((NewsArticleDetails)nude.getDetails()).getContent(),nADs.getContent());
		assertEquals("Title not updated.",((NewsArticleDetails)nude.getDetails()).getTitle(),nADs.getTitle());
		assertEquals("Timestamp not updated.",nADs.getDate(),((NewsArticleDetails)nude.getDetails()).getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#deleteUser(com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent)}.
	 */
	@Test
	public void testDeleteNewsArticle() 
	{
		Long articleId=(long) 1;
		DeleteNewsArticleEvent deleteNewsArticleEvent=new DeleteNewsArticleEvent(articleId);
		DeletedEvent nUDe = newsService.deleteNewsArticle(deleteNewsArticleEvent);
		assertNotNull("Null event returned",nUDe);
		ReadEvent rane=newsService.requestReadNewsArticle(new RequestReadNewsArticleEvent(articleId));
		assertFalse("Entity was not deleted.",rane.isEntityFound());
	}
	
	@Test
	public void testDeleteNonExistentArticle()
	{
		Long articleId=(long) 27;
		DeleteNewsArticleEvent deleteNewsArticleEvent=new DeleteNewsArticleEvent(articleId);
		DeletedEvent nUDe = newsService.deleteNewsArticle(deleteNewsArticleEvent);
		assertNotNull("Null event returned",nUDe);
		assertFalse("",nUDe.isEntityFound());
	}

	@Test
	public void testShouldReadNewsArticles()
	{
		Long instId=(long)1;
		ReadNewsArticlesEvent rnae=new ReadNewsArticlesEvent(instId);
		Direction sortDirection=Direction.DESC;
		NewsArticlesReadEvent nare=newsService.readNewsArticles(rnae,sortDirection,page,size);
		assertNotNull(nare);
		assertTrue(nare.isNewsFeedFound());
		assertTrue(nare.isInstitutionFound());
		Iterable <NewsArticleDetails> artDets=nare.getArticles();
		Iterator <NewsArticleDetails> iter=artDets.iterator();
		int count=0;
		while(iter.hasNext())
		{
			count++;
			iter.next();
		}
		assertEquals(count,2);
	}
		
	@Test
	public void testShouldReadNewsArticlesNonExistentInstId()
	{
		Long instId=(long)28;
		ReadNewsArticlesEvent rnae=new ReadNewsArticlesEvent(instId);
		Direction sortDirection=Direction.DESC;
		NewsArticlesReadEvent nare=newsService.readNewsArticles(rnae,sortDirection,page,size);
		assertNotNull(nare);
		assertFalse(nare.isNewsFeedFound());
		assertFalse(nare.isInstitutionFound());
	}
		
	@Test
	public void testShouldReadNewsArticlesNoArticles()
	{
		Long instId=(long)2;
		ReadNewsArticlesEvent rnae=new ReadNewsArticlesEvent(instId);
		Direction sortDirection=Direction.DESC;
		NewsArticlesReadEvent nare=newsService.readNewsArticles(rnae,sortDirection,page,size);
		assertNotNull(nare);
		assertTrue(nare.isNewsFeedFound());
		assertTrue(nare.isInstitutionFound());
		Iterable <NewsArticleDetails> artDets=nare.getArticles();
		Iterator <NewsArticleDetails> iter=artDets.iterator();
		assertFalse(iter.hasNext());
	}
		
}
