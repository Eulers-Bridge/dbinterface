/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.*;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Greg Newitt
 */
public class NewsEventHandlerTest {
  @Mock
  NewsArticleRepository newsRepos;
  @Mock
  InstitutionRepository institutionRepos;
  @Mock
  UserRepository userRepos;

  NewsEventHandler service;

  int page = 0;
  int size = 10;

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
    MockitoAnnotations.initMocks(this);

    service = new NewsEventHandler(newsRepos, userRepos, institutionRepos);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler(com.eulersbridge.iEngage.database.repository.NewsArticleRepository)}.
   */
  @Test
  public void testNewsEventHandler() {
    NewsService newsService = new NewsEventHandler(newsRepos, userRepos, institutionRepos);
    assertNotNull("newsService not being created by constructor.", newsService);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#createNewsArticle(com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent)}.
   */
  @Test
  public void testCreateNewsArticle() {
    CreateNewsArticleEvent createNewsArticleEvent;
    NewsArticle article = DatabaseDataFixture.populateNewsArticle1();
    NewsArticleDetails nADs = article.toNewsArticleDetails();
    createNewsArticleEvent = new CreateNewsArticleEvent(nADs);
    when(userRepos.findByEmail(any(String.class))).thenReturn(DatabaseDataFixture.populateUserGnewitt());
    when(institutionRepos.findNewsFeed(any(Long.class))).thenReturn(DatabaseDataFixture.populateNewsFeed1());
    when(newsRepos.save(any(NewsArticle.class))).thenReturn(article);
    NewsArticleCreatedEvent nace = service.createNewsArticle(createNewsArticleEvent);
    assertNotNull("News article created event null.", nace);
    assertEquals(nace.getDetails(), article.toNewsArticleDetails());
    assertEquals(nace.getNewsArticleId(), article.getNodeId());
    assertEquals(nace.getNodeId(), article.getNodeId());
    assertTrue(nace.isCreatorFound());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler#requestReadNewsArticle(com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent)}.
   */
  @Test
  public void testRequestReadNewsArticle() {
    NewsArticle value = DatabaseDataFixture.populateNewsArticle1();
    RequestReadNewsArticleEvent rnae = new RequestReadNewsArticleEvent(value.getNodeId());
    when(newsRepos.findOne(any(Long.class))).thenReturn(value);
    ReadNewsArticleEvent rane = (ReadNewsArticleEvent) service.requestReadNewsArticle(rnae);
    assertNotNull("Null read news article event returned.", rane);
    assertEquals(rane.getDetails(), value.toNewsArticleDetails());
    assertEquals("article ids do not match.", rane.getNodeId(), rnae.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler(com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent)}.
   */
  @Test
  public void testUpdateNewsArticle() {
    NewsArticleDetails nADs;
    NewsArticle article = DatabaseDataFixture.populateNewsArticle1();
    nADs = article.toNewsArticleDetails();

    when(userRepos.findByEmail(any(String.class))).thenReturn(DatabaseDataFixture.populateUserGnewitt());
    when(institutionRepos.findNewsFeed(any(Long.class))).thenReturn(DatabaseDataFixture.populateNewsFeed1());
    when(newsRepos.save(any(NewsArticle.class))).thenReturn(article);

    UpdateNewsArticleEvent updateNewsArticleEvent = new UpdateNewsArticleEvent(nADs.getNewsArticleId(), nADs);
    UpdatedEvent nude = service.updateNewsArticle(updateNewsArticleEvent);
    assertNotNull("News article updated event null.", nude);
    assertEquals(nude.getDetails(), article.toNewsArticleDetails());
    assertEquals(nude.getNodeId(), article.getNodeId());
    assertTrue(nude.isEntityFound());
    assertFalse(nude.isFailed());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.core.services.NewsEventHandler(com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent)}.
   */
  @Test
  public void testDeleteNewsArticle() {
    Long articleId = (long) 1;
    DeleteNewsArticleEvent deleteNewsArticleEvent = new DeleteNewsArticleEvent(articleId);
    DeletedEvent nUDe = service.deleteNewsArticle(deleteNewsArticleEvent);
    assertNotNull("Null event returned", nUDe);
  }

  @Test
  public void testDeleteNonExistentArticle() {
    Long articleId = (long) 27;
    DeleteNewsArticleEvent deleteNewsArticleEvent = new DeleteNewsArticleEvent(articleId);
    when(newsRepos.exists(any(Long.class))).thenReturn(false);
    DeletedEvent nUDe = service.deleteNewsArticle(deleteNewsArticleEvent);
    assertNotNull("Null event returned", nUDe);
    assertFalse("", nUDe.isEntityFound());
  }

  @Test
  public void testShouldReadNewsArticles() {
    Long instId = (long) 1;
    ReadAllEvent rnae = new ReadAllEvent(instId);
    Direction sortDirection = Direction.DESC;
    int pageLength = 10;
    int pageNumber = 0;

    ArrayList<NewsArticle> evts = new ArrayList<>();

    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "a.date");
    Page<NewsArticle> value = new PageImpl<>(evts, pageable, evts.size());
    when(newsRepos.findByInstitutionId(any(Long.class), any(Pageable.class))).thenReturn(value);

    NewsArticlesReadEvent nare = service.readNewsArticles(rnae, sortDirection, page, size);
    assertNotNull(nare);
    assertTrue(nare.isNewsFeedFound());
    assertTrue(nare.isInstitutionFound());
    Iterable<NewsArticleDetails> artDets = nare.getArticles();
    Iterator<NewsArticleDetails> iter = artDets.iterator();
    int count = 0;
    while (iter.hasNext()) {
      count++;
      iter.next();
    }
    assertEquals(count, 2);
  }

  @Test
  public void testShouldReadNewsArticlesNonExistentInstId() {
    Long instId = (long) 28;
    ReadAllEvent rnae = new ReadAllEvent(instId);
    Direction sortDirection = Direction.DESC;
    NewsArticlesReadEvent nare = service.readNewsArticles(rnae, sortDirection, page, size);
    assertNotNull(nare);
    assertFalse(nare.isNewsFeedFound());
    assertFalse(nare.isInstitutionFound());
  }

  @Test
  public void testShouldReadNewsArticlesNoArticles() {
    Long instId = (long) 2;
    ReadAllEvent rnae = new ReadAllEvent(instId);
    Direction sortDirection = Direction.DESC;

    int pageLength = 10;
    int pageNumber = 0;

    ArrayList<NewsArticle> evts = new ArrayList<>();

    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "a.date");
    Page<NewsArticle> value = new PageImpl<>(evts, pageable, evts.size());
    when(newsRepos.findByInstitutionId(any(Long.class), any(Pageable.class))).thenReturn(value);
    when(institutionRepos.findOne(any(Long.class))).thenReturn(DatabaseDataFixture.populateInstUniMelb());
    NewsArticlesReadEvent nare = service.readNewsArticles(rnae, sortDirection, page, size);
    assertNotNull(nare);
    assertTrue(nare.isNewsFeedFound());
    assertTrue(nare.isInstitutionFound());
    Iterable<NewsArticleDetails> artDets = nare.getArticles();
    Iterator<NewsArticleDetails> iter = artDets.iterator();
    assertFalse(iter.hasNext());
  }

}
