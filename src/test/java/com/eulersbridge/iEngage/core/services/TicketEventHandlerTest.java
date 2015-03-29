/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.ticket.*;
import com.eulersbridge.iEngage.database.domain.Support;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
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
    @Mock
    UserRepository userRepository;

    TicketEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

//		service=new TicketEventHandler(ticketRepository,candidateRepository);
		service=new TicketEventHandler(ticketRepository,electionRepository, userRepository);
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
		assertEquals(testData.getNodeId(),returnedDets.getNodeId());
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
		RequestReadTicketEvent requestReadTicketEvent=new RequestReadTicketEvent(testData.getNodeId());
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
	 * Test method for {@link com.eulersbridge.iEngage.core.services.TicketTicketHandler#readTickets(com.eulersbridge.iEngage.core.events.events.ReadAllTicket,Direction,int,int)}.
	 */
	@Test
	public final void testReadTickets()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTickets()");
		HashMap<Long, Ticket> events = DatabaseDataFixture.populateTickets();
		ArrayList<Ticket> evts=new ArrayList<Ticket>();
		Iterator<Ticket> iter=events.values().iterator();
		while (iter.hasNext())
		{
			Ticket na=iter.next();
			evts.add(na);
		}

		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<Ticket> testData=new PageImpl<Ticket>(evts,pageable,evts.size());
		when(ticketRepository.findByElectionId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		TicketsReadEvent evtData = service.readTickets(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages(),new Integer(1));
		assertEquals(evtData.getTotalItems(),new Long(evts.size()));
	}

	@Test
	public final void testReadTicketsNoneAvailable()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTickets()");
		ArrayList<Ticket> evts=new ArrayList<Ticket>();
		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<Ticket> testData=new PageImpl<Ticket>(evts,pageable,evts.size());
		when(ticketRepository.findByElectionId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		Election inst=DatabaseDataFixture.populateElection1();
		when(electionRepository.findOne(any(Long.class))).thenReturn(inst);
				
		TicketsReadEvent evtData = service.readTickets(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertEquals(evtData.getTotalPages().intValue(),0);
		assertEquals(evtData.getTotalItems().longValue(),0);
	}

	@Test
	public final void testReadTicketsNoValidInst()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTickets()");
		ArrayList<Ticket> evts=new ArrayList<Ticket>();
		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		int pageLength=10;
		int pageNumber=0;
		
		Pageable pageable=new PageRequest(pageNumber,pageLength,Direction.ASC,"a.date");
		Page<Ticket> testData=new PageImpl<Ticket>(evts,pageable,evts.size());
		when(ticketRepository.findByElectionId(any(Long.class),any(Pageable.class))).thenReturn(testData);
		when(electionRepository.findOne(any(Long.class))).thenReturn(null);
				
		TicketsReadEvent evtData = service.readTickets(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertFalse(evtData.isElectionFound());
		assertEquals(evtData.getTotalPages(),null);
		assertEquals(evtData.getTotalItems(),null);
	}

	@Test
	public final void testReadTicketsNullReturned()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingTickets()");
		
		Long institutionId=1l;
		ReadAllEvent evt=new ReadAllEvent(institutionId);
		
		Page<Ticket> testData=null;
		when(ticketRepository.findByElectionId(any(Long.class),any(Pageable.class))).thenReturn(testData);

		int pageLength=10;
		int pageNumber=0;
		TicketsReadEvent evtData = service.readTickets(evt, Direction.ASC, pageNumber, pageLength);
		assertNotNull(evtData);
		assertFalse(evtData.isElectionFound());
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
		assertEquals(evtData.getNodeId(),testData.getNodeId());
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
		DeleteTicketEvent deleteTicketEvent=new DeleteTicketEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteTicket(deleteTicketEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
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
		DeleteTicketEvent deleteTicketEvent=new DeleteTicketEvent(testData.getNodeId());
		DeletedEvent evtData = service.deleteTicket(deleteTicketEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getNodeId(),evtData.getNodeId());
	}

    @Test
    public void testSupportTicket() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("SupportTicket()");
        Ticket testTicket = DatabaseDataFixture.populateTicket1();
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Support support = DatabaseDataFixture.populateSupport(testUser, testTicket);
        SupportTicketEvent supportTicketEvent = new SupportTicketEvent(testTicket.getNodeId(), testUser.getEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ticketRepository.findOne(any(Long.class))).thenReturn(testTicket);
        when(ticketRepository.supportTicket(any(Long.class), any(String.class))).thenReturn(support);

        TicketSupportedEvent ticketSupportedEvent = service.supportTicket(supportTicketEvent);
        assertTrue(ticketSupportedEvent.isEntityFound());
        assertTrue(ticketSupportedEvent.isUserFound());
        assertTrue(ticketSupportedEvent.isResult());
    }

    @Test
    public void testSupportTicketNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("SupportTicket()");
        Ticket testTicket = DatabaseDataFixture.populateTicket1();
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Support support = DatabaseDataFixture.populateSupport(testUser, testTicket);
        SupportTicketEvent supportTicketEvent = new SupportTicketEvent(testTicket.getNodeId(), testUser.getEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ticketRepository.findOne(any(Long.class))).thenReturn(null);
        when(ticketRepository.supportTicket(any(Long.class), any(String.class))).thenReturn(support);

        TicketSupportedEvent ticketSupportedEvent = service.supportTicket(supportTicketEvent);
        assertFalse(ticketSupportedEvent.isEntityFound());
        assertTrue(ticketSupportedEvent.isUserFound());
        assertFalse(ticketSupportedEvent.isResult());
    }

    @Test
    public void testSupportTicketUserNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("SupportTicket()");
        Ticket testTicket = DatabaseDataFixture.populateTicket1();
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        Support support = DatabaseDataFixture.populateSupport(testUser, testTicket);
        SupportTicketEvent supportTicketEvent = new SupportTicketEvent(testTicket.getNodeId(), testUser.getEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(ticketRepository.findOne(any(Long.class))).thenReturn(testTicket);
        when(ticketRepository.supportTicket(any(Long.class), any(String.class))).thenReturn(support);

        TicketSupportedEvent ticketSupportedEvent = service.supportTicket(supportTicketEvent);
        assertTrue(ticketSupportedEvent.isEntityFound());
        assertFalse(ticketSupportedEvent.isUserFound());
        assertFalse(ticketSupportedEvent.isResult());
    }

    @Test
    public void testWithdrawSupportTicket() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("WithdrawSupportTicket()");
        Ticket testTicket = DatabaseDataFixture.populateTicket1();
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        SupportTicketEvent supportTicketEvent = new SupportTicketEvent(testTicket.getNodeId(), testUser.getEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ticketRepository.findOne(any(Long.class))).thenReturn(testTicket);

        TicketSupportedEvent ticketSupportedEvent = service.withdrawSupportTicket(supportTicketEvent);
        assertTrue(ticketSupportedEvent.isEntityFound());
        assertTrue(ticketSupportedEvent.isUserFound());
        assertTrue(ticketSupportedEvent.isResult());
    }

    @Test
    public void testWithdrawSupportTicketNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("WithdrawSupportTicket()");
        Ticket testTicket = DatabaseDataFixture.populateTicket1();
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        SupportTicketEvent supportTicketEvent = new SupportTicketEvent(testTicket.getNodeId(), testUser.getEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(testUser);
        when(ticketRepository.findOne(any(Long.class))).thenReturn(null);

        TicketSupportedEvent ticketSupportedEvent = service.withdrawSupportTicket(supportTicketEvent);
        assertFalse(ticketSupportedEvent.isEntityFound());
        assertTrue(ticketSupportedEvent.isUserFound());
        assertFalse(ticketSupportedEvent.isResult());
    }

    @Test
    public void testWithdrawSupportTicketUserNotFound() throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("WithdrawSupportTicket()");
        Ticket testTicket = DatabaseDataFixture.populateTicket1();
        User testUser = DatabaseDataFixture.populateUserGnewitt();
        SupportTicketEvent supportTicketEvent = new SupportTicketEvent(testTicket.getNodeId(), testUser.getEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(ticketRepository.findOne(any(Long.class))).thenReturn(testTicket);

        TicketSupportedEvent ticketSupportedEvent = service.withdrawSupportTicket(supportTicketEvent);
        assertTrue(ticketSupportedEvent.isEntityFound());
        assertFalse(ticketSupportedEvent.isUserFound());
        assertFalse(ticketSupportedEvent.isResult());
    }
}
