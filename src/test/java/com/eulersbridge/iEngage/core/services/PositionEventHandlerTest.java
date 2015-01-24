/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.positions.CreatePositionEvent;
import com.eulersbridge.iEngage.core.events.positions.PositionCreatedEvent;
import com.eulersbridge.iEngage.core.events.positions.PositionDetails;
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
		PositionCreatedEvent evtData = service.createPosition(createPositionEvent);
		PositionDetails returnedDets = (PositionDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toPositionDetails());
		assertNotNull(evtData.getNodeId());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#readPosition(com.eulersbridge.iEngage.core.events.positions.RequestReadPositionEvent)}.
	 */
	@Test
	public final void testRequestReadPosition()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#updatePosition(com.eulersbridge.iEngage.core.events.positions.UpdatePositionEvent)}.
	 */
	@Test
	public final void testUpdatePosition()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.PositionEventHandler#deletePosition(com.eulersbridge.iEngage.core.events.positions.DeletePositionEvent)}.
	 */
	@Test
	public final void testDeletePosition()
	{
		fail("Not yet implemented"); // TODO
	}

}
