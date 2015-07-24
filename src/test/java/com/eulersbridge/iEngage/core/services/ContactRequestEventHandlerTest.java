/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestReadEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import com.eulersbridge.iEngage.database.domain.Contact;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

/**
 * @author Greg Newitt
 *
 */
public class ContactRequestEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(ContactRequestEventHandlerTest.class);

    @Mock
	ContactRequestRepository contactRequestRepository;
    @Mock
	UserRepository userRepository;

    ContactRequestEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new ContactRequestEventHandler(contactRequestRepository,userRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ContactRequestEventHandler#ContactRequestEventHandler(com.eulersbridge.iEngage.database.repository.ContactRequestRepository, com.eulersbridge.iEngage.database.repository.UserRepository)}.
	 */
	@Test
	public final void testContactRequestEventHandlerContactRequestRepositoryUserRepository()
	{
		service=new ContactRequestEventHandler(contactRequestRepository,userRepository);
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ContactRequestEventHandler#createContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent)}.
	 */
	@Test
	public final void testCreateContactRequest()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		when(contactRequestRepository.save(any(ContactRequest.class))).thenReturn(testData);
		ContactRequestDetails dets=testData.toContactRequestDetails();
		CreateContactRequestEvent createContactRequestEvent=new CreateContactRequestEvent(dets);
		CreatedEvent evtData = service.createContactRequest(createContactRequestEvent);
		Details returnedDets = evtData.getDetails();
		assertEquals(testData.toContactRequestDetails(),returnedDets);
		assertEquals(testData.getNodeId(),returnedDets.getNodeId());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ContactRequestEventHandler#createContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent)}.
	 */
	@Test
	public final void testCreateContactRequestFailed()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		when(contactRequestRepository.save(any(ContactRequest.class))).thenReturn(null);
		ContactRequestDetails dets=testData.toContactRequestDetails();
		CreateContactRequestEvent createPositionEvent=new CreateContactRequestEvent(dets);
		CreatedEvent evtData = service.createContactRequest(createPositionEvent);
		ContactRequestDetails returnedDets = (ContactRequestDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toContactRequestDetails());
		assertTrue(evtData.isFailed());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ContactRequestEventHandler#createContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent)}.
	 */
	@Test
	public final void testCreateContactRequestFailedNodeIdNull()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		testData.setNodeId(null);
		when(contactRequestRepository.save(any(ContactRequest.class))).thenReturn(testData);
		ContactRequestDetails dets=testData.toContactRequestDetails();
		CreateContactRequestEvent createPositionEvent=new CreateContactRequestEvent(dets);
		CreatedEvent evtData = service.createContactRequest(createPositionEvent);
		ContactRequestDetails returnedDets = (ContactRequestDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toContactRequestDetails());
		assertTrue(evtData.isFailed());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ContactRequestEventHandler#readContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent)}.
	 */
	@Test
	public final void testReadContactRequest()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		ReadContactRequestEvent readContactRequestEvent=new ReadContactRequestEvent(testData.getNodeId());
		ContactRequestReadEvent evtData = (ContactRequestReadEvent) service.readContactRequest(readContactRequestEvent);
		ContactRequestDetails returnedDets = (ContactRequestDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toContactRequestDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}
	@Test
	public final void testReadContactRequestNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(null);
		ReadContactRequestEvent readContactRequestEvent=new ReadContactRequestEvent(testData.getNodeId());
		ReadEvent evtData = service.readContactRequest(readContactRequestEvent);
		assertNull(evtData.getDetails());
		assertEquals(evtData.getNodeId(),testData.getNodeId());
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ContactRequestEventHandler#readContactRequestByUserIdContactNumber(com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent)}.
	 */
	@Test
	public final void testReadContactRequestByUserIdContactNumber()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingContactRequestByUserIdContactNumber()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		when(contactRequestRepository.findContactRequestByUserIdContactInfo(any(Long.class),any(String.class))).thenReturn(testData);
		ReadContactRequestEvent readContactRequestEvent=new ReadContactRequestEvent(testData.toContactRequestDetails());
		ContactRequestReadEvent evtData = (ContactRequestReadEvent) service.readContactRequestByUserIdContactNumber(readContactRequestEvent);
		ContactRequestDetails returnedDets = (ContactRequestDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toContactRequestDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}

	@Test
	public final void testReadContactRequestByUserIdContactNumberContactNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingContactRequestByUserIdContactNumber()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		when(contactRequestRepository.findContactRequestByUserIdContactInfo(any(Long.class),any(String.class))).thenReturn(null);
		ReadContactRequestEvent readContactRequestEvent=new ReadContactRequestEvent(testData.toContactRequestDetails());
		ReadEvent evtData = service.readContactRequestByUserIdContactNumber(readContactRequestEvent);
		assertNull(evtData.getDetails());
		assertEquals(evtData.getNodeId(),readContactRequestEvent.getNodeId());
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ContactRequestEventHandler#acceptContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent)}.
	 */
	@Test
	public final void testAcceptContactRequestByContactNumber()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		testData.setResponseDate(null);
		ContactRequest respData=DatabaseDataFixture.populateContactRequest1();
   		respData.setAccepted(true);
   		respData.setRejected(false);
   		respData.setResponseDate(Calendar.getInstance().getTimeInMillis());

		User userData=DatabaseDataFixture.populateUserGnewitt();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		when(userRepository.findByContactNumber(any(String.class))).thenReturn(userData);
		Contact value=DatabaseDataFixture.populateContact1();
		when(userRepository.addContact(any(Long.class), any(Long.class))).thenReturn(value);
		when(contactRequestRepository.save(any(ContactRequest.class))).thenReturn(respData);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertEquals(testData.getNodeId(),uEvt.getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getNodeId(),value.getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getTimestamp(),value.getTimestamp());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getContacteeId(),value.getContactee().getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getContactorId(),value.getContactor().getNodeId());
	}
	@Test
	public final void testAcceptContactRequestByEmail()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		testData.setResponseDate(null);
		ContactRequest respData=DatabaseDataFixture.populateContactRequest2();
   		respData.setAccepted(true);
   		respData.setRejected(false);
   		respData.setResponseDate(Calendar.getInstance().getTimeInMillis());

		User userData=DatabaseDataFixture.populateUserGnewitt2();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		when(userRepository.findByEmail(any(String.class))).thenReturn(userData);
		Contact value=DatabaseDataFixture.populateContact1();
		when(userRepository.addContact(any(Long.class), any(Long.class))).thenReturn(value);
		when(contactRequestRepository.save(any(ContactRequest.class))).thenReturn(respData);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertEquals(testData.getNodeId(),uEvt.getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getNodeId(),value.getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getTimestamp(),value.getTimestamp());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getContacteeId(),value.getContactee().getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getContactorId(),value.getContactor().getNodeId());
	}

	@Test
	public final void testAcceptContactRequestByEmailAddContactResultNull()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		ContactRequest respData=DatabaseDataFixture.populateContactRequest2();
   		respData.setAccepted(true);
   		respData.setRejected(false);
   		respData.setResponseDate(Calendar.getInstance().getTimeInMillis());

		User userData=DatabaseDataFixture.populateUserGnewitt2();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		when(userRepository.findByEmail(any(String.class))).thenReturn(userData);
		when(userRepository.addContact(any(Long.class), any(Long.class))).thenReturn(null);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertNull(uEvt.getNodeId());
		assertFalse(uEvt.isEntityFound());
		assertNull(uEvt.getDetails());
	}

	@Test
	public final void testAcceptContactRequestByEmailAddContactResultNodeIdNull()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		ContactRequest respData=DatabaseDataFixture.populateContactRequest2();
   		respData.setAccepted(true);
   		respData.setRejected(false);
   		respData.setResponseDate(Calendar.getInstance().getTimeInMillis());

		User userData=DatabaseDataFixture.populateUserGnewitt2();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		when(userRepository.findByEmail(any(String.class))).thenReturn(userData);
		Contact value=DatabaseDataFixture.populateContact1();
		value.setNodeId(null);
		when(userRepository.addContact(any(Long.class), any(Long.class))).thenReturn(value);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertNull(uEvt.getNodeId());
		assertFalse(uEvt.isEntityFound());
		assertNull(uEvt.getDetails());
	}

	@Test
	public final void testAcceptContactRequestByEmailCRSaveResultNull()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		testData.setResponseDate(null);
		ContactRequest respData=DatabaseDataFixture.populateContactRequest2();
   		respData.setAccepted(true);
   		respData.setRejected(false);
   		respData.setResponseDate(Calendar.getInstance().getTimeInMillis());

		User userData=DatabaseDataFixture.populateUserGnewitt2();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		when(userRepository.findByEmail(any(String.class))).thenReturn(userData);
		Contact value=DatabaseDataFixture.populateContact1();
		when(userRepository.addContact(any(Long.class), any(Long.class))).thenReturn(value);
		when(contactRequestRepository.save(any(ContactRequest.class))).thenReturn(null);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertEquals(testData.getNodeId(),uEvt.getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getNodeId(),value.getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getTimestamp(),value.getTimestamp());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getContacteeId(),value.getContactee().getNodeId());
		assertEquals(((ContactDetails)(uEvt.getDetails())).getContactorId(),value.getContactor().getNodeId());
	}

	@Test
	public final void testAcceptContactRequestByEmailContacteeNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		testData.setResponseDate(null);
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		when(userRepository.findByEmail(any(String.class))).thenReturn(null);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		if (LOG.isDebugEnabled()) LOG.debug("Test Data -"+testData);
		if (LOG.isDebugEnabled()) LOG.debug("uEvt -"+uEvt);
		assertEquals(testData.getNodeId(),uEvt.getNodeId());
		assertFalse(uEvt.isEntityFound());
		assertNull(uEvt.getDetails());
	}
	@Test
	public final void testAcceptContactRequestByEmailContactRequestNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(null);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertEquals(testData.getNodeId(),uEvt.getNodeId());
		assertFalse(uEvt.isEntityFound());
		assertNull(uEvt.getDetails());
	}

	@Test
	public final void testAcceptContactRequestByEmailContactResponseDateAlreadySet()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		testData.setResponseDate(24l);
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertNull(uEvt.getNodeId());
		assertFalse(uEvt.isEntityFound());
		assertNull(uEvt.getDetails());
	}

	@Test
	public final void testAcceptContactRequestByEmailContactRequestAlreadyAccepted()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		testData.setResponseDate(24l);
		testData.setAccepted(true);
		testData.setRejected(false);
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertNull(uEvt.getNodeId());
		assertFalse(uEvt.isEntityFound());
		assertNull(uEvt.getDetails());
	}

	@Test
	public final void testAcceptContactRequestByEmailContactRequestAlreadyRejected()
	{
		if (LOG.isDebugEnabled()) LOG.debug("AcceptingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest2();
		testData.setResponseDate(23l);
		testData.setAccepted(false);
		testData.setRejected(true);
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(testData.getNodeId());
		UpdatedEvent uEvt=service.acceptContactRequest(acceptContactRequestEvent);
		assertNull(uEvt.getNodeId());
		assertFalse(uEvt.isEntityFound());
		assertNull(uEvt.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ContactRequestEventHandler#rejectContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.RejectContactRequestEvent)}.
	 */
	@Test
	public final void testRejectContactRequestByContactNumber()
	{
		if (LOG.isDebugEnabled()) LOG.debug("RejectingContactRequest()");
		ContactRequest testData=DatabaseDataFixture.populateContactRequest1();
		testData.setResponseDate(null);
		ContactRequest respData=DatabaseDataFixture.populateContactRequest1();
   		respData.setAccepted(false);
   		respData.setRejected(true);
   		respData.setResponseDate(Calendar.getInstance().getTimeInMillis());

		User userData=DatabaseDataFixture.populateUserGnewitt();
		when(contactRequestRepository.findOne(any(Long.class))).thenReturn(testData);
		when(userRepository.findByContactNumber(any(String.class))).thenReturn(userData);
		Contact value=DatabaseDataFixture.populateContact1();
		when(userRepository.addContact(any(Long.class), any(Long.class))).thenReturn(value);
		when(contactRequestRepository.save(any(ContactRequest.class))).thenReturn(respData);
		Details details=new Details();
		UpdateEvent rejectContactRequestEvent=new UpdateEvent(testData.getNodeId(), details);
		UpdatedEvent uEvt=service.rejectContactRequest(rejectContactRequestEvent);
		assertEquals(testData.getNodeId(),uEvt.getNodeId());
		assertEquals(((ContactRequestDetails)(uEvt.getDetails())).getNodeId(),testData.getNodeId());
		assertEquals(((ContactRequestDetails)(uEvt.getDetails())).getResponseDate(),testData.getResponseDate());
		assertTrue(((ContactRequestDetails)(uEvt.getDetails())).getRejected());
		assertEquals(((ContactRequestDetails)(uEvt.getDetails())).getRequestDate(),testData.getRequestDate());
	}

}
