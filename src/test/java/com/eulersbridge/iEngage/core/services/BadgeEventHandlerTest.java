///**
// *
// */
//package com.eulersbridge.iEngage.core.services;
//
//import com.eulersbridge.iEngage.core.events.*;
//import com.eulersbridge.iEngage.core.events.badge.*;
//import com.eulersbridge.iEngage.database.domain.Badge;
//import com.eulersbridge.iEngage.database.domain.BadgeComplete;
//import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
//import com.eulersbridge.iEngage.database.domain.User;
//import com.eulersbridge.iEngage.database.repository.BadgeRepository;
//import com.eulersbridge.iEngage.database.repository.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
///**
// * @author Greg Newitt
// *
// */
//public class BadgeEventHandlerTest
//{
//    private static Logger LOG = LoggerFactory.getLogger(BadgeEventHandlerTest.class);
//
//    @Mock
//	BadgeRepository badgeRepository;
//    @Mock
//	UserRepository userRepository;
//
//    BadgeEventHandler service;
//
//
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception
//	{
//		MockitoAnnotations.initMocks(this);
//
//		service=new BadgeEventHandler(badgeRepository,userRepository);
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#BadgeEventHandler(com.eulersbridge.iEngage.database.repository.BadgeRepository)}.
//	 */
//	@Test
//	public final void testBadgeEventHandler()
//	{
//		assertNotNull("Not yet implemented",service);
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#createBadge(com.eulersbridge.iEngage.core.events.badge.CreateBadgeEvent)}.
//	 */
//	@Test
//	public final void testCreateBadge()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("CreatingBadge()");
//		Badge testData=DatabaseDataFixture.populateBadge1();
//		when(badgeRepository.save(any(Badge.class))).thenReturn(testData);
//		BadgeDetails dets=testData.toBadgeDetails();
//		CreateBadgeEvent createPositionEvent=new CreateBadgeEvent(dets);
//		CreatedEvent evtData = service.createBadge(createPositionEvent);
//		BadgeDetails returnedDets = (BadgeDetails)evtData.getDetails();
//		assertEquals(returnedDets,testData.toBadgeDetails());
//		assertNotNull(evtData.getNodeId());
//		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#createBadge(com.eulersbridge.iEngage.core.events.badge.CreateBadgeEvent)}.
//	 */
//	@Test
//	public final void testCreateBadgeFailed()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("CreatingBadge()");
//		Badge testData=DatabaseDataFixture.populateBadge1();
//		when(badgeRepository.save(any(Badge.class))).thenReturn(null);
//		BadgeDetails dets=testData.toBadgeDetails();
//		CreateBadgeEvent createPositionEvent=new CreateBadgeEvent(dets);
//		CreatedEvent evtData = service.createBadge(createPositionEvent);
//		BadgeDetails returnedDets = (BadgeDetails)evtData.getDetails();
//		assertEquals(returnedDets,testData.toBadgeDetails());
//		assertTrue(evtData.isFailed());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#requestReadBadge(com.eulersbridge.iEngage.core.events.badge.RequestReadBadgeEvent)}.
//	 */
//	@Test
//	public final void testRequestReadBadge()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("ReadingBadge()");
//		Badge testData=DatabaseDataFixture.populateBadge1();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(testData);
//		RequestReadBadgeEvent requestReadBadgeEvent=new RequestReadBadgeEvent(testData.getNodeId());
//		ReadBadgeEvent evtData = (ReadBadgeEvent) service.requestReadBadge(requestReadBadgeEvent);
//		BadgeDetails returnedDets = (BadgeDetails)evtData.getDetails();
//		assertEquals(returnedDets,testData.toBadgeDetails());
//		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
//		assertTrue(evtData.isEntityFound());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#requestReadBadge(com.eulersbridge.iEngage.core.events.badge.RequestReadBadgeEvent)}.
//	 */
//	@Test
//	public final void testReadBadgeNotFound()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("ReadingBadge()");
//		Badge testData=null;
//		Long nodeId=23l;
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(testData);
//		RequestReadBadgeEvent requestReadBadgeEvent=new RequestReadBadgeEvent(nodeId);
//		ReadEvent evtData = service.requestReadBadge(requestReadBadgeEvent);
//		BadgeDetails returnedDets = (BadgeDetails)evtData.getDetails();
//		assertNull(returnedDets);
//		assertEquals(evtData.getNodeId(),nodeId);
//		assertFalse(evtData.isEntityFound());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeBadgeHandler#readBadges(com.eulersbridge.iEngage.core.events.events.ReadAllBadge,Direction,int,int)}.
//	 */
//	@Test
//	public final void testReadBadges()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("ReadingBadges()");
//		HashMap<Long, Badge> events = DatabaseDataFixture.populateBadges();
//		ArrayList<Badge> evts=new ArrayList<Badge>();
//		Iterator<Badge> iter=events.values().iterator();
//		while (iter.hasNext())
//		{
//			Badge na=iter.next();
//			evts.add(na);
//		}
//
//
//		Long institutionId=1l;
//		ReadAllEvent evt=new ReadAllEvent(institutionId);
//		int pageLength=10;
//		int pageNumber=0;
//
//		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
//		Page<Badge> testData=new PageImpl<Badge>(evts,pageable,evts.size());
//		when(badgeRepository.findAll(any(Pageable.class))).thenReturn(testData);
//
//		AllReadEvent evtData = service.readBadges(evt, Direction.ASC, pageNumber, pageLength);
//		assertNotNull(evtData);
//		assertEquals(evtData.getTotalPages(),new Integer(1));
//		assertEquals(evtData.getTotalItems(),new Long(evts.size()));
//	}
//
//	@Test
//	public final void testReadBadgesNoneAvailable()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("ReadingBadges()");
//		ArrayList<Badge> evts=new ArrayList<Badge>();
//
//		Long institutionId=1l;
//		ReadAllEvent evt=new ReadAllEvent(institutionId);
//		int pageLength=10;
//		int pageNumber=0;
//
//		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
//		Page<Badge> testData=new PageImpl<Badge>(evts,pageable,evts.size());
//		when(badgeRepository.findAll(any(Pageable.class))).thenReturn(testData);
//
//		AllReadEvent evtData = service.readBadges(evt, Direction.ASC, pageNumber, pageLength);
//		assertNotNull(evtData);
//		assertEquals(evtData.getTotalPages().intValue(),0);
//		assertEquals(evtData.getTotalItems().longValue(),0);
//	}
//
//	@Test
//	public final void testReadBadgesNullReturned()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("ReadingBadges()");
//
//		Long institutionId=1l;
//		ReadAllEvent evt=new ReadAllEvent(institutionId);
//
//		Page<Badge> testData=null;
//		when(badgeRepository.findAll(any(Pageable.class))).thenReturn(testData);
//
//		int pageLength=10;
//		int pageNumber=0;
//		AllReadEvent evtData = service.readBadges(evt, Direction.ASC, pageNumber, pageLength);
//		assertNotNull(evtData);
//		assertFalse(((AllReadEvent)evtData).isEntityFound());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#updateBadge(com.eulersbridge.iEngage.core.events.badge.UpdateBadgeEvent)}.
//	 */
//	@Test
//	public final void testUpdateBadge()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("UpdatingBadge()");
//		Badge testData=DatabaseDataFixture.populateBadge1();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(testData);
//		when(badgeRepository.save(any(Badge.class))).thenReturn(testData);
//		BadgeDetails dets=testData.toBadgeDetails();
//		UpdateBadgeEvent createElectionEvent=new UpdateBadgeEvent(dets.getNodeId(), dets);
//		UpdatedEvent evtData = service.updateBadge(createElectionEvent);
//		BadgeDetails returnedDets = (BadgeDetails) evtData.getDetails();
//		assertEquals(returnedDets,testData.toBadgeDetails());
//		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
//		assertTrue(evtData.isEntityFound());
//		assertNotNull(evtData.getNodeId());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#updateBadge(com.eulersbridge.iEngage.core.events.badges.UpdateBadgeEvent)}.
//	 */
//	@Test
//	public final void testUpdateBadgeNotFound()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("UpdatingBadge()");
//		Badge testData=DatabaseDataFixture.populateBadge1();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(null);
//		when(badgeRepository.save(any(Badge.class))).thenReturn(testData);
//		BadgeDetails dets=testData.toBadgeDetails();
//		UpdateBadgeEvent createBadgeEvent=new UpdateBadgeEvent(dets.getNodeId(), dets);
//		UpdatedEvent evtData = service.updateBadge(createBadgeEvent);
//		assertNull(evtData.getDetails());
//		assertEquals(evtData.getNodeId(),testData.getNodeId());
//		assertFalse(evtData.isEntityFound());
//		assertNotNull(evtData.getNodeId());
//	}
//
//	@Test
//	public final void testCompletedBadge()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("CompletingBadge()");
//		BadgeComplete testData=DatabaseDataFixture.populateBadgeComplete1();
//		Badge testBadge=testData.getBadge();
//		User testUser=testData.getUser();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(testBadge);
//		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
//		when(badgeRepository.badgeCompleted(any(Long.class),any(Long.class))).thenReturn(testData);
//		BadgeCompleteDetails dets=testData.toBadgeCompleteDetails();
//		UpdateEvent completeBadgeEvent=new UpdateEvent(null,dets);
//		UpdatedEvent evtData = service.completedBadge(completeBadgeEvent);
//		BadgeCompleteDetails returnedDets = (BadgeCompleteDetails) evtData.getDetails();
//		assertEquals(returnedDets,testData.toBadgeCompleteDetails());
//		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
//		assertTrue(evtData.isEntityFound());
//		assertNotNull(evtData.getNodeId());
//	}
//
//	@Test
//	public final void testCompletedBadgeNullUser()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("CompletingBadge()");
//		User testUser=null;
//		BadgeComplete testData=DatabaseDataFixture.populateBadgeComplete1();
//		testData.setUser(testUser);
//		Badge testBadge=testData.getBadge();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(testBadge);
//		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
//		when(badgeRepository.badgeCompleted(any(Long.class),any(Long.class))).thenReturn(testData);
//		BadgeCompleteDetails dets=testData.toBadgeCompleteDetails();
//		UpdateEvent completeBadgeEvent=new UpdateEvent(null,dets);
//		UpdatedEvent evtData = service.completedBadge(completeBadgeEvent);
//		BadgeCompleteDetails returnedDets = (BadgeCompleteDetails) evtData.getDetails();
//		assertNull(returnedDets);
//		assertFalse(evtData.isEntityFound());
//		assertNull(evtData.getNodeId());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeBadgeHandler#readBadges(com.eulersbridge.iEngage.core.events.events.ReadAllBadge,Direction,int,int)}.
//	 */
//	@Test
//	public final void testFindCompletedBadges()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("FindingCompletedBadges()");
//		HashMap<Long, Badge> events = DatabaseDataFixture.populateBadges();
//		ArrayList<Badge> evts=new ArrayList<Badge>();
//		Iterator<Badge> iter=events.values().iterator();
//		while (iter.hasNext())
//		{
//			Badge na=iter.next();
//			evts.add(na);
//		}
//
//
//		Long userId=1l;
//		ReadAllEvent evt=new ReadAllEvent(userId);
//		int pageLength=10;
//		int pageNumber=0;
//
//		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
//		Page<Badge> testData=new PageImpl<Badge>(evts,pageable,evts.size());
//		when(badgeRepository.findCompletedBadges(any(Long.class),any(Pageable.class))).thenReturn(testData);
//
//		AllReadEvent evtData = service.readCompletedBadges(evt, Direction.ASC, pageNumber, pageLength);
//		assertNotNull(evtData);
//		assertEquals(evtData.getTotalPages(),new Integer(1));
//		assertEquals(evtData.getTotalItems(),new Long(evts.size()));
//	}
//
//	@Test
//	public final void testFindCompletedBadgesNoneAvailable()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("FindingCompletedBadges()");
//		ArrayList<Badge> evts=new ArrayList<Badge>();
//
//		Long userId=1l;
//		ReadAllEvent evt=new ReadAllEvent(userId);
//		int pageLength=10;
//		int pageNumber=0;
//
//		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
//		Page<Badge> testData=new PageImpl<Badge>(evts,pageable,evts.size());
//		when(badgeRepository.findCompletedBadges(any(Long.class),any(Pageable.class))).thenReturn(testData);
//		User testUser=DatabaseDataFixture.populateUserGnewitt();
//		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
//
//		AllReadEvent evtData = service.readCompletedBadges(evt, Direction.ASC, pageNumber, pageLength);
//		assertNotNull(evtData);
//		assertEquals(evtData.getTotalPages().intValue(),0);
//		assertEquals(evtData.getTotalItems().longValue(),0);
//	}
//
//	@Test
//	public final void testFindCompletedBadgesNullReturned()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("FindingCompletedBadges()");
//
//		Long userId=1l;
//		ReadAllEvent evt=new ReadAllEvent(userId);
//
//		Page<Badge> testData=null;
//		when(badgeRepository.findAll(any(Pageable.class))).thenReturn(testData);
//
//		int pageLength=10;
//		int pageNumber=0;
//		AllReadEvent evtData = service.readCompletedBadges(evt, Direction.ASC, pageNumber, pageLength);
//		assertNotNull(evtData);
//		assertFalse(((AllReadEvent)evtData).isEntityFound());
//	}
//
//	@Test
//	public final void testCompletedBadgeInvalidUser()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("CompletingBadge()");
//		BadgeComplete testData=DatabaseDataFixture.populateBadgeComplete1();
//		Badge testBadge=testData.getBadge();
//		User testUser=testData.getUser();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(testBadge);
//		when(userRepository.findOne(any(Long.class))).thenReturn(null);
//		when(badgeRepository.badgeCompleted(any(Long.class),any(Long.class))).thenReturn(testData);
//		BadgeCompleteDetails dets=testData.toBadgeCompleteDetails();
//		UpdateEvent completeBadgeEvent=new UpdateEvent(null,dets);
//		UpdatedEvent evtData = service.completedBadge(completeBadgeEvent);
//		BadgeCompleteDetails returnedDets = (BadgeCompleteDetails) evtData.getDetails();
//		assertNull(returnedDets);
//		assertFalse(evtData.isEntityFound());
//		assertEquals(evtData.getNodeId(),testUser.getNodeId());
//	}
//
//	@Test
//	public final void testCompletedBadgeNullBadge()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("CompletingBadge()");
//		Badge testBadge=null;
//		BadgeComplete testData=DatabaseDataFixture.populateBadgeComplete1();
//		testData.setBadge(testBadge);
//		User testUser=testData.getUser();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(testBadge);
//		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
//		when(badgeRepository.badgeCompleted(any(Long.class),any(Long.class))).thenReturn(testData);
//		BadgeCompleteDetails dets=testData.toBadgeCompleteDetails();
//		UpdateEvent completeBadgeEvent=new UpdateEvent(null,dets);
//		UpdatedEvent evtData = service.completedBadge(completeBadgeEvent);
//		BadgeCompleteDetails returnedDets = (BadgeCompleteDetails) evtData.getDetails();
//		assertNull(returnedDets);
//		assertFalse(evtData.isEntityFound());
//		assertNull(evtData.getNodeId());
//	}
//
//	@Test
//	public final void testCompletedBadgeInvalidBadge()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("CompletingBadge()");
//		BadgeComplete testData=DatabaseDataFixture.populateBadgeComplete1();
//		Badge testBadge=testData.getBadge();
//		User testUser=testData.getUser();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(null);
//		when(userRepository.findOne(any(Long.class))).thenReturn(testUser);
//		when(badgeRepository.badgeCompleted(any(Long.class),any(Long.class))).thenReturn(testData);
//		BadgeCompleteDetails dets=testData.toBadgeCompleteDetails();
//		UpdateEvent completeBadgeEvent=new UpdateEvent(null,dets);
//		UpdatedEvent evtData = service.completedBadge(completeBadgeEvent);
//		BadgeCompleteDetails returnedDets = (BadgeCompleteDetails) evtData.getDetails();
//		assertNull(returnedDets);
//		assertFalse(evtData.isEntityFound());
//		assertEquals(evtData.getNodeId(),testBadge.getNodeId());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#deleteBadge(com.eulersbridge.iEngage.core.events.badge.DeleteBadgeEvent)}.
//	 */
//	@Test
//	public final void testDeleteBadge()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("DeletingBadge()");
//		Badge testData=DatabaseDataFixture.populateBadge1();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(testData);
//		doNothing().when(badgeRepository).delete((any(Long.class)));
//		DeleteBadgeEvent deleteBadgeEvent=new DeleteBadgeEvent(testData.getNodeId());
//		DeletedEvent evtData = service.deleteBadge(deleteBadgeEvent);
//		assertTrue(evtData.isEntityFound());
//		assertTrue(evtData.isDeletionCompleted());
//		assertEquals(testData.getNodeId(),evtData.getNodeId());
//	}
//
//	/**
//	 * Test method for {@link com.eulersbridge.iEngage.core.services.BadgeEventHandler#deleteBadge(com.eulersbridge.iEngage.core.events.badges.DeleteBadgeEvent)}.
//	 */
//	@Test
//	public final void testDeleteBadgeNotFound()
//	{
//		if (LOG.isDebugEnabled()) LOG.debug("DeletingBadge()");
//		Badge testData=DatabaseDataFixture.populateBadge1();
//		when(badgeRepository.findOne(any(Long.class))).thenReturn(null);
//		doNothing().when(badgeRepository).delete((any(Long.class)));
//		DeleteBadgeEvent deleteBadgeEvent=new DeleteBadgeEvent(testData.getNodeId());
//		DeletedEvent evtData = service.deleteBadge(deleteBadgeEvent);
//		assertFalse(evtData.isEntityFound());
//		assertFalse(evtData.isDeletionCompleted());
//		assertEquals(testData.getNodeId(),evtData.getNodeId());
//	}
//}
