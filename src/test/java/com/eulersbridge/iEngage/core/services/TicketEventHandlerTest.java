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
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.CreateTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.DeleteTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.ReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.RequestReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketCreatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.core.events.ticket.UpdateTicketEvent;
import com.eulersbridge.iEngage.database.domain.Ticket;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.CandidateRepository;
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
		service=new TicketEventHandler(ticketRepository);
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
		when(ticketRepository.save(any(Ticket.class))).thenReturn(testData);
		TicketDetails dets=testData.toTicketDetails();
		CreateTicketEvent createTicketEvent=new CreateTicketEvent(dets);
		TicketCreatedEvent evtData = service.createTicket(createTicketEvent);
		Details returnedDets = evtData.getDetails();
		assertEquals(testData.toTicketDetails(),returnedDets);
		assertEquals(testData.getTicketId(),returnedDets.getNodeId());
		assertNotNull(evtData.getNodeId());
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

}
