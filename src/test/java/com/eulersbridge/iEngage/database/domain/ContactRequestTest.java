/**
 *
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */
public class ContactRequestTest {
  private ContactRequest contactRequest;
  private ContactRequestDetails dets;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    contactRequest = DatabaseDataFixture.populateContactRequest1();
    dets = contactRequest.toContactRequestDetails();
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#ContactRequest()}.
   */
  @Test
  public final void testContactRequest() {
    ContactRequest contactRequestTest = new ContactRequest();
    assertEquals("contactRequestTest not of ContactRequest class", contactRequestTest.getClass(), ContactRequest.class);
  }

  private void checkHashCode(ContactRequest test1, ContactRequest test2) {
    assertNotEquals(test1.hashCode(), test2.hashCode());
    assertNotEquals(test2.hashCode(), test1.hashCode());
  }

  private void checkNotEquals(ContactRequest test1, ContactRequest test2) {
    assertNotEquals(test1, test2);
    assertNotEquals(test2, test1);
  }


  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#toContactRequestDetails()}.
   */
  @Test
  public final void testToContactRequestDetails() {
    ContactRequestDetails dets = contactRequest.toContactRequestDetails();
    assertEquals("electionDetails not of ElectionDetails class", dets.getClass(), ContactRequestDetails.class);
    assertEquals("", contactRequest.getNodeId(), dets.getNodeId());
    assertEquals("", contactRequest.getAccepted(), dets.getAccepted());
    assertEquals("", contactRequest.getContactDetails(), dets.getContactDetails());
    assertEquals("", contactRequest.getRejected(), dets.getRejected());
    assertEquals("", contactRequest.getRequestDate(), dets.getRequestDate());
    assertEquals("", contactRequest.getResponseDate(), dets.getResponseDate());
    assertEquals("", contactRequest.getUser$().getNodeId(), dets.getUserId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#fromContactRequestDetails(com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails)}.
   */
  @Test
  public final void testFromContactRequestDetails() {
    ContactRequest contactRequestTest = ContactRequest.fromContactRequestDetails(dets);
    User user = new User(dets.getUserId());
    contactRequestTest.setUser(user);
    assertEquals("electionTest not of Election class", contactRequestTest.getClass(), ContactRequest.class);
    assertEquals("", dets.getNodeId(), contactRequestTest.getNodeId());
    assertEquals("", dets.getAccepted(), contactRequestTest.getAccepted());
    assertEquals("", dets.getContactDetails(), contactRequestTest.getContactDetails());
    assertEquals("", dets.getRejected(), contactRequestTest.getRejected());
    assertEquals("", dets.getRequestDate(), contactRequestTest.getRequestDate());
    assertEquals("", dets.getResponseDate(), contactRequestTest.getResponseDate());
    assertEquals("", dets.getUserId(), contactRequestTest.getUser$().getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#getNodeId()}.
   */
  @Test
  public final void testGetNodeId() {
    assertEquals("", contactRequest.getNodeId(), dets.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#setNodeId(java.lang.Long)}.
   */
  @Test
  public final void testSetNodeId() {
    Long id = 535l;
    assertNotEquals("", contactRequest.getNodeId(), id);
    contactRequest.setNodeId(id);
    assertEquals("", id, contactRequest.getNodeId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#getContactDetails()}.
   */
  @Test
  public final void testGetContactDetails() {
    assertEquals("", contactRequest.getContactDetails(), dets.getContactDetails());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#setContactDetails(java.lang.String)}.
   */
  @Test
  public final void testSetContactDetails() {
    String cDets = "some contact details";
    assertNotEquals("", contactRequest.getContactDetails(), cDets);
    contactRequest.setContactDetails(cDets);
    assertEquals("", cDets, contactRequest.getContactDetails());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#getRequestDate()}.
   */
  @Test
  public final void testGetRequestDate() {
    assertEquals("", contactRequest.getRequestDate(), dets.getRequestDate());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#setRequestDate(java.lang.Long)}.
   */
  @Test
  public final void testSetRequestDate() {
    Long reqDate = 53535353l;
    assertNotEquals("", contactRequest.getRequestDate(), reqDate);
    contactRequest.setRequestDate(reqDate);
    assertEquals("", reqDate, contactRequest.getRequestDate());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#getResponseDate()}.
   */
  @Test
  public final void testGetResponseDate() {
    assertEquals("", contactRequest.getResponseDate(), dets.getResponseDate());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#setResponseDate(java.lang.Long)}.
   */
  @Test
  public final void testSetResponseDate() {
    Long respDate = 53545454l;
    assertNotEquals("", contactRequest.getResponseDate(), respDate);
    contactRequest.setResponseDate(respDate);
    assertEquals("", respDate, contactRequest.getResponseDate());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#getAccepted()}.
   */
  @Test
  public final void testGetAccepted() {
    assertEquals("", contactRequest.getAccepted(), dets.getAccepted());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#setAccepted(java.lang.Boolean)}.
   */
  @Test
  public final void testSetAccepted() {
    Boolean accepted = true;
    assertNotEquals("", contactRequest.getAccepted(), accepted);
    contactRequest.setAccepted(accepted);
    assertEquals("", accepted, contactRequest.getAccepted());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#getRejected()}.
   */
  @Test
  public final void testGetRejected() {
    assertEquals("", contactRequest.getRejected(), dets.getRejected());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#setRejected(java.lang.Boolean)}.
   */
  @Test
  public final void testSetRejected() {
    Boolean rejected = true;
    assertNotEquals("", contactRequest.getRejected(), rejected);
    contactRequest.setRejected(rejected);
    assertEquals("", rejected, contactRequest.getRejected());
  }

  /**
   * Test method for {@link ContactRequest#getUser$()}.
   */
  @Test
  public final void testGetUser() {
    assertEquals("", contactRequest.getUser$().getNodeId(), dets.getUserId());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#setUser(com.eulersbridge.iEngage.database.domain.User)}.
   */
  @Test
  public final void testSetUser() {
    User user = new User(234234l);
    assertNotEquals("", contactRequest.getUser$(), user);
    contactRequest.setUser(user);
    assertEquals("", user, contactRequest.getUser$());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#toString()}.
   */
  @Test
  public final void testToString() {
    assertNotNull("Not yet implemented", contactRequest.toString());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#hashCode()}.
   */
  @Test
  public final void testHashCode() {
    ContactRequest contactRequestTest = DatabaseDataFixture.populateContactRequest1();
    assertEquals(contactRequestTest.hashCode(), contactRequestTest.hashCode());
    assertEquals(contactRequestTest.hashCode(), contactRequest.hashCode());
    contactRequestTest.setNodeId(null);
    checkHashCode(contactRequest, contactRequestTest);
    contactRequest.setNodeId(null);

    contactRequestTest.setContactDetails(null);
    checkHashCode(contactRequest, contactRequestTest);
    contactRequestTest.setContactDetails(contactRequest.getContactDetails());

    contactRequestTest.setUser(null);
    checkHashCode(contactRequest, contactRequestTest);
    contactRequestTest.setUser(contactRequest.getUser$());

    contactRequestTest.setRequestDate(null);
    checkHashCode(contactRequest, contactRequestTest);
    contactRequestTest.setRequestDate(contactRequest.getRequestDate());

    contactRequestTest.setResponseDate(null);
    checkHashCode(contactRequest, contactRequestTest);
    contactRequestTest.setResponseDate(contactRequest.getResponseDate());

    contactRequestTest.setAccepted(null);
    checkHashCode(contactRequest, contactRequestTest);
    contactRequestTest.setAccepted(contactRequest.getAccepted());

    contactRequestTest.setRejected(null);
    checkHashCode(contactRequest, contactRequestTest);
    contactRequestTest.setRejected(contactRequest.getRejected());
  }

  /**
   * Test method for {@link com.eulersbridge.iEngage.database.domain.ContactRequest#equals(java.lang.Object)}.
   */
  @Test
  public final void testEqualsObject() {
    ContactRequest contactRequestTest = null;
    assertNotEquals(contactRequestTest, contactRequest);
    assertNotEquals(contactRequest, contactRequestTest);
    String notElection = "";
    assertNotEquals(contactRequest, notElection);
    contactRequestTest = DatabaseDataFixture.populateContactRequest1();
    contactRequestTest.setRequestDate(contactRequest.getRequestDate());
    contactRequestTest.setResponseDate(contactRequest.getResponseDate());
    assertEquals(contactRequestTest, contactRequestTest);
    assertEquals(contactRequestTest, contactRequest);

    contactRequestTest.setNodeId(54l);
    checkNotEquals(contactRequest, contactRequestTest);
    contactRequest.setNodeId(null);
    checkNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setNodeId(null);

    contactRequestTest.setRequestDate(contactRequest.getRequestDate());
    contactRequestTest.setResponseDate(contactRequest.getResponseDate());

    assertEquals(contactRequest, contactRequestTest);
    assertEquals(contactRequestTest, contactRequest);

    contactRequestTest.setContactDetails("Some description");
    assertNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setContactDetails(null);
    checkNotEquals(contactRequestTest, contactRequest);
    contactRequestTest.setContactDetails(contactRequest.getContactDetails());

    contactRequestTest.setRequestDate(523453l);
    assertNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setRequestDate(null);
    checkNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setRequestDate(contactRequest.getRequestDate());

    contactRequestTest.setResponseDate(523453l);
    assertNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setResponseDate(null);
    checkNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setResponseDate(contactRequest.getResponseDate());

    contactRequestTest.setUser(null);
    checkNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setUser(contactRequest.getUser$());

    contactRequestTest.setAccepted(true);
    assertNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setAccepted(null);
    checkNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setAccepted(contactRequest.getAccepted());

    contactRequestTest.setRejected(true);
    assertNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setRejected(null);
    checkNotEquals(contactRequest, contactRequestTest);
    contactRequestTest.setRejected(contactRequest.getRejected());
  }

}
