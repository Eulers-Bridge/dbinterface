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

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.CreatePollEvent;
import com.eulersbridge.iEngage.core.events.polls.DeletePollEvent;
import com.eulersbridge.iEngage.core.events.polls.PollCreatedEvent;
import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.core.events.polls.PollUpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.ReadPollEvent;
import com.eulersbridge.iEngage.core.events.polls.RequestReadPollEvent;
import com.eulersbridge.iEngage.core.events.polls.UpdatePollEvent;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.PollRepository;

/**
 * @author Greg Newitt
 *
 */
public class PollEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(PollEventHandlerTest.class);

    @Mock
	PollRepository pollRepository;
    @Mock
    OwnerRepository ownerRepository;

    PollEventHandler service;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new PollEventHandler(pollRepository,ownerRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#PollEventHandler(com.eulersbridge.iEngage.database.repository.PollRepository, com.eulersbridge.iEngage.database.repository.OwnerRepository)}.
	 */
	@Test
	public final void testPollEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#requestReadPoll(com.eulersbridge.iEngage.core.events.polls.RequestReadPollEvent)}.
	 */
	@Test
	public final void testRequestReadPoll()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadPollEvent readPollEvent=new RequestReadPollEvent(testData.getNodeId());
		ReadPollEvent evtData = (ReadPollEvent) service.requestReadPoll(readPollEvent);
		PollDetails returnedDets = (PollDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toPollDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}

/**
 * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#readPoll(com.eulersbridge.iEngage.core.events.photo.ReadPollEvent)}.
 */
@Test
public final void testReadPollNotFound()
{
	if (LOG.isDebugEnabled()) LOG.debug("ReadingPoll()");
	Poll testData=null;
	Long nodeId=1l;
	when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
	RequestReadPollEvent readPollEvent=new RequestReadPollEvent(nodeId);
	ReadEvent evtData = service.requestReadPoll(readPollEvent);
	assertNull(evtData.getDetails());
	assertEquals(nodeId,evtData.getNodeId());
	assertFalse(evtData.isEntityFound());
}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#createPoll(com.eulersbridge.iEngage.core.events.polls.CreatePollEvent)}.
	 */
@Test
public final void testCreatePoll()
{
	if (LOG.isDebugEnabled()) LOG.debug("CreatingPoll()");
	Poll testData=DatabaseDataFixture.populatePoll1();
	Owner testOwner=new Owner();
	testOwner.setNodeId(testData.getOwner().getNodeId());
	Owner testCreator=new Owner();
	testCreator.setNodeId(testData.getCreator().getNodeId());
	when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
	when(pollRepository.save(any(Poll.class))).thenReturn(testData);
	PollDetails dets=testData.toPollDetails();
	CreatePollEvent createPollEvent=new CreatePollEvent(dets);
	PollCreatedEvent evtData = service.createPoll(createPollEvent);
	Details returnedDets = evtData.getDetails();
	assertEquals(testData.toPollDetails(),returnedDets);
	assertEquals(testData.getNodeId(),returnedDets.getNodeId());
	assertNull(evtData.getFailedNodeId());
}
@Test
public final void testCreatePollOwnerNotFound()
{
	if (LOG.isDebugEnabled()) LOG.debug("CreatingPoll()");
	Poll testData=DatabaseDataFixture.populatePoll1();
	Owner testOwner=null;
	Owner testCreator=new Owner();
	testCreator.setNodeId(testData.getCreator().getNodeId());
	when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
	when(pollRepository.save(any(Poll.class))).thenReturn(testData);
	PollDetails dets=testData.toPollDetails();
	CreatePollEvent createPollEvent=new CreatePollEvent(dets);
	PollCreatedEvent evtData = service.createPoll(createPollEvent);
	assertEquals(evtData.getFailedNodeId(),testData.getOwner().getNodeId());
	assertFalse(evtData.isOwnerFound());
	assertNull(evtData.getDetails());
}
@Test
public final void testCreatePollCreatorNotFound()
{
	if (LOG.isDebugEnabled()) LOG.debug("CreatingPoll()");
	Poll testData=DatabaseDataFixture.populatePoll1();
	Owner testOwner=new Owner();
	testOwner.setNodeId(testData.getOwner().getNodeId());
	Owner testCreator=null;
	when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner).thenReturn(testCreator);
	when(pollRepository.save(any(Poll.class))).thenReturn(testData);
	PollDetails dets=testData.toPollDetails();
	CreatePollEvent createPollEvent=new CreatePollEvent(dets);
	PollCreatedEvent evtData = service.createPoll(createPollEvent);
	assertEquals(evtData.getFailedNodeId(),testData.getCreator().getNodeId());
	assertFalse(evtData.isCreatorFound());
	assertNull(evtData.getDetails());
}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#deletePoll(com.eulersbridge.iEngage.core.events.polls.DeletePollEvent)}.
	 */
	@Test
	public final void testDeletePoll()
	{
		LOG.debug("DeletingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(pollRepository).delete((any(Long.class)));
		DeletePollEvent deletePollEvent=new DeletePollEvent(testData.getNodeId());
		DeletedEvent evtData = service.deletePoll(deletePollEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#deletePoll(com.eulersbridge.iEngage.core.events.polls.DeletePollEvent)}.
	 */
	@Test
	public final void testDeletePollNotFound() 
	{
		LOG.debug("DeletingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		when(pollRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(pollRepository).delete((any(Long.class)));
		DeletePollEvent deletePollEvent=new DeletePollEvent(testData.getNodeId());
		DeletedEvent evtData = service.deletePoll(deletePollEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#updatePoll(com.eulersbridge.iEngage.core.events.polls.UpdatePollEvent)}.
	 */
	@Test
	public final void testUpdatePoll()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		Owner testOwner=new Owner();
		testOwner.setNodeId(testData.getOwner().getNodeId());
		Owner testCreator=new Owner();
		testCreator.setNodeId(testData.getCreator().getNodeId());
		when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner).thenReturn(testCreator);
		when(pollRepository.save(any(Poll.class))).thenReturn(testData);
		PollDetails dets=testData.toPollDetails();
		UpdatePollEvent createPollEvent=new UpdatePollEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePoll(createPollEvent);
		PollDetails returnedDets = (PollDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toPollDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PollEventHandler#updatePoll(com.eulersbridge.iEngage.core.events.photo.UpdatePollEvent)}.
	 */
	@Test
	public final void testUpdatePollNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		when(pollRepository.findOne(any(Long.class))).thenReturn(null);
		PollDetails dets=testData.toPollDetails();
		UpdatePollEvent createPollEvent=new UpdatePollEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePoll(createPollEvent);
		assertNull(evtData.getDetails());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	@Test
	public final void testUpdatePollOwnerNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		Owner testOwner=null;
		when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner);
		when(pollRepository.save(any(Poll.class))).thenReturn(testData);
		PollDetails dets=testData.toPollDetails();
		UpdatePollEvent createPollEvent=new UpdatePollEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePoll(createPollEvent);
		assertEquals(evtData.getNodeId(),testData.getOwner().getNodeId());
		assertFalse(((PollUpdatedEvent)evtData).isOwnerFound());
		assertNull(evtData.getDetails());
	}

	@Test
	public final void testUpdatePollOwnerNotFoundNull()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		Owner testOwner=new Owner(null);
		testData.setOwner(testOwner);
		when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
		when(pollRepository.save(any(Poll.class))).thenReturn(testData);
		PollDetails dets=testData.toPollDetails();
		UpdatePollEvent createPollEvent=new UpdatePollEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePoll(createPollEvent);
		assertEquals(evtData.getNodeId(),testData.getOwner().getNodeId());
		assertFalse(((PollUpdatedEvent)evtData).isOwnerFound());
		assertNull(evtData.getDetails());
	}

	@Test
	public final void testUpdatePollCreatorNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		Owner testOwner=new Owner();
		testOwner.setNodeId(testData.getOwner().getNodeId());
		Owner testCreator=null;
		when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner).thenReturn(testCreator);
		when(pollRepository.save(any(Poll.class))).thenReturn(testData);
		PollDetails dets=testData.toPollDetails();
		UpdatePollEvent createPollEvent=new UpdatePollEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePoll(createPollEvent);
		assertNotNull(evtData.getNodeId());
		assertEquals(evtData.getNodeId(),testData.getCreator().getNodeId());
		assertFalse(((PollUpdatedEvent)evtData).isCreatorFound());
		assertNull(evtData.getDetails());
	}

	@Test
	public final void testUpdatePollCreatorNotFoundNull()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPoll()");
		Poll testData=DatabaseDataFixture.populatePoll1();
		Owner testOwner=new Owner();
		testOwner.setNodeId(testData.getOwner().getNodeId());
		Owner testCreator=new Owner(null);
		testData.setCreator(testCreator);
		when(pollRepository.findOne(any(Long.class))).thenReturn(testData);
		when(ownerRepository.findOne(any(Long.class))).thenReturn(testOwner).thenReturn(testCreator);
		when(pollRepository.save(any(Poll.class))).thenReturn(testData);
		PollDetails dets=testData.toPollDetails();
		UpdatePollEvent createPollEvent=new UpdatePollEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePoll(createPollEvent);
		assertEquals(evtData.getNodeId(),testData.getCreator().getNodeId());
		assertFalse(((PollUpdatedEvent)evtData).isCreatorFound());
		assertNull(evtData.getDetails());
	}

}
