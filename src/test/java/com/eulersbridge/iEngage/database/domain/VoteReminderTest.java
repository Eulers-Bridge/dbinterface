/**
 *
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */
public class VoteReminderTest {
  private static Logger LOG = LoggerFactory.getLogger(VoteReminderTest.class);
  VoteReminder vr;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    vr = DatabaseDataFixture.populateVoteReminder1();
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#VoteReminder()}.
   */
  @Test
  public final void testVoteReminder() {
    if (LOG.isDebugEnabled()) LOG.debug("Test constructor,");
    VoteReminder vr1 = new VoteReminder();
    assertNotNull("Not yet implemented", vr1);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#VoteReminder(java.lang.Long, java.lang.String)}.
   */
  @Test
  public final void testVoteReminderLongString() {
    VoteReminder vr1 = new VoteReminder(34453434l, "Union Building");
    assertNotNull("Not yet implemented", vr1);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#fromVoteReminderDetails(java.lang.Long, java.lang.String)}.
   */
  @Test
  public final void testToVoteReminderDetails() {
    VoteReminderDetails vr1 = vr.toVoteReminderDetails();
    assertNotNull("Not yet implemented", vr1);
    assertEquals(vr1.getDate(), vr.getDate());
    assertEquals(vr1.getElectionId(), vr.getElection().getNodeId());
    assertEquals(vr1.getLocation(), vr.getLocation());
    assertEquals(vr1.getNodeId(), vr.getNodeId());
    assertEquals(vr1.getTimestamp(), vr.getTimestamp());
    assertEquals(vr1.getUserId(), vr.getVoter().getEmail());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#toVoteReminderDetails(java.lang.Long, java.lang.String)}.
   */
  @Test
  public final void testFromVoteReminderDetails() {
    VoteReminderDetails vr1 = vr.toVoteReminderDetails();
    VoteReminder vr2 = VoteReminder.fromVoteReminderDetails(vr1);
    assertNotNull("Not yet implemented", vr1);
    assertEquals(vr1.getDate(), vr2.getDate());
    assertEquals(vr1.getElectionId(), vr2.getElection().getNodeId());
    assertEquals(vr1.getLocation(), vr2.getLocation());
    assertEquals(vr1.getNodeId(), vr2.getNodeId());
    assertEquals(vr1.getTimestamp(), vr2.getTimestamp());
    assertEquals(vr1.getUserId(), vr2.getVoter().getEmail());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#getNodeId()}.
   */
  @Test
  public final void testGetNodeId() {
    Long id = 1l;
    VoteReminder vr1 = DatabaseDataFixture.populateVoteReminder(id, 1234l, null, "Union Building", 123l, null);
    assertEquals(id, vr1.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#setNodeId(java.lang.Long)}.
   */
  @Test
  public final void testSetNodeId() {
    Long id = 1l;
    VoteReminder vr1 = new VoteReminder();
    vr1.setNodeId(id);
    assertEquals(id, vr1.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#getDate()}.
   */
  @Test
  public final void testGetDate() {
    Long date = 1234l;
    VoteReminder vr1 = DatabaseDataFixture.populateVoteReminder(1l, date, null, "Union Building", 123l, null);
    assertEquals(date, vr1.getDate());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#setDate(java.lang.Long)}.
   */
  @Test
  public final void testSetDate() {
    Long date = 134l;
    VoteReminder vr1 = new VoteReminder();
    vr1.setDate(date);
    assertEquals(date, vr1.getDate());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#getLocation()}.
   */
  @Test
  public final void testGetLocation() {
    String location = "Union House";
    VoteReminder vr1 = DatabaseDataFixture.populateVoteReminder(1l, 123456l, null, location, 123l, null);
    assertEquals(location, vr1.getLocation());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#setLocation(java.lang.String)}.
   */
  @Test
  public final void testSetLocation() {
    String location = "Union House";
    VoteReminder vr1 = new VoteReminder();
    vr1.setLocation(location);
    assertEquals(location, vr1.getLocation());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#getVoter()}.
   */
  @Test
  public final void testGetVoter() {
    User voter = DatabaseDataFixture.populateUserGnewitt();
    VoteReminder vr1 = DatabaseDataFixture.populateVoteReminder(1l, 123456l, null, "Union House", 123l, voter);
    assertEquals(voter, vr1.getVoter());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#setVoter(com.eulersbridge.iEngage.database.domain.User)}.
   */
  @Test
  public final void testSetVoter() {
    User voter = DatabaseDataFixture.populateUserGnewitt();
    VoteReminder vr1 = new VoteReminder();
    vr1.setVoter(voter);
    assertEquals(voter, vr1.getVoter());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#getElection()}.
   */
  @Test
  public final void testGetElection() {
    Election election = DatabaseDataFixture.populateElection1();
    VoteReminder vr1 = DatabaseDataFixture.populateVoteReminder(1l, 123456l, election, "Union House", 123l, null);
    assertEquals(election, vr1.getElection());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#setElection(com.eulersbridge.iEngage.database.domain.Election)}.
   */
  @Test
  public final void testSetElection() {
    Election election = DatabaseDataFixture.populateElection1();
    VoteReminder vr1 = new VoteReminder();
    vr1.setElection(election);
    assertEquals(election, vr1.getElection());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#getTimestamp()}.
   */
  @Test
  public final void testGetTimestamp() {
    Long timestamp = 1234l;
    VoteReminder vr1 = DatabaseDataFixture.populateVoteReminder(1l, 123456l, null, "Union Building", timestamp, null);
    assertEquals(timestamp, vr1.getTimestamp());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#setTimestamp(java.lang.Long)}.
   */
  @Test
  public final void testSetTimestamp() {
    Long timestamp = 1534535l;
    VoteReminder vr1 = new VoteReminder();
    vr1.setTimestamp(timestamp);
    assertEquals(timestamp, vr1.getTimestamp());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#toString()}.
   */
  @Test
  public final void testToString() {
    assertNotNull("Not yet implemented", vr.toString());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.VoteReminder#equals(java.lang.Object)}.
   */
  @Test
  public final void testEqualsObject() {

  }

}
