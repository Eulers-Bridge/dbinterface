/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Greg Newitt
 *
 */
public class NotificationContactRequestTest
{
	private NotificationContactRequest notification;
	private NotificationDetails dets;

    private static Logger LOG = LoggerFactory.getLogger(NotificationContactRequestTest.class);

    @Mock
	UserRepository userRepository;
    
    @Mock
    ContactRequestRepository crRepository;

    HashMap<String,GraphRepository<?>> repos;
    
    /**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		notification=DatabaseDataFixture.populateNotificationContactRequest1();
		dets=notification.toNotificationDetails();
		repos=new HashMap<String,GraphRepository<?>>();
		repos.put(UserRepository.class.getSimpleName(), userRepository);
		repos.put(ContactRequestRepository.class.getSimpleName(), crRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.NotificationContactRequest#NotificationContactRequest()}.
	 */
	@Test
	public final void testNotificationContactRequest()
	{
		NotificationContactRequest notificationTest=new NotificationContactRequest();
		assertEquals("candidateTest not of NotificationContactRequest class",notificationTest.getClass(),NotificationContactRequest.class);
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.NotificationContactRequest#setupForSave(java.util.HashMap)}.
	 */
	@Test
	public final void testSetupForSave()
	{
		User testData=DatabaseDataFixture.populateUserGnewitt();
		ContactRequest testCr=DatabaseDataFixture.populateContactRequest1();
		
		when(userRepository.findOne(any(Long.class))).thenReturn(testData);
		when(crRepository.findOne(any(long.class))).thenReturn(testCr);

		Boolean response=notification.setupForSave(repos);
		assertTrue(response);
		assertEquals(notification.getHasNotificationRelationship().getUser(),testData);
		assertEquals(notification.getContactRequest(),testCr);
	}

	@Test
	public final void testSetupForSaveNodeIdNull()
	{
		User testData=DatabaseDataFixture.populateUserGnewitt();
		ContactRequest testCr=DatabaseDataFixture.populateContactRequest1();
		
		when(userRepository.findOne(any(Long.class))).thenReturn(testData);
//		when(crRepository.findOne(any(long.class))).thenReturn(testCr);
		when(crRepository.findContactRequestByUserIdContactInfo(any(Long.class),any(String.class))).thenReturn(testCr);

		ContactRequest cr=notification.getContactRequest();
		cr.setNodeId(null);
		Boolean response=notification.setupForSave(repos);
		assertTrue(response);
		assertEquals(notification.getHasNotificationRelationship().getUser(),testData);
		assertEquals(notification.getContactRequest(),testCr);
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.NotificationContactRequest#toNotificationDetails()}.
	 */
	@Test
	public final void testToNotificationDetails()
	{
		NotificationDetails dets=notification.toNotificationDetails();
		assertEquals("dets not of NotificationDetails class",dets.getClass(),NotificationDetails.class);
		assertEquals("",notification.getNodeId(),dets.getNodeId());
		assertEquals("",notification.getType(),dets.getType());
		assertEquals("",notification.getRead(),dets.getRead());
		assertEquals("",notification.getTimestamp(),dets.getTimestamp());
		assertEquals("",notification.getUser().getNodeId(),dets.getUserId());
		assertEquals("",notification.getContactRequest().toContactRequestDetails(),dets.getNotificationBody());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.NotificationContactRequest#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",notification.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.NotificationContactRequest#fromNotificationDetails(com.eulersbridge.iEngage.core.events.notifications.NotificationDetails)}.
	 */
	@Test
	public final void testFromNotificationDetailsNotificationDetails()
	{
		NotificationContactRequest ncr=NotificationContactRequest.fromNotificationDetails(dets);
		assertEquals(ncr.getNodeId(),notification.getNodeId());
		assertEquals(ncr.getRead(),notification.getRead());
		assertEquals(ncr.getTimestamp(),notification.getTimestamp());
		assertEquals(ncr.getType(),notification.getType());
		assertEquals(ncr.getContactRequest(),notification.getContactRequest());
		assertEquals(ncr.getUser().getNodeId(),notification.getUser().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.NotificationContactRequest#getContactRequest()}.
	 */
	@Test
	public final void testGetContactRequest()
	{
		assertEquals("",notification.getContactRequest().toContactRequestDetails(),dets.getNotificationBody());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.NotificationContactRequest#setContactRequest(com.eulersbridge.iEngage.database.domain.ContactRequest)}.
	 */
	@Test
	public final void testSetContactRequest()
	{
		ContactRequest type=new ContactRequest();
		assertNotEquals("",notification.getContactRequest(),type);
		notification.setContactRequest(type);
		assertEquals("",type,notification.getContactRequest());
	}

}
