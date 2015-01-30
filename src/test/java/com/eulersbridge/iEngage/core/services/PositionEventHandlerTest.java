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
import com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent;
import com.eulersbridge.iEngage.core.events.positions.DeletePositionEvent;
import com.eulersbridge.iEngage.core.events.positions.PositionCreatedEvent;
import com.eulersbridge.iEngage.core.events.positions.PositionDetails;
import com.eulersbridge.iEngage.core.events.positions.PositionReadEvent;
import com.eulersbridge.iEngage.core.events.positions.RequestReadPositionEvent;
import com.eulersbridge.iEngage.core.events.positions.UpdatePositionEvent;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Position;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.PositionRepository;

/**
 * @author Greg Newitt
 *
 */
public class PositionEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(PositionEventHandlerTest.class);

    @Mock
	ElectionRepository electionRepository;
    @Mock
	PositionRepository positionRepository;

    PositionEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new PositionEventHandler(positionRepository,electionRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#PositionEventHandler(com.eulersbridge.iEngage.database.repository.PositionRepository)}.
	 */
	@Test
	public final void testPositionEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#createPosition(com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent)}.
	 */
	@Test
	public final void testCreatePosition()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingPosition()");
		Position testData=DatabaseDataFixture.populatePosition1();
		Election testInst=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(testInst);
		when(positionRepository.save(any(Position.class))).thenReturn(testData);
		PositionDetails dets=testData.toPositionDetails();
		CreatePositionEvent createPositionEvent=new CreatePositionEvent(dets);
		CreatedEvent evtData = service.createPosition(createPositionEvent);
		PositionDetails returnedDets = (PositionDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toPositionDetails());
		assertNotNull(evtData.getNodeId());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#createPosition(com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent)}.
	 */
	@Test
	public final void testCreatePositionElectionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingPosition()");
		Position testData=DatabaseDataFixture.populatePosition1();
		Election testInst=null;
		when(electionRepository.findOne(any(Long.class))).thenReturn(testInst);
		when(positionRepository.save(any(Position.class))).thenReturn(testData);
		PositionDetails dets=testData.toPositionDetails();
		CreatePositionEvent createElectionEvent=new CreatePositionEvent(dets);
		CreatedEvent evtData = service.createPosition(createElectionEvent);
		assertFalse(((PositionCreatedEvent)evtData).isElectionFound());
		assertEquals(((PositionCreatedEvent)evtData).getFailedNodeId(),testData.getElection().getNodeId());
		assertNull(evtData.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#readPosition(com.eulersbridge.iEngage.core.events.positions.RequestReadPositionEvent)}.
	 */
	@Test
	public final void testReadPosition()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPosition()");
		Position testData=DatabaseDataFixture.populatePosition1();
		when(positionRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadPositionEvent requestReadPositionEvent=new RequestReadPositionEvent(testData.getNodeId());
		PositionReadEvent evtData = (PositionReadEvent) service.readPosition(requestReadPositionEvent);
		PositionDetails returnedDets = (PositionDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toPositionDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}

	@Test
	public final void testReadPositionNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingPosition()");
		Position testData=null;
		Long nodeId=23l;
		when(positionRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadPositionEvent requestReadPositionEvent=new RequestReadPositionEvent(nodeId);
		ReadEvent evtData = service.readPosition(requestReadPositionEvent);
		PositionDetails returnedDets = (PositionDetails)evtData.getDetails();
		assertNull(returnedDets);
		assertEquals(evtData.getNodeId(),nodeId);
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#updatePosition(com.eulersbridge.iEngage.core.events.positions.UpdatePositionEvent)}.
	 */
	@Test
	public final void testUpdatePosition()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPosition()");
		Position testData=DatabaseDataFixture.populatePosition1();
		when(positionRepository.findOne(any(Long.class))).thenReturn(testData);
		when(positionRepository.save(any(Position.class))).thenReturn(testData);
		PositionDetails dets=testData.toPositionDetails();
		UpdatePositionEvent createElectionEvent=new UpdatePositionEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePosition(createElectionEvent);
		PositionDetails returnedDets = (PositionDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toPositionDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#updatePosition(com.eulersbridge.iEngage.core.events.positions.UpdatePositionEvent)}.
	 */
	@Test
	public final void testUpdatePositionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingPosition()");
		Position testData=DatabaseDataFixture.populatePosition1();
		when(positionRepository.findOne(any(Long.class))).thenReturn(null);
		when(positionRepository.save(any(Position.class))).thenReturn(testData);
		PositionDetails dets=testData.toPositionDetails();
		UpdatePositionEvent createPositionEvent=new UpdatePositionEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updatePosition(createPositionEvent);
		assertNull(evtData.getDetails());
		assertEquals(evtData.getNodeId(),testData.getNodeId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#deletePosition(com.eulersbridge.iEngage.core.events.positions.DeletePositionEvent)}.
	 */
	@Test
	public final void testDeletePosition()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingPosition()");
		Position testData=DatabaseDataFixture.populatePosition1();
		when(positionRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(positionRepository).delete((any(Long.class)));
		DeletePositionEvent deletePositionEvent=new DeletePositionEvent(testData.getNodeId());
		DeletedEvent evtData = service.deletePosition(deletePositionEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#deletePosition(com.eulersbridge.iEngage.core.events.positions.DeletePositionEvent)}.
	 */
	@Test
	public final void testDeletePositionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingPosition()");
		Position testData=DatabaseDataFixture.populatePosition1();
		when(positionRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(positionRepository).delete((any(Long.class)));
		DeletePositionEvent deletePositionEvent=new DeletePositionEvent(testData.getNodeId());
		DeletedEvent evtData = service.deletePosition(deletePositionEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}


}