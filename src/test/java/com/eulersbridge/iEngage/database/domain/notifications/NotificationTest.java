/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.junit.Before;
import org.junit.Ignore;
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
public class NotificationTest
{
	private Notification notification;
	private NotificationDetails dets;

    private static Logger LOG = LoggerFactory.getLogger(NotificationTest.class);

    @Mock
	UserRepository userRepository;

    HashMap<String,GraphRepository<?>> repos;
    
    /**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		notification=DatabaseDataFixture.populateNotification1();
		dets=notification.toNotificationDetails();
		repos=new HashMap<String,GraphRepository<?>>();
		repos.put(UserRepository.class.getSimpleName(), userRepository);
		if (LOG.isDebugEnabled()) LOG.debug("Setup");
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Notification#Notification()}.
	 */
	@Test
	public final void testNotification()
	{
		Notification notificationTest=new Notification();
		assertEquals("candidateTest not of Notification class",notificationTest.getClass(),Notification.class);
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#setupForSave(java.util.HashMap)}.
	 */
	@Test
	public final void testSetupForSaveUsingNodeId()
	{
		User testData=DatabaseDataFixture.populateUserGnewitt();
		when(userRepository.findOne(any(Long.class))).thenReturn(testData);

		Boolean response=notification.setupForSave(repos);
		assertTrue(response);
		assertEquals(notification.getHasNotificationRelationship().getUser(),testData);
	}

	@Test
	public final void testSetupForSaveUsingEmail()
	{
		User saveData=DatabaseDataFixture.populateUserGnewitt(),testData=DatabaseDataFixture.populateUserGnewitt();
		saveData.setNodeId(null);
		notification.setUser(saveData);
		when(userRepository.findByEmail(any(String.class))).thenReturn(testData);

		Boolean response=notification.setupForSave(repos);
		assertTrue(response);
		assertEquals(notification.getHasNotificationRelationship().getUser(),testData);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#toNotificationDetails()}.
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
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#fromNotificationDetails(com.eulersbridge.iEngage.core.events.notifications.NotificationDetails)}.
	 */
	@Test
	public final void testFromNotificationDetails()
	{
		Notification notificationTest=Notification.fromNotificationDetails(dets);
		assertEquals("notificationTest not of NotificationMessage class",notificationTest.getClass(),NotificationMessage.class);
		assertEquals("",dets.getNodeId(),notificationTest.getNodeId());
		assertEquals("",dets.getType(),notificationTest.getType());
		assertEquals("",dets.getRead(),notificationTest.getRead());
		assertEquals("",dets.getTimestamp(),notificationTest.getTimestamp());
		assertEquals("",dets.getUserId(),notificationTest.getUser().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals("",notification.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long id=53125l;
		assertNotEquals("",notification.getNodeId(),id);
		notification.setNodeId(id);
		assertEquals("",id,notification.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#getRead()}.
	 */
	@Test
	public final void testGetRead()
	{
		assertEquals("",notification.getRead(),dets.getRead());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#setRead(java.lang.Boolean)}.
	 */
	@Test
	@Ignore
	public final void testSetRead()
	{
		Boolean read=true;
		assertNotEquals("",notification.getRead(),read);
		notification.setRead(read);
		assertEquals("",read,notification.getRead());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#getTimestamp()}.
	 */
	@Test
	public final void testGetTimestamp()
	{
		assertEquals("",notification.getTimestamp(),dets.getTimestamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#setTimestamp(java.lang.Long)}.
	 */
	@Test
	public final void testSetTimestamp()
	{
		Long timestamp=53121323325l;
		assertNotEquals("",notification.getTimestamp(),timestamp);
		notification.setTimestamp(timestamp);
		assertEquals("",timestamp,notification.getTimestamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#getType()}.
	 */
	@Test
	public final void testGetType()
	{
		assertEquals("",notification.getType(),dets.getType());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#setType(java.lang.String)}.
	 */
	@Test
	public final void testSetType()
	{
		String type="some type";
		assertNotEquals("",notification.getType(),type);
		notification.setType(type);
		assertEquals("",type,notification.getType());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#getUser()}.
	 */
	@Test
	public final void testGetUser()
	{
		assertEquals("",notification.getUser().getNodeId(),dets.getUserId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#setUser(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testSetUser()
	{
		User user=DatabaseDataFixture.populateUserGnewitt();
		assertNotEquals("",notification.getUser(),user);
		notification.setUser(user);
		assertEquals("",user,notification.getUser());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.notifications.Notification#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",notification.toString());
	}

}
