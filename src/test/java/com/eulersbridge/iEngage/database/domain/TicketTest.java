/**
 *
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */
public class TicketTest {
  private Ticket ticket;
  private TicketDetails dets;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    ticket = DatabaseDataFixture.populateTicket1();
    dets = ticket.toTicketDetails();
  }

  private boolean checkCandidateIdsEqual(Iterable<Long> first, Iterable<Long> second) {
    boolean result = false;
    if ((null == first) && (null == second)) result = true;
    else if ((first != null) && (second != null)) {
      Long firstNumber = null;
      Long secondNumber = null;
      Iterator<Long> iter1 = first.iterator();
      Iterator<Long> iter2 = second.iterator();
      while (iter1.hasNext() && iter2.hasNext()) {
        firstNumber = iter1.next();
        secondNumber = iter2.next();
        if (firstNumber != secondNumber) {
          break;
        }
      }
      if ((!iter1.hasNext()) && (!iter2.hasNext()) && (firstNumber == secondNumber)) {
        result = true;
      }
    }

    return result;
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#fromTicketDetails(com.eulersbridge.iEngage.core.events.ticket.TicketDetails)}.
   */
  @Test
  public final void testFromTicketDetails() {
    Ticket evt2 = Ticket.fromTicketDetails(dets);
    Election election = new Election();
    election.setNodeId(dets.getElectionId());
    evt2.setElection(election);
    assertEquals(dets.getName(), evt2.getName());
    assertEquals(dets.getNodeId(), evt2.getNodeId());
    assertEquals(dets.getInformation(), evt2.getInformation());
    assertEquals(dets.getElectionId(), evt2.getElection$().getNodeId());
    assertEquals(dets.getLogo(), evt2.getLogo());
// Following test unnecessary because we are not interested in setting candidates from a create.  Will be done individually. At candidate creation
// Time
//		assertTrue(checkCandidateIdsEqual(dets.getCandidateIds(),Ticket.toCandidateIds(evt2.getCandidates())));
  }

  @Test
  public final void testFromNullTicketDetails() {
    Ticket evt2 = Ticket.fromTicketDetails(null);
    assertNull(evt2);
// Following test unnecessary because we are not interested in setting candidates from a create.  Will be done individually. At candidate creation
// Time
//		assertTrue(checkCandidateIdsEqual(dets.getCandidateIds(),Ticket.toCandidateIds(evt2.getCandidates())));
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#toTicketDetails()}.
   */
  @Test
  public final void testToTicketDetails() {
    TicketDetails dets2 = ticket.toTicketDetails();
    assertEquals(dets2.getName(), ticket.getName());
    assertEquals(dets2.getNodeId(), ticket.getNodeId());
    assertEquals(dets2.getElectionId(), ticket.getElection$().getNodeId());
    assertEquals(dets2.getInformation(), ticket.getInformation());
    assertEquals(dets2.getLogo(), ticket.getLogo());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#toString()}.
   */
  @Test
  public final void testToString() {
    assertNotNull("Not yet implemented", ticket.toString());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#Ticket()}.
   */
  @Test
  public final void testTicket() {
    Ticket taskTest = new Ticket();
    assertEquals("taskTest not of Ticket class", taskTest.getClass(), Ticket.class);
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Iterable, com.eulersbridge.iEngage.database.domain.Election)}.
   */
  @Test
  public final void testTicketLongStringStringStringIterableOfCandidateElection() {
    Election election = new Election();
    election.setNodeId(dets.getElectionId());
    Ticket taskTest = new Ticket(dets.getNodeId(), dets.getName(), dets.getLogo(), dets.getInformation(), null, election, "GRN");
    assertEquals("taskTest not of Ticket class", taskTest.getClass(), Ticket.class);
    assertEquals(dets.getName(), taskTest.getName());
    assertEquals(dets.getNodeId(), taskTest.getNodeId());
    assertEquals(dets.getLogo(), taskTest.getLogo());
    assertEquals(dets.getInformation(), taskTest.getInformation());
    assertEquals(dets.getChararcterCode(), taskTest.getCode());
    assertNull(taskTest.getCandidates$());
    assertEquals(election, taskTest.getElection$());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#getNodeId()}.
   */
  @Test
  public final void testGetNodeId() {
    assertEquals(dets.getNodeId(), ticket.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#setNodeId(java.lang.Long)}.
   */
  @Test
  public final void testSetNodeId() {
    Long id = 34l;
    assertNotEquals(id, ticket.getNodeId());
    ticket.setNodeId(id);
    assertEquals(id, ticket.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#getName()}.
   */
  @Test
  public final void testGetName() {
    assertEquals(dets.getName(), ticket.getName());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#setName(java.lang.String)}.
   */
  @Test
  public final void testSetName() {
    String name = "Another name";
    assertNotEquals(name, ticket.getName());
    ticket.setName(name);
    assertEquals(name, ticket.getName());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#getLogo()}.
   */
  @Test
  public final void testGetLogo() {
    assertEquals(dets.getLogo(), ticket.getLogo());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#setLogo(java.lang.String)}.
   */
  @Test
  public final void testSetLogo() {
    String logo = "Another logo";
    assertNotEquals(logo, ticket.getLogo());
    ticket.setLogo(logo);
    assertEquals(logo, ticket.getLogo());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#getInformation()}.
   */
  @Test
  public final void testGetInformation() {
    assertEquals(dets.getInformation(), ticket.getInformation());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket#setInformation(java.lang.String)}.
   */
  @Test
  public final void testSetInformation() {
    String information = "Other information";
    assertNotEquals(information, ticket.getInformation());
    ticket.setInformation(information);
    assertEquals(information, ticket.getInformation());
  }

  /**
   * Test method for {@link Ticket#getCandidates$()}.
   */
  @Test
  public final void testGetCandidates() {
    Iterable<Candidate> candidates = DatabaseDataFixture.populateTicket1().getCandidates$();
    assertEquals(candidates, ticket.getCandidates$());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket(java.lang.Iterable)}.
   */
  @Test
  public final void testSetCandidate() {
    List<Candidate> candidates = DatabaseDataFixture.populateTicket2().getCandidates$();
    assertNotEquals(candidates, ticket.getCandidates$());
    ticket.setCandidates(Node.castList(candidates));
    assertEquals(candidates, ticket.getCandidates$());
  }

  /**
   * Test method for {@link Ticket#getElection$()}.
   */
  @Test
  public final void testGetElection() {
    Election election = DatabaseDataFixture.populateTicket1().getElection$();
    assertEquals(election, ticket.getElection$());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.Ticket(com.eulersbridge.iEngage.database.domain.Election)}.
   */
  @Test
  public final void testSetElection() {
    Election election = DatabaseDataFixture.populateTicket2().getElection$();
    ;
    assertNotEquals(election, ticket.getElection$());
    ticket.setElection(election);
    assertEquals(election, ticket.getElection$());
  }

  private void checkHashCode(Ticket test1, Ticket test2) {
    assertNotEquals(test1.hashCode(), test2.hashCode());
    assertNotEquals(test2.hashCode(), test1.hashCode());
  }

  private void checkNotEquals(Ticket test1, Ticket test2) {
    assertNotEquals(test1, test2);
    assertNotEquals(test2, test1);
  }

  /**
   * Test method for {@link java.lang.Object#hashCode()}.
   */
  @Test
  public final void testHashCode() {
    Ticket ticketTest = DatabaseDataFixture.populateTicket1();
    assertEquals(ticketTest.hashCode(), ticketTest.hashCode());
    assertEquals(ticketTest.hashCode(), ticket.hashCode());
    ticketTest.setNodeId(null);
    checkHashCode(ticket, ticketTest);
    ticket.setNodeId(null);
    ticketTest.setName(null);
    checkHashCode(ticket, ticketTest);
    ticketTest.setName(ticket.getName());
    ticketTest.setLogo(null);
    checkHashCode(ticket, ticketTest);
    ticketTest.setLogo(ticket.getLogo());
    ticketTest.setInformation(null);
    checkHashCode(ticket, ticketTest);
    ticketTest.setInformation(ticket.getInformation());
    ticketTest.setElection(null);
    checkHashCode(ticket, ticketTest);
    ticketTest.setElection(ticket.getElection$());
    ticketTest.setCandidates(null);
    ;
    checkHashCode(ticket, ticketTest);
    ticketTest.setCandidates(Node.castList(ticket.getCandidates$()));
  }

  /**
   * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
   */
  @Test
  public final void testEquals() {
    Ticket ticketTest = null;
    assertNotEquals(ticketTest, ticket);
    assertNotEquals(ticket, ticketTest);
    String notElection = "";
    assertNotEquals(ticket, notElection);
    ticketTest = DatabaseDataFixture.populateTicket1();
    assertEquals(ticketTest, ticketTest);
    assertEquals(ticketTest, ticket);
    ticketTest.setNodeId(54l);
    checkNotEquals(ticket, ticketTest);
    ticket.setNodeId(null);
    checkNotEquals(ticket, ticketTest);
    ticketTest.setNodeId(null);
    assertEquals(ticket, ticketTest);
    assertEquals(ticketTest, ticket);

    ticketTest.setName("Some other name.");
    assertNotEquals(ticket, ticketTest);
    ticketTest.setName(null);
    checkNotEquals(ticket, ticketTest);
    ticketTest.setName(ticket.getName());

    ticketTest.setInformation("Some information");
    assertNotEquals(ticket, ticketTest);
    ticketTest.setInformation(null);
    checkNotEquals(ticketTest, ticket);
    ticketTest.setInformation(ticket.getInformation());

    ticketTest.setLogo("some logo");
    assertNotEquals(ticket, ticketTest);
    ticketTest.setLogo(null);
    checkNotEquals(ticket, ticketTest);
    ticketTest.setLogo(ticket.getLogo());

    ticketTest.setElection(DatabaseDataFixture.populateTicket2().getElection$());
    assertNotEquals(ticket, ticketTest);
    ticketTest.setElection(null);
    checkNotEquals(ticket, ticketTest);
    ticketTest.setElection(ticket.getElection$());

    ticketTest.setCandidates(Node.castList(DatabaseDataFixture.populateTicket2().getCandidates$()));
    assertNotEquals(ticket, ticketTest);
    ticketTest.setCandidates(null);
    checkNotEquals(ticket, ticketTest);
    ticketTest.setCandidates(Node.castList(ticket.getCandidates$()));
  }
}
