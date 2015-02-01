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
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.CreateTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.DeleteTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.ReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.RequestReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketCreatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.core.events.ticket.UpdateTicketEvent;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Ticket;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.CandidateRepository;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.TicketRepository;

/**
 * @author Greg Newitt
 *
 */
public class TicketEventHandlerTest
{

    private static Logger LOG = LoggerFactory.getLogger(TicketEventHandlerTest.class);

    @Mock
	TicketRepository ticketRepository;
    @Mock
	ElectionRepository electionRepository;
    @Mock
	CandidateRepository candidateRepository;

    TicketEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

//		service=new TicketEventHandler(ticketRepository,candidateRepository);
		service=new TicketEventHandler(ticketRepository,electionRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TicketEventHandler#TIcketEventHandler(com.eulersbridge.iEngage.database.repository.TicketRepository)}.
	 */
	@Test
	public final void testTicketEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TicketEventHandler#createTicket(com.eulersbridge.iEngage.core.events.ticket.CreateTicketEvent)}.
	 */
	@Test
	public final void testCreateTicket()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingTicket()");
		Ticket testData=DatabaseDataFixture.populateTicket1();
		Election testElec = DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(testElec);
		when(ticketRepository.save(any(Ticket.class))).thenReturn(testData);
		TicketDetails dets=testData.toTicketDetails();
		CreateTicketEvent createTicketEvent=new CreateTicketEvent(dets);
		CreatedEvent evtData = service.createTicket(createTicketEvent);
		Details returnedDets = evtData.getDetails();
		assertEquals(testData.toTicketDetails(),returnedDets);
		assertEquals(testData.getTicketId(),returnedDets.getNodeId());
		assertNotNull(evtData.getNodeId());
	}

	@Test
	public final void testCreateTicketElectionNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingTicket()");
		Ticket testData=DatabaseDataFixture.populateTicket1();
		Election testElec = null;
		when(electionRepository.findOne(any(Long.class))).thenReturn(testElec);
		when(ticketRepository.save(any(Ticket.class))).thenReturn(testData);
		TicketDetails dets=testData.toTicketDetails();
		CreateTicketEvent createTicketEvent=new CreateTicketEvent(dets);
		CreatedEvent evtData = service.createTicket(createTicketEvent);
		Details returnedDets = evtData.getDetails();
		assertNull(returnedDets);
		assertTrue(evtData.isFailed());
		assertFalse(((TicketCreatedEvent)evtData).isElectionFound());
		assertEquals(testData.getElection().getNodeId(),((TicketCreatedEvent)evtData).getFailedId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TicketEventHandler#requestReadTicket(com.eulersbridge.iEngage.core.events.ticket.RequestReadTicketEvent)}.
	 */
	@Test
	public final void testRequestReadTicket()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTicket()");
		Ticket testData=DatabaseDataFixture.populateTicket1();
		when(ticketRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadTicketEvent requestReadTicketEvent=new RequestReadTicketEvent(testData.getTicketId());
		ReadTicketEvent evtData = (ReadTicketEvent) service.requestReadTicket(requestReadTicketEvent);
		TicketDetails returnedDets = (TicketDetails)evtData.getDetails();
		assertEquals(returnedDets,testData.toTicketDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
	}
	@Test
	public final void testReadTicketNotFound()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTicket()");
		Ticket testData=null;
		Long nodeId=23l;
		when(ticketRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadTicketEvent requestReadTicketEvent=new RequestReadTicketEvent(nodeId);
		ReadEvent evtData = service.requestReadTicket(requestReadTicketEvent);
		TicketDetails returnedDets = (TicketDetails)evtData.getDetails();
		assertNull(returnedDets);
		assertEquals(evtData.getNodeId(),nodeId);
		assertFalse(evtData.isEntityFound());
	}


	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TicketEventHandler#updateTicket(com.eulersbridge.iEngage.core.events.ticket.UpdateTicketEvent)}.
	 */
	@Test
	public final void testUpdateTicket()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingTicket()");
		Ticket testData=DatabaseDataFixture.populateTicket1();
		when(ticketRepository.findOne(any(Long.class))).thenReturn(testData);
		when(ticketRepository.save(any(Ticket.class))).thenReturn(testData);
		TicketDetails dets=testData.toTicketDetails();
		UpdateTicketEvent createElectionEvent=new UpdateTicketEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateTicket(createElectionEvent);
		TicketDetails returnedDets = (TicketDetails) evtData.getDetails();
		assertEquals(returnedDets,testData.toTicketDetails());
		assertEquals(evtData.getNodeId(),returnedDets.getNodeId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TicketEventHandler#updateTicket(com.eulersbridge.iEngage.core.events.tickets.UpdateTicketEvent)}.
	 */
	@Test
	public final void testUpdateTicketNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingTicket()");
		Ticket testData=DatabaseDataFixture.populateTicket1();
		when(ticketRepository.findOne(any(Long.class))).thenReturn(null);
		when(ticketRepository.save(any(Ticket.class))).thenReturn(testData);
		TicketDetails dets=testData.toTicketDetails();
		UpdateTicketEvent createTicketEvent=new UpdateTicketEvent(dets.getNodeId(), dets);
		UpdatedEvent evtData = service.updateTicket(createTicketEvent);
		assertNull(evtData.getDetails());
		assertEquals(evtData.getNodeId(),testData.getTicketId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TicketEventHandler#deleteTicket(com.eulersbridge.iEngage.core.events.ticket.DeleteTicketEvent)}.
	 */
	@Test
	public final void testDeleteTicket()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingTicket()");
		Ticket testData=DatabaseDataFixture.populateTicket1();
		when(ticketRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(ticketRepository).delete((any(Long.class)));
		DeleteTicketEvent deleteTicketEvent=new DeleteTicketEvent(testData.getTicketId());
		DeletedEvent evtData = service.deleteTicket(deleteTicketEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getTicketId(),evtData.getNodeId());
	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TicketEventHandler#deleteTicket(com.eulersbridge.iEngage.core.events.tickets.DeleteTicketEvent)}.
	 */
	@Test
	public final void testDeleteTicketNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingTicket()");
		Ticket testData=DatabaseDataFixture.populateTicket1();
		when(ticketRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(ticketRepository).delete((any(Long.class)));
		DeleteTicketEvent deleteTicketEvent=new DeleteTicketEvent(testData.getTicketId());
		DeletedEvent evtData = service.deleteTicket(deleteTicketEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getTicketId(),evtData.getNodeId());
	}

}
