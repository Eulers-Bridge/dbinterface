/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.badge.BadgeDetails;
import com.eulersbridge.iEngage.core.events.badge.CreateBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.DeleteBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.ReadBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.RequestReadBadgeEvent;
import com.eulersbridge.iEngage.core.events.badge.UpdateBadgeEvent;
import com.eulersbridge.iEngage.database.domain.Badge;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.BadgeRepository;

/**
 * @author Greg Newitt
 *
 */
public class BadgeEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(BadgeEventHandlerTest.class);

    @Mock
	BadgeRepository badgeRepository;

    BadgeEventHandler service;



	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new BadgeEventHandler(badgeRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#BadgeEventHandler(com.eulersbridge.iEngage.database.repository.BadgeRepository)}.
	 */
	@Test
	public final void testBadgeEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#createBadge(com.eulersbridge.iEngage.core.events.badge.CreateBadgeEvent)}.
	 */
	@Test
	public final void testCreateBadge()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingBadge()");
		Badge testData=DatabaseDataFixture.populateBadge1();
		when(badgeRepository.save(any(Badge.class))).thenReturn(testData);
		BadgeDetails dets=testData.toBadgeDetails();
		CreateBadgeEvent createPositionEvent=new CreateBadgeEvent(dets);
		CreatedEvent evtData = service.createBadge(createPositionEvent);
		BadgeDetails returnedDets = (BadgeDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toBadgeDetails());
		assertNotNull(evtData.getNodeId());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#createBadge(com.eulersbridge.iEngage.core.events.badge.CreateBadgeEvent)}.
	 */
	@Test
	public final void testCreateBadgeFailed()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingBadge()");
		Badge testData=DatabaseDataFixture.populateBadge1();
		when(badgeRepository.save(any(Badge.class))).thenReturn(null);
		BadgeDetails dets=testData.toBadgeDetails();
		CreateBadgeEvent createPositionEvent=new CreateBadgeEvent(dets);
		CreatedEvent evtData = service.createBadge(createPositionEvent);
		BadgeDetails returnedDets = (BadgeDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toBadgeDetails());
		assertTrue(evtData.isFailed());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#requestReadBadge(com.eulersbridge.iEngage.core.events.badge.RequestReadBadgeEvent)}.
	 */
	@Test
	public final void testRequestReadBadge()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingBadge()");
		Badge testData=DatabaseDataFixture.populateBadge1();
		when(badgeRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadBadgeEvent requestReadBadgeEvent=new RequestReadBadgeEvent(testData.getBadgeId());
		ReadBadgeEvent evtData = (ReadBadgeEvent) service.requestReadBadge(requestReadBadgeEvent);
		BadgeDetails returnedDets = (BadgeDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toBadgeDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#requestReadBadge(com.eulersbridge.iEngage.core.events.badge.RequestReadBadgeEvent)}.
	 */
	@Test
	public final void testReadBadgeNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingBadge()");
		Badge testData=null;
		Long nodeId=23l;
		when(badgeRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadBadgeEvent requestReadBadgeEvent=new RequestReadBadgeEvent(nodeId);
		ReadEvent evtData = service.requestReadBadge(requestReadBadgeEvent);
		BadgeDetails returnedDets = (BadgeDetails)evtData.getDetails();
		assertNull(returnedDets);
		assertEquals(evtData.getNodeId(),nodeId);
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#updateBadge(com.eulersbridge.iEngage.core.events.badge.UpdateBadgeEvent)}.
	 */
	@Test
	public final void testUpdateBadge()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingBadge()");
		Badge testData=DatabaseDataFixture.populateBadge1();
		when(badgeRepository.findOne(any(Long.class))).thenReturn(testData);
		when(badgeRepository.save(any(Badge.class))).thenReturn(testData);
		BadgeDetails dets=testData.toBadgeDetails();
		UpdateBadgeEvent createElectionEvent=new UpdateBadgeEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateBadge(createElectionEvent);
		BadgeDetails returnedDets = (BadgeDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toBadgeDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#updateBadge(com.eulersbridge.iEngage.core.events.badges.UpdateBadgeEvent)}.
	 */
	@Test
	public final void testUpdateBadgeNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingBadge()");
		Badge testData=DatabaseDataFixture.populateBadge1();
		when(badgeRepository.findOne(any(Long.class))).thenReturn(null);
		when(badgeRepository.save(any(Badge.class))).thenReturn(testData);
		BadgeDetails dets=testData.toBadgeDetails();
		UpdateBadgeEvent createBadgeEvent=new UpdateBadgeEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateBadge(createBadgeEvent);
		assertNull(evtData.getDetails());
		assertEquals(evtData.getNodeId(),testData.getBadgeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#deleteBadge(com.eulersbridge.iEngage.core.events.badge.DeleteBadgeEvent)}.
	 */
	@Test
	public final void testDeleteBadge()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingBadge()");
		Badge testData=DatabaseDataFixture.populateBadge1();
		when(badgeRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(badgeRepository).delete((any(Long.class)));
		DeleteBadgeEvent deleteBadgeEvent=new DeleteBadgeEvent(testData.getBadgeId());
		DeletedEvent evtData = service.deleteBadge(deleteBadgeEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getBadgeId(),evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#deleteBadge(com.eulersbridge.iEngage.core.events.badges.DeleteBadgeEvent)}.
	 */
	@Test
	public final void testDeleteBadgeNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingBadge()");
		Badge testData=DatabaseDataFixture.populateBadge1();
		when(badgeRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(badgeRepository).delete((any(Long.class)));
		DeleteBadgeEvent deleteBadgeEvent=new DeleteBadgeEvent(testData.getBadgeId());
		DeletedEvent evtData = service.deleteBadge(deleteBadgeEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getBadgeId(),evtData.getNodeId());
	}
}
