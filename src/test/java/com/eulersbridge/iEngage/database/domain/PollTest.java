/**
 *
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */
public class PollTest {
  Poll poll;
  PollDetails dets;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    poll = DatabaseDataFixture.populatePoll1();
    dets = poll.toPollDetails();
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#Poll()}.
   */
  @Test
  public final void testPoll() {
    poll = new Poll();
    assertNotNull(poll);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#Poll(java.lang.String, java.lang.String, java.lang.Long, java.lang.Long)}.
   */
  @Test
  public final void testPollStringStringLongLong() {
    assertNotNull(poll);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#toPollDetails()}.
   */
  @Test
  public final void testToPollDetails() {
    PollDetails dets = poll.toPollDetails();
    assertEquals("pollDetails not of pollDetails class", dets.getClass(), PollDetails.class);
    assertEquals("", poll.getNodeId(), dets.getPollId());
    assertEquals("", poll.getQuestion(), dets.getQuestion());
    assertEquals("", poll.getStart(), dets.getStart());
    assertEquals("", poll.getDuration(), dets.getDuration());
    assertEquals("", poll.getInstitution$().getNodeId(), dets.getOwnerId());
    assertEquals("", poll.getCreator$().getNodeId(), dets.getCreatorId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#fromPollDetails(com.eulersbridge.iEngage.core.events.polls.PollDetails)}.
   */
  @Test
  public final void testFromPollDetails() {
    Poll pollTest = Poll.fromPollDetails(dets);
    pollTest.setInstitution(new Institution(dets.getOwnerId()));
    pollTest.setCreator(new User(dets.getCreatorId()));
    assertEquals("pollTest not of Poll class", pollTest.getClass(), Poll.class);
    assertEquals("", dets.getNodeId(), pollTest.getNodeId());
    assertEquals("", dets.getQuestion(), pollTest.getQuestion());
    assertEquals("", dets.getStart(), pollTest.getStart());
    assertEquals("", dets.getDuration(), pollTest.getDuration());
    assertEquals("", dets.getOwnerId(), pollTest.getInstitution$().getNodeId());
    assertEquals("", dets.getCreatorId(), pollTest.getCreator$().getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#getNodeId()}.
   */
  @Test
  public final void testGetNodeId() {
    assertEquals(poll.getNodeId(), dets.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#setNodeId(java.lang.Long)}.
   */
  @Test
  public final void testSetNodeId() {
    Long nodeId = 4325l;
    assertNotEquals(poll.getNodeId(), nodeId);
    poll.setNodeId(nodeId);
    assertEquals(poll.getNodeId(), nodeId);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#getQuestion()}.
   */
  @Test
  public final void testGetQuestion() {
    assertEquals(poll.getQuestion(), dets.getQuestion());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#setQuestion(java.lang.String)}.
   */
  @Test
  public final void testSetQuestion() {
    String question = "Other Question";
    assertNotEquals(poll.getQuestion(), question);
    poll.setQuestion(question);
    assertEquals(poll.getQuestion(), question);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#getStart()}.
   */
  @Test
  public final void testGetStart() {
    assertEquals(poll.getStart(), dets.getStart());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#setStart(java.lang.Long)}.
   */
  @Test
  public final void testSetStart() {
    Long start = 4325l;
    assertNotEquals(poll.getStart(), start);
    poll.setStart(start);
    assertEquals(poll.getStart(), start);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#getDuration()}.
   */
  @Test
  public final void testGetDuration() {
    assertEquals(poll.getDuration(), dets.getDuration());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#setDuration(java.lang.Long)}.
   */
  @Test
  public final void testSetDuration() {
    Long duration = 4325l;
    assertNotEquals(poll.getDuration(), duration);
    poll.setDuration(duration);
    assertEquals(poll.getDuration(), duration);
  }

  /**
   * Test method for {@link Poll#getCreator$()}.
   */
  @Test
  public final void testGetCreator() {
    assertEquals(poll.getCreator$().getNodeId(), dets.getCreatorId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#(com.eulersbridge.iEngage.database.domain)}.
   */
  @Test
  public final void testSetCreator() {
    Node creator = new Node(4325l);
    assertNotEquals(poll.getCreator$(), creator);
    poll.setCreator(new User(creator.getNodeId()));
    assertEquals(poll.getCreator$(), new User(creator.getNodeId()));
  }

  /**
   * Test method for {@link Poll#getInstitution$()}.
   */
  @Test
  public final void testGetOwner() {
    assertEquals(poll.getInstitution$().getNodeId(), dets.getOwnerId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#(com.eulersbridge.iEngage.database.domain)}.
   */
  @Test
  public final void testSetOwner() {
    Institution owner = new Institution(43254l);
    assertNotEquals(poll.getInstitution$(), owner);
    poll.setInstitution(owner);
    assertEquals(poll.getInstitution$(), owner);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#toString()}.
   */
  @Test
  public final void testToString() {
    assertNotNull("Not yet implemented", poll.toString());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Poll#hashCode()}.
   */
  @Test
  public final void testHashCode() {
    Poll pollTest = DatabaseDataFixture.populatePoll1();
    assertEquals(pollTest.hashCode(), pollTest.hashCode());
    pollTest.setNodeId(null);
    assertNotEquals(poll.hashCode(), pollTest.hashCode());
    assertNotEquals(pollTest.hashCode(), poll.hashCode());
    poll.setNodeId(null);
    pollTest.setStart(null);
    assertNotEquals(poll.hashCode(), pollTest.hashCode());
    assertNotEquals(pollTest.hashCode(), poll.hashCode());
    pollTest.setStart(poll.getStart());
    pollTest.setDuration(null);
    assertNotEquals(poll.hashCode(), pollTest.hashCode());
    assertNotEquals(pollTest.hashCode(), poll.hashCode());
    pollTest.setDuration(poll.getDuration());
    pollTest.setQuestion(null);
    assertNotEquals(poll.hashCode(), pollTest.hashCode());
    assertNotEquals(pollTest.hashCode(), poll.hashCode());
    pollTest.setQuestion(poll.getQuestion());
//    assertNotEquals(poll.hashCode(), pollTest.hashCode());
//    assertNotEquals(pollTest.hashCode(), poll.hashCode());
    pollTest.setCreator(null);
    assertNotEquals(poll.hashCode(), pollTest.hashCode());
    assertNotEquals(pollTest.hashCode(), poll.hashCode());
    pollTest.setCreator(poll.getCreator$());
    pollTest.setInstitution(null);
    assertNotEquals(poll.hashCode(), pollTest.hashCode());
    assertNotEquals(pollTest.hashCode(), poll.hashCode());
    pollTest.setInstitution(poll.getInstitution$());
  }

}
