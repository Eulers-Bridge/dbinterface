/**
 *
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */
public class PollAnswerRelationTest {
  PollAnswerRelation answer;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    answer = DatabaseDataFixture.populatePollAnswer1();
  }

  /**
   * Test method for {@link PollAnswerRelation ()}.
   */
  @Test
  public final void testPollQuestionAnswer() {
    answer = new PollAnswerRelation();
    assertNotNull("Not yet implemented", answer);
  }

  /**
   * Test method for {@link PollAnswerRelation#(com.eulersbridge.iEngage.database.domain.User, com.eulersbridge.iEngage.database.domain.Poll, java.lang.Integer)}.
   */
  @Test
  public final void testPollQuestionAnswerUserPollInteger() {
    User answerer = DatabaseDataFixture.populateUserGnewitt();
    Node owner = new Node(answerer.getNodeId());
    Poll poll = DatabaseDataFixture.populatePoll1();
    Integer answerIndex = 3;
    answer = new PollAnswerRelation(owner, poll, answerIndex);
    assertNotNull("Not yet implemented", answer);
    assertEquals(answer.getUser(), owner);
    assertEquals(answer.getPoll(), poll);
    assertEquals(answer.getAnswer(), answerIndex);
  }

  /**
   * Test method for {@link PollAnswerRelation#getNodeId()}.
   */
  @Test
  public final void testGetId() {
    PollAnswerRelation answer2 = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(answer.getNodeId(), answer2.getNodeId());
  }

  /**
   * Test method for {@link PollAnswerRelation#setNodeId(java.lang.Long)}.
   */
  @Test
  public final void testSetId() {
    PollAnswerRelation answer2 = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(answer.getNodeId(), answer2.getNodeId());
    Long id = answer.getNodeId();
    id++;
    assertNotEquals(id, answer.getNodeId());
    answer.setNodeId(id);
    assertEquals(id, answer.getNodeId());
  }

  /**
   * Test method for {@link PollAnswerRelation#getUser()}.
   */
  @Test
  public final void testGetAnswerer() {
    PollAnswerRelation answer2 = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(answer.getUser(), answer2.getUser());
  }

  /**
   * Test method for {@link PollAnswerRelation#(com.eulersbridge.iEngage.database.domain.User)}.
   */
  @Test
  public final void testSetAnswerer() {
    PollAnswerRelation answer2 = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(answer.getUser(), answer2.getUser());
    User answerer = DatabaseDataFixture.populateUserGnewitt2();
    Node owner = new Node(answerer.getNodeId());
    assertNotEquals(answerer, answer.getUser());
    answer.setUser(owner);
    assertEquals(owner, answer.getUser());
  }

  /**
   * Test method for {@link PollAnswerRelation#getPoll()}.
   */
  @Test
  public final void testGetPoll() {
    PollAnswerRelation answer2 = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(answer.getPoll(), answer2.getPoll());
  }

  /**
   * Test method for {@link PollAnswerRelation#setPoll(com.eulersbridge.iEngage.database.domain.Poll)}.
   */
  @Test
  public final void testSetPoll() {
    PollAnswerRelation answer2 = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(answer.getPoll(), answer2.getPoll());
    Poll poll = DatabaseDataFixture.populatePoll2();
    assertNotEquals(poll, answer.getPoll());
    answer.setPoll(poll);
    assertEquals(poll, answer.getPoll());
  }

  /**
   * Test method for {@link PollAnswerRelation#getTimeStamp()}.
   */
  @Test
  public final void testGetTimeStamp() {
    assertEquals(answer.getTimeStamp(), answer.getTimeStamp());
  }

  /**
   * Test method for {@link PollAnswerRelation#setTimeStamp(java.lang.Long)}.
   */
  @Test
  public final void testSetTimeStamp() {
    Long timestamp = answer.getTimeStamp();
    assertNull(answer.getTimeStamp());
    timestamp = Calendar.getInstance().getTimeInMillis();
    answer.setTimeStamp(timestamp);
    assertEquals(timestamp, answer.getTimeStamp());
  }

  /**
   * Test method for {@link PollAnswerRelation#getAnswer()}.
   */
  @Test
  public final void testGetAnswer() {
    PollAnswerRelation answer2 = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(answer.getAnswer(), answer2.getAnswer());
  }

  /**
   * Test method for {@link PollAnswerRelation#setAnswer(java.lang.Integer)}.
   */
  @Test
  public final void testSetAnswer() {
    PollAnswerRelation answer2 = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(answer.getAnswer(), answer2.getAnswer());
    Integer answerIndex = answer.getAnswer();
    answerIndex++;
    assertNotEquals(answerIndex, answer.getAnswer());
    answer.setAnswer(answerIndex);
    assertEquals(answerIndex, answer.getAnswer());
  }

  @Test
  public final void testToPollAnswerDetails() {
    PollAnswerDetails dets = answer.toPollAnswerDetails();
    assertEquals(answer.getAnswer(), dets.getAnswerIndex());
    assertEquals(answer.getUser().getNodeId(), dets.getAnswererId());
    assertEquals(answer.getPoll().getNodeId(), dets.getPollId());
    assertEquals(answer.getNodeId(), dets.getNodeId());
    assertEquals(answer.getTimeStamp(), dets.getTimeStamp());
  }

  @Test
  public final void testFromPollAnswerDetails() {
    PollAnswerRelation answer2 = PollAnswerRelation.fromPollAnswerDetails(answer.toPollAnswerDetails());
    assertEquals(answer.getAnswer(), answer2.getAnswer());
    assertEquals(answer.getUser().getNodeId(), answer2.getUser().getNodeId());
    assertEquals(answer.getPoll().getNodeId(), answer2.getPoll().getNodeId());
    assertEquals(answer.getNodeId(), answer2.getNodeId());
    assertEquals(answer.getTimeStamp(), answer2.getTimeStamp());
  }

  /**
   * Test method for {@link PollAnswerRelation#toString()}.
   */
  @Test
  public final void testToString() {
    assertNotNull("Not yet implemented", answer.toString());
  }

  private void checkHashCode(PollAnswerRelation test1, PollAnswerRelation test2) {
    assertNotEquals(test1.hashCode(), test2.hashCode());
    assertNotEquals(test2.hashCode(), test1.hashCode());
  }

  private void checkNotEquals(PollAnswerRelation test1, PollAnswerRelation test2) {
    assertNotEquals(test1, test2);
    assertNotEquals(test2, test1);
  }

  /**
   * Test method for {@link PollAnswerRelation#hashCode()}.
   */
  @Test
  public final void testHashCode() {
    PollAnswerRelation pollAnswerRelationTest = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(pollAnswerRelationTest.hashCode(), pollAnswerRelationTest.hashCode());
    assertEquals(pollAnswerRelationTest.hashCode(), answer.hashCode());
    pollAnswerRelationTest.setNodeId(null);
    checkHashCode(answer, pollAnswerRelationTest);
    answer.setNodeId(null);
    pollAnswerRelationTest.setUser(null);
    checkHashCode(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setUser(answer.getUser());
    pollAnswerRelationTest.setPoll(null);
    checkHashCode(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setPoll(answer.getPoll());
    pollAnswerRelationTest.setAnswer(null);
    checkHashCode(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setAnswer(answer.getAnswer());
    pollAnswerRelationTest.setTimeStamp(Calendar.getInstance().getTimeInMillis());
    checkHashCode(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setTimeStamp(answer.getTimeStamp());
  }

  /**
   * Test method for {@link PollAnswerRelation#equals(java.lang.Object)}.
   */
  @Test
  public final void testEqualsObject() {
    PollAnswerRelation pollAnswerRelationTest = null;
    assertNotEquals(pollAnswerRelationTest, answer);
    assertNotEquals(answer, pollAnswerRelationTest);
    String notElection = "";
    assertNotEquals(answer, notElection);
    pollAnswerRelationTest = DatabaseDataFixture.populatePollAnswer1();
    assertEquals(pollAnswerRelationTest, pollAnswerRelationTest);
    assertEquals(pollAnswerRelationTest, answer);
    pollAnswerRelationTest.setNodeId(54l);
    checkNotEquals(answer, pollAnswerRelationTest);
    answer.setNodeId(null);
    checkNotEquals(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setNodeId(null);
    pollAnswerRelationTest.setTimeStamp(answer.getTimeStamp());
    assertEquals(answer, pollAnswerRelationTest);
    assertEquals(pollAnswerRelationTest, answer);
    pollAnswerRelationTest.setTimeStamp(4321l);
    checkNotEquals(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setTimeStamp(null);
    assertEquals(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setTimeStamp(answer.getTimeStamp());

    pollAnswerRelationTest.setUser(new Node(DatabaseDataFixture.populateUserGnewitt2().getNodeId()));
    assertNotEquals(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setUser(null);
    checkNotEquals(pollAnswerRelationTest, answer);
    pollAnswerRelationTest.setUser(answer.getUser());

    pollAnswerRelationTest.setPoll(DatabaseDataFixture.populatePoll2());
    assertNotEquals(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setPoll(null);
    checkNotEquals(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setPoll(answer.getPoll());

    pollAnswerRelationTest.setAnswer(2);
    assertNotEquals(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setAnswer(null);
    checkNotEquals(answer, pollAnswerRelationTest);
    pollAnswerRelationTest.setAnswer(answer.getAnswer());
  }

}
