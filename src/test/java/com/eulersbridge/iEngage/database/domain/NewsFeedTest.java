/**
 *
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */
public class NewsFeedTest {
  NewsFeed nf;
  List<NewsArticle> news;

  private static Logger LOG = LoggerFactory.getLogger(NewsFeedTest.class);

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    nf = DatabaseDataFixture.populateNewsFeed2();
    news = new ArrayList<NewsArticle>();
    news.add(DatabaseDataFixture.populateNewsArticle1());
    nf.setNews(Node.castList(news));

  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#toDetails()}.
   */
  @Test
  public void testToDetails() {
    NewsFeedDetails dets = nf.toDetails();
    if (LOG.isDebugEnabled())
      LOG.debug("dets node id " + dets.getNodeId() + " nf inst id " + nf.getNodeId());
    assertEquals("NodeIds don't match", dets.getNodeId(), nf.getNodeId());
    if (LOG.isDebugEnabled())
      LOG.debug("dets inst id " + dets.getInstitutionId() + " nf inst id " + nf.getInstitution().getNodeId());
    assertEquals("InstitutionIds don't match", dets.getInstitutionId(), nf.getInstitution().getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#fromDetails(com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails)}.
   */
  @Test
  public void testFromDetails() {
    NewsFeedDetails dets = nf.toDetails();
    NewsFeed nf2 = NewsFeed.fromDetails(dets);
    assertEquals("NodeIds don't match", dets.getNodeId(), nf2.getNodeId());
    if (LOG.isDebugEnabled())
      LOG.debug("dets inst id " + dets.getInstitutionId() + " nf inst id " + nf.getInstitution().getNodeId());
    assertEquals("InstitutionIds don't match", dets.getInstitutionId(), nf2.getInstitution().getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#getNodeId()}.
   */
  @Test
  public void testGetNodeId() {
    assertEquals("", nf.getNodeId(), new Long(1));
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#setNodeId(java.lang.Long)}.
   */
  @Test
  public void testSetNodeId() {
    nf.setNodeId(new Long(3));
    assertEquals("", nf.getNodeId(), new Long(3));
    nf.setNodeId(new Long(1));
    assertEquals("", nf.getNodeId(), new Long(1));
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#getInstitution()}.
   */
  @Test
  public void testGetInstitution() {
    Institution inst = nf.getInstitution(), ddfinst = DatabaseDataFixture.populateNewsFeed2().getInstitution();
    assertEquals(inst.getName(), ddfinst.getName());
    assertEquals(inst.getCampus(), ddfinst.getCampus());
    assertEquals(inst.getState(), ddfinst.getState());
    assertEquals(inst.getCountry().getCountryName(), ddfinst.getCountry().getCountryName());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#setInstitution(com.eulersbridge.iEngage.database.domain.Institution)}.
   */
  @Test
  public void testSetInstitution() {
    Institution inst = nf.getInstitution();
    String name = "UCLA", campus = "LA", state = "California";
    Long nodeId = 23l;
    Institution inst2 = DatabaseDataFixture.populateInst(nodeId, name, campus, state, DatabaseDataFixture.populateCountryAust());
    nf.setInstitution(inst2);
    assertEquals("", inst2.getName(), name);
    assertEquals("", inst2.getCampus(), campus);
    assertEquals("", inst2.getState(), state);
    assertEquals("", inst2.getCountry().getCountryName(), "Australia");
    nf.setInstitution(inst);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#getNews()}.
   */
  @Test
  public void testGetNews() {
    List<NewsArticle> news;
    news = nf.getNews();
    assertEquals(this.news, news);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#(java.util.Set)}.
   */
  @Test
  public void testSetNews() {
    List<NewsArticle> news = nf.getNews();
    nf.setNews((List<Node>) null);
    assertNull(nf.getNews());
    nf.setNews(Node.castList(news));
    assertEquals(nf.getNews(), news);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#toString()}.
   */
  @Test
  public void testToString() {
    assertNotNull(nf.toString());
  }

  private void checkHashCode(NewsFeed test1, NewsFeed test2) {
    assertNotEquals(test1.hashCode(), test2.hashCode());
    assertNotEquals(test2.hashCode(), test1.hashCode());
  }

  private void checkNotEquals(NewsFeed test1, NewsFeed test2) {
    assertNotEquals(test1, test2);
    assertNotEquals(test2, test1);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#hashCode()}.
   */
  @Test
  public final void testHashCode() {
    NewsFeed nfTest = DatabaseDataFixture.populateNewsFeed2();
    assertEquals(nfTest.hashCode(), nfTest.hashCode());
    assertEquals(nfTest.hashCode(), nf.hashCode());
    nfTest.setNodeId(null);
    checkHashCode(nf, nfTest);
    nf.setNodeId(null);

    nfTest.setInstitution(null);
    checkHashCode(nf, nfTest);
    nfTest.setInstitution(nf.getInstitution());

    nfTest.setNews((List<Node>) null);
    checkHashCode(nf, nfTest);
    nfTest.setNews(Node.castList(nf.getNews()));

  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.NewsFeed#equals(java.lang.Object)}.
   */
  @Test
  public final void testEqualsObject() {
    NewsFeed nfTest = null;
    assertNotEquals(nfTest, nf);
    assertNotEquals(nf, nfTest);
    String notElection = "";
    assertNotEquals(nf, notElection);
    nfTest = DatabaseDataFixture.populateNewsFeed2();
    nfTest.setNews(Node.castList(news));
    assertEquals(nfTest, nfTest);
    assertEquals(nfTest, nf);

    nfTest.setNodeId(54l);
    checkNotEquals(nf, nfTest);
    nf.setNodeId(null);
    checkNotEquals(nf, nfTest);
    nfTest.setNodeId(null);

    assertEquals(nf, nfTest);
    assertEquals(nfTest, nf);

    nfTest.setInstitution(DatabaseDataFixture.populateInstMonashUni());
    assertNotEquals(nf, nfTest);
    nfTest.setInstitution(null);
    checkNotEquals(nfTest, nf);
    nfTest.setInstitution(nf.getInstitution());

    nfTest.setNews((List<Node>) null);
    assertNotEquals(nf, nfTest);
    nfTest.setNews(Node.castList(nf.getNews()));
  }


}
