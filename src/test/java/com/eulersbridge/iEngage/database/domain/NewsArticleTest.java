/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class NewsArticleTest 
{
	NewsArticle news;
	final String title="The title";
	final String content="The content.";
	final Calendar date=Calendar.getInstance();
	final User creator=DatabaseDataFixture.populateUserGnewitt2();
	final NewsFeed year=DatabaseDataFixture.populateNewsFeed2();
	final Long node1=new Long(1);
	final Long node2=new Long(2);
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
		news=new NewsArticle(title, content, date, creator);
		news.setNodeId(node1);
		news.setNewsFeed(year);
		Set<Like> likes1=new HashSet<Like>();
		news.setLikes(likes1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#NewsArticle()}.
	 */
	@Test
	public void testNewsArticle() 
	{
		NewsArticle news2=new NewsArticle();
		assertNotNull("Empty constructor did not create object.",news2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#NewsArticle(java.lang.String, java.lang.String, java.lang.Iterable, java.util.Calendar, com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public void testNewsArticleStringStringIterableOfStringCalendarUser() 
	{
		NewsArticle news2=new NewsArticle(title, content, date, creator);
		assertNotNull("Loaded Constructor did not create object.",news2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#getNodeId()}.
	 */
	@Test
	public void testGetNodeId() 
	{
		assertEquals("node ids don't match.",node1,news.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#setNodeId(java.lang.Long)}.
	 */
	@Test
	public void testSetNodeId() 
	{
		news.setNodeId(node2);
		assertEquals("node ids don't match.",node2,news.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#getTitle()}.
	 */
	@Test
	public void testGetTitle() 
	{
		assertEquals("Titles don't match",title,news.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#setTitle(java.lang.String)}.
	 */
	@Test
	public void testSetTitle() 
	{
		String title="Some title";
		news.setTitle(title);
		assertEquals("dates don't match.",title,news.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#getContent()}.
	 */
	@Test
	public void testGetContent() 
	{
		assertEquals("Content doesn't match.",content,news.getContent());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#setContent(java.lang.String)}.
	 */
	@Test
	public void testSetContent() 
	{
		String content="Some content";
		news.setContent(content);
		assertEquals("dates don't match.",content,news.getContent());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#getPhotos()}.
	 */
	@Test
	public void testGetPicture() 
	{
		assertNull("pictures don't match.",news.getPhotos());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#getLikers()}.
	 */
	@Test
	public void testGetLikes() 
	{
		assertEquals("No likers at this point.",0,news.getLikes().size());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#getDate()}.
	 */
	@Test
	public void testGetDate() 
	{
		assertEquals("Dates don't match.",new Long(date.getTimeInMillis()),news.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#setDate(java.lang.Long)}.
	 */
	@Test
	public void testSetDate() 
	{
		news.setDate(node2);
		assertEquals("dates don't match.",node2,news.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#getCreator()}.
	 */
	@Test
	public void testGetCreator() 
	{
		assertEquals("",news.getCreator(),creator);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#setCreator(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public void testSetCreator() 
	{
		User creator2=DatabaseDataFixture.populateUserGnewitt();
		news.setCreator(creator2);
		assertNotEquals("",news.getCreator(),creator);
		assertEquals("",news.getCreator(),creator2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#toNewsArticleDetails()}.
	 */
	@Test
	public void testToNewsArticleDetails() 
	{
		NewsArticleDetails dets = news.toNewsArticleDetails();
		assertEquals("Titles don't match.",dets.getTitle(),news.getTitle());
		assertEquals("Contents don't match.",dets.getContent(),news.getContent());
		assertEquals("Dates don't match.",dets.getDate(),news.getDate());
		assertEquals("Creator emails don't match.",dets.getCreatorEmail(),news.getCreator().getEmail());
		assertEquals("IDs don't match.",dets.getNewsArticleId(),news.getNodeId());
//TODO		assertEquals("Pictures don't match.",dets.getPicture().size(),0);
		assertEquals("Likes don't match.",dets.getLikes().intValue(),news.getLikes().size());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#fromNewsArticleDetails(com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails)}.
	 */
	@Test
	public void testFromNewsArticleDetails() 
	{
		NewsArticleDetails dets = news.toNewsArticleDetails();
		NewsArticle news2 = NewsArticle.fromNewsArticleDetails(dets);
		assertEquals("Titles don't match.",news2.getTitle(),news.getTitle());
		assertEquals("Contents don't match.",news2.getContent(),news.getContent());
		assertEquals("Dates don't match.",news2.getDate(),news.getDate());
		assertEquals("Creator emails don't match.",news2.getCreator().getEmail(),news.getCreator().getEmail());
		assertEquals("IDs don't match.",news2.getNodeId(),news.getNodeId());
//TODO		assertEquals("Pictures don't match.",news2.getPicture(),dets.getPicture());
		assertNull("Likers don't match.",news2.getLikes());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#hashCode()}.
	 */
	@Test
	public final void testHashCode() 
	{
		NewsArticle articleTest=DatabaseDataFixture.populateNewsArticle1();
		NewsArticle newsTest=DatabaseDataFixture.populateNewsArticle1();
		assertEquals(articleTest.hashCode(),articleTest.hashCode());
		assertEquals(articleTest.hashCode(),newsTest.hashCode());
		articleTest.setNodeId(null);
		assertNotEquals(newsTest.hashCode(), articleTest.hashCode());
		assertNotEquals(articleTest.hashCode(), newsTest.hashCode());
		newsTest.setNodeId(null);
		articleTest.setCreator(null);
		assertNotEquals(newsTest.hashCode(), articleTest.hashCode());
		assertNotEquals(articleTest.hashCode(), newsTest.hashCode());
		articleTest.setCreator(newsTest.getCreator());
		articleTest.setNewsFeed(null);
		assertNotEquals(newsTest.hashCode(), articleTest.hashCode());
		assertNotEquals(articleTest.hashCode(), newsTest.hashCode());
		articleTest.setNewsFeed(newsTest.getNewsFeed());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsArticle#equals(com.eulersbridge.iEngage.database.domain.NewsArticle)}.
	 */
	@Test
	public void testEqualsNewsArticle() 
	{
		NewsArticle news1 = DatabaseDataFixture.populateNewsArticle1();
		NewsArticle news2=null;
		assertNotEquals(news2,news1);
		assertNotEquals(news1,news2);
		String notElection="";
		assertNotEquals(news1,notElection);
		news2 = DatabaseDataFixture.populateNewsArticle1();
		news2.setDate(news1.getDate());
		assertEquals(news2,news2);
		assertEquals(news2,news1);
		news2.setNodeId(54l);
		assertNotEquals(news1, news2);
		assertNotEquals(news2, news1);
		news1.setNodeId(null);
		assertNotEquals(news1, news2);
		assertNotEquals(news2, news1);
		news2.setNodeId(null);
		assertEquals(news1, news2);
		assertEquals(news2, news1);
		
		news2.setCreator(creator);
		assertNotEquals(news1, news2);
		news2.setCreator(null);
		assertNotEquals(news1, news2);
		assertNotEquals(news2, news1);
		news2.setCreator(news1.getCreator());
		
		news2.setDate(415l);
		assertNotEquals(news1, news2);
		news2.setDate(null);
		assertNotEquals(news1, news2);
		assertNotEquals(news2, news1);
		news2.setDate(news1.getDate());
		
		news2.setTitle("A title");
		assertNotEquals(news1, news2);
		news2.setTitle(null);
		assertNotEquals(news1, news2);
		assertNotEquals(news2, news1);
		news2.setTitle(news1.getTitle());
		
		news2.setContent("A content");
		assertNotEquals(news1, news2);
		news2.setContent(null);
		assertNotEquals(news1, news2);
		assertNotEquals(news2, news1);
		news2.setContent(news1.getContent());
		
		news2.setNewsFeed(DatabaseDataFixture.populateNewsFeed1());
		assertNotEquals(news1, news2);
		news2.setNewsFeed(null);
		assertNotEquals(news1, news2);
		assertNotEquals(news2, news1);
		news2.setNewsFeed(news1.getNewsFeed());
	}

	@Test
	public void testToString() 
	{
		NewsArticleDetails dets = news.toNewsArticleDetails();
		NewsArticle news2 = NewsArticle.fromNewsArticleDetails(dets);
		assertNotNull(news2.toString());
	}

}
