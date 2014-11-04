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

import com.eulersbridge.iEngage.core.events.events.CreateEventEvent;
import com.eulersbridge.iEngage.core.events.events.DeleteEventEvent;
import com.eulersbridge.iEngage.core.events.events.EventCreatedEvent;
import com.eulersbridge.iEngage.core.events.events.EventDeletedEvent;
import com.eulersbridge.iEngage.core.events.events.EventDetails;
import com.eulersbridge.iEngage.core.events.events.EventUpdatedEvent;
import com.eulersbridge.iEngage.core.events.events.ReadEventEvent;
import com.eulersbridge.iEngage.core.events.events.RequestReadEventEvent;
import com.eulersbridge.iEngage.core.events.events.UpdateEventEvent;
import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.repository.EventRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

/**
 * @author Greg Newitt
 *
 */
public class EventEventHandlerTest
{
    private static Logger LOG = LoggerFactory.getLogger(EventEventHandlerTest.class);

    @Mock
	EventRepository eventRepository;
    @Mock
	InstitutionRepository institutionRepository;

    EventEventHandler service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		service=new EventEventHandler(eventRepository,institutionRepository);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#EventEventHandler(com.eulersbridge.iEngage.database.repository.EventRepository)}.
	 */
	@Test
	public final void testEventEventHandler()
	{
		assertNotNull("Not yet implemented",service);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#createEvent(com.eulersbridge.iEngage.core.events.events.CreateEventEvent)}.
	 */
	@Test
	public final void testCreateEvent()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingEvent()");
		Event testData=DatabaseDataFixture.populateEvent1();
		Institution testInst=DatabaseDataFixture.populateInstUniMelb();
		when(institutionRepository.findOne(any(Long.class))).thenReturn(testInst);
		when(eventRepository.save(any(Event.class))).thenReturn(testData);
		EventDetails dets=testData.toEventDetails();
		CreateEventEvent createEventEvent=new CreateEventEvent(dets);
		EventCreatedEvent evtData = service.createEvent(createEventEvent);
		EventDetails returnedDets = evtData.getEventDetails();
		assertEquals(returnedDets,testData.toEventDetails());
		assertEquals(evtData.getEventId(),returnedDets.getEventId());
		assertNotNull(evtData.getEventDetails());
	}

	@Test
	public final void testCreateEventInstNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreatingEvent()");
		Event testData=DatabaseDataFixture.populateEvent1();
		Institution testInst=null;
		when(institutionRepository.findOne(any(Long.class))).thenReturn(testInst);
		when(eventRepository.save(any(Event.class))).thenReturn(testData);
		EventDetails dets=testData.toEventDetails();
		CreateEventEvent createEventEvent=new CreateEventEvent(dets);
		EventCreatedEvent evtData = service.createEvent(createEventEvent);
		assertFalse(evtData.isInstitutionFound());
		assertEquals(evtData.getEventId(),testData.getInstitution().getNodeId());
		assertNull(evtData.getEventDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#requestReadEvent(com.eulersbridge.iEngage.core.events.events.RequestReadEventEvent)}.
	 */
	@Test
	public final void testReadEvent()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingEvent()");
		Event testData=DatabaseDataFixture.populateEvent1();
		when(eventRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadEventEvent readElectionEvent=new RequestReadEventEvent(testData.getEventId());
		ReadEventEvent evtData = service.readEvent(readElectionEvent);
		EventDetails returnedDets = evtData.getEventDetails();
		assertEquals(returnedDets,testData.toEventDetails());
		assertEquals(evtData.getEventId(),returnedDets.getEventId());
		assertTrue(evtData.isEntityFound());
	}

	@Test
	public final void testReadEventNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("ReadingEvent()");
		Event testData=null;
		Long nodeId=1l;
		when(eventRepository.findOne(any(Long.class))).thenReturn(testData);
		RequestReadEventEvent readElectionEvent=new RequestReadEventEvent(nodeId);
		ReadEventEvent evtData = service.readEvent(readElectionEvent);
		EventDetails returnedDets = evtData.getEventDetails();
		assertNull(returnedDets);
		assertEquals(nodeId,evtData.getEventId());
		assertFalse(evtData.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#updateEvent(com.eulersbridge.iEngage.core.events.events.UpdateEventEvent)}.
	 */
	@Test
	public final void testUpdateEvent()
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingEvent()");
		Event testData=DatabaseDataFixture.populateEvent1();
		when(eventRepository.findOne(any(Long.class))).thenReturn(testData);
		when(eventRepository.save(any(Event.class))).thenReturn(testData);
		EventDetails dets=testData.toEventDetails();
		UpdateEventEvent createEventEvent=new UpdateEventEvent(dets.getEventId(), dets);
		EventUpdatedEvent evtData = service.updateEvent(createEventEvent);
		EventDetails returnedDets = evtData.getEventDetails();
		assertEquals(returnedDets,testData.toEventDetails());
		assertEquals(evtData.getEventId(),returnedDets.getEventId());
		assertTrue(evtData.isEntityFound());
		assertNotNull(evtData.getEventId());
	}
	@Test
	public final void testUpdateEventNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UpdatingEvent()");
		Event testData=DatabaseDataFixture.populateEvent1();
		when(eventRepository.findOne(any(Long.class))).thenReturn(null);
		when(eventRepository.save(any(Event.class))).thenReturn(testData);
		EventDetails dets=testData.toEventDetails();
		UpdateEventEvent createEventEvent=new UpdateEventEvent(dets.getEventId(), dets);
		EventUpdatedEvent evtData = service.updateEvent(createEventEvent);
		assertNull(evtData.getEventDetails());
		assertEquals(evtData.getEventId(),testData.getEventId());
		assertFalse(evtData.isEntityFound());
		assertNotNull(evtData.getEventId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.services.EventEventHandler#deleteEvent(com.eulersbridge.iEngage.core.events.events.DeleteEventEvent)}.
	 */
	@Test
	public final void testDeleteEvent()
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingEvent()");
		Event testData=DatabaseDataFixture.populateEvent1();
		when(eventRepository.findOne(any(Long.class))).thenReturn(testData);
		doNothing().when(eventRepository).delete((any(Long.class)));
		DeleteEventEvent deleteEventEvent=new DeleteEventEvent(testData.getEventId());
		EventDeletedEvent evtData = service.deleteEvent(deleteEventEvent);
		assertTrue(evtData.isEntityFound());
		assertTrue(evtData.isDeletionCompleted());
		assertEquals(testData.getEventId(),evtData.getEventId());
	}
	@Test
	public final void testDeleteElectionNotFound() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("DeletingEvent()");
		Event testData=DatabaseDataFixture.populateEvent1();
		when(eventRepository.findOne(any(Long.class))).thenReturn(null);
		doNothing().when(eventRepository).delete((any(Long.class)));
		DeleteEventEvent deleteEventEvent=new DeleteEventEvent(testData.getEventId());
		EventDeletedEvent evtData = service.deleteEvent(deleteEventEvent);
		assertFalse(evtData.isEntityFound());
		assertFalse(evtData.isDeletionCompleted());
		assertEquals(testData.getEventId(),evtData.getEventId());
	}

}
