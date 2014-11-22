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
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.elections.CreateElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionCreatedEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import com.eulersbridge.iEngage.core.events.elections.ElectionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.elections.ReadElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent;
import com.eulersbridge.iEngage.core.events.elections.UpdateElectionEvent;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

/**
 * @author Greg Newitt
 *
 */
public class ElectionEventHandlerTest 
{
    private static Logger LOG = LoggerFactory.getLogger(ElectionEventHandlerTest.class);

    @Mock
	ElectionRepository electionRepository;
    @Mock
	InstitutionRepository institutionRepository;

    ElectionEventHandler service;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);

		service=new ElectionEventHandler(electionRepository,institutionRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#ElectionEventHandler(com.eulersbridge.iEngage.database.repository.ElectionRepository)}.
	 */
	@Test
	public final void testElectionEventHandler() 
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#requestReadElection(com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent)}.
	 */
	@Test
	public final void testRequestReadElection() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadElectionEvent requestReadElectionEvent=new RequestReadElectionEvent(testData.getNodeId());
		ReadElectionEvent evtData = (ReadElectionEvent) service.readElection(requestReadElectionEvent);
		ElectionDetails returnedDets = (ElectionDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toElectionDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getElectionId());
		assertTrue(evtData.isEntityFound());
	}

	@Test
	public final void testRequestReadElectionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingElection()");
		Election testData=null;
		Long nodeId=1l;
		when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadElectionEvent requestReadElectionEvent=new RequestReadElectionEvent(nodeId);
		ReadEvent evtData = service.readElection(requestReadElectionEvent);
		assertNull(evtData.getDetails());
		assertEquals(nodeId,evtData.getNodeId());
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#createElection(com.eulersbridge.iEngage.core.events.elections.CreateElectionEvent)}.
	 */
	@Test
	public final void testCreateElection() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		Institution testInst=DatabaseDataFixture.populateInstUniMelb();
		when(institutionRepository.findOne(any(Long.class))).thenReturn(testInst);
		when(electionRepository.save(any(Election.class))).thenReturn(testData);
		ElectionDetails dets=testData.toElectionDetails();
		CreateElectionEvent createElectionEvent=new CreateElectionEvent(dets);
		ElectionCreatedEvent evtData = service.createElection(createElectionEvent);
		ElectionDetails returnedDets = (ElectionDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toElectionDetails());
		assertEquals(evtData.getElectionId(),returnedDets.getElectionId());
		assertNotNull(evtData.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#createElection(com.eulersbridge.iEngage.core.events.elections.CreateElectionEvent)}.
	 */
	@Test
	public final void testCreateElectionInstNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		Institution testInst=null;
		when(institutionRepository.findOne(any(Long.class))).thenReturn(testInst);
		when(electionRepository.save(any(Election.class))).thenReturn(testData);
		ElectionDetails dets=testData.toElectionDetails();
		CreateElectionEvent createElectionEvent=new CreateElectionEvent(dets);
		ElectionCreatedEvent evtData = service.createElection(createElectionEvent);
		assertFalse(evtData.isInstitutionFound());
		assertEquals(evtData.getElectionId(),testData.getInstitution().getNodeId());
		assertNull(evtData.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#readPreviousElection(com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent)}.
	 */
	@Test
	public final void testReadPreviousElection() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPreviousElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findPreviousElection(any(Long.class))).thenReturn(testData);
		RequestReadElectionEvent requestReadElectionEvent=new RequestReadElectionEvent(testData.getNodeId());
		ReadElectionEvent evtData = (ReadElectionEvent) service.readPreviousElection(requestReadElectionEvent);
		ElectionDetails returnedDets = (ElectionDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toElectionDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getElectionId());
		assertTrue(evtData.isEntityFound());
	}

	@Test
	public final void testReadPreviousElectionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPreviousElection()");
		Election testData=null;
		Long nodeId=1l;
		when(electionRepository.findPreviousElection(any(Long.class))).thenReturn(testData);
		RequestReadElectionEvent requestReadElectionEvent=new RequestReadElectionEvent(nodeId);
		ReadEvent evtData = service.readPreviousElection(requestReadElectionEvent);
		ElectionDetails returnedDets = (ElectionDetails) evtData.getDetails();
		assertNull(returnedDets);
		assertEquals(nodeId,evtData.getNodeId());
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#readNextElection(com.eulersbridge.iEngage.core.events.elections.RequestReadElectionEvent)}.
	 */
	@Test
	public final void testReadNextElection() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingNextElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findNextElection(any(Long.class))).thenReturn(testData);
		RequestReadElectionEvent requestReadElectionEvent=new RequestReadElectionEvent(testData.getNodeId());
		ReadElectionEvent evtData = (ReadElectionEvent) service.readNextElection(requestReadElectionEvent);
		ElectionDetails returnedDets = (ElectionDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toElectionDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getElectionId());
		assertTrue(evtData.isEntityFound());
	}

	@Test
	public final void testReadNextElectionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingNextElection()");
		Election testData=null;
		Long nodeId=1l;
		when(electionRepository.findNextElection(any(Long.class))).thenReturn(testData);
		RequestReadElectionEvent requestReadElectionEvent=new RequestReadElectionEvent(nodeId);
		ReadEvent evtData = service.readNextElection(requestReadElectionEvent);
		assertNull(evtData.getDetails());
		assertEquals(nodeId,evtData.getNodeId());
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#deleteElection(com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent)}.
	 */
	@Test
	public final void testDeleteElection() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(electionRepository).delete((any(Long.class)));
		DeleteElectionEvent deleteElectionEvent=new DeleteElectionEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteElection(deleteElectionEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#deleteElection(com.eulersbridge.iEngage.core.events.elections.DeleteElectionEvent)}.
	 */
	@Test
	public final void testDeleteElectionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(electionRepository).delete((any(Long.class)));
		DeleteElectionEvent deleteElectionEvent=new DeleteElectionEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteElection(deleteElectionEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#updateElection(com.eulersbridge.iEngage.core.events.elections.UpdateElectionEvent)}.
	 */
	@Test
	public final void testUpdateElection() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(testData);
		when(electionRepository.save(any(Election.class))).thenReturn(testData);
		ElectionDetails dets=testData.toElectionDetails();
		UpdateElectionEvent createElectionEvent=new UpdateElectionEvent(dets.getElectionId(), dets);
		ElectionUpdatedEvent evtData = service.updateElection(createElectionEvent);
		ElectionDetails returnedDets = evtData.getElectionDetails();
		assertEquals(returnedDets,testData.toElectionDetails());
		assertEquals(evtData.getElectionId(),returnedDets.getElectionId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.ElectionEventHandler#updateElection(com.eulersbridge.iEngage.core.events.elections.UpdateElectionEvent)}.
	 */
	@Test
	public final void testUpdateElectionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingElection()");
		Election testData=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(null);
		when(electionRepository.save(any(Election.class))).thenReturn(testData);
		ElectionDetails dets=testData.toElectionDetails();
		UpdateElectionEvent createElectionEvent=new UpdateElectionEvent(dets.getElectionId(), dets);
		ElectionUpdatedEvent evtData = service.updateElection(createElectionEvent);
		assertNull(evtData.getElectionDetails());
		assertEquals(evtData.getElectionId(),testData.getNodeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getElectionId());
	}

}
