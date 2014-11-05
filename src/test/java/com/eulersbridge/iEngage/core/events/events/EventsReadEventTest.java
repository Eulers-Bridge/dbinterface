package com.eulersbridge.iEngage.core.events.events;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

public class EventsReadEventTest
{
	private Long totalElements=1l;
	private Long institutionId;
	private ArrayList<EventDetails> dets=new ArrayList<EventDetails>();
	private Integer totalPages=1;
	EventsReadEvent evtTest;
	Event evt=DatabaseDataFixture.populateEvent1();
	
	@Before
	public void setUp() throws Exception
	{
		dets.add(evt.toEventDetails());
		institutionId=evt.getNewsFeed().getInstitution().getNodeId();
		evtTest=new EventsReadEvent(institutionId, dets, totalElements, totalPages);
	}

	@Test
	public final void testEventsReadEvent()
	{
		evtTest=new EventsReadEvent();
		assertNotNull("Not yet implemented",evtTest);
	}

	@Test
	public final void testEventsReadEventLongCollectionOfEventDetails()
	{
		evtTest=new EventsReadEvent(institutionId, dets);
		assertNotNull("Not yet implemented",evtTest);
	}

	@Test
	public final void testEventsReadEventLongArrayListOfEventDetailsLongInt()
	{
		assertNotNull("Not yet implemented",evtTest);
	}

	@Test
	public final void testGetInstId()
	{
		assertEquals(evt.getNewsFeed().getInstitution().getNodeId(),evtTest.getInstId());
	}

	@Test
	public final void testSetInstId()
	{
		Long test=5432l;
		evtTest.setInstId(test);
		assertEquals(test,evtTest.getInstId());
	}

	@Test
	public final void testIsNewsFeedFound()
	{
		assertTrue(evtTest.isNewsFeedFound());
	}

	@Test
	public final void testSetNewsFeedFound()
	{
		evtTest.setNewsFeedFound(false);
		assertFalse(evtTest.isNewsFeedFound());
	}

	@Test
	public final void testIsInstitutionFound()
	{
		assertTrue(evtTest.isInstitutionFound());
	}

	@Test
	public final void testSetInstitutionFound()
	{
		evtTest.setInstitutionFound(false);
		assertFalse(evtTest.isInstitutionFound());
	}

	@Test
	public final void testIsEventsFound()
	{
		assertTrue(evtTest.isEventsFound());
	}

	@Test
	public final void testSetEventsFound()
	{
		evtTest.setEventsFound(false);
		assertFalse(evtTest.isEventsFound());
	}

	@Test
	public final void testGetTotalEvents()
	{
		assertEquals(totalElements,evtTest.getTotalEvents());
	}

	@Test
	public final void testSetTotalEvents()
	{
		Long tot=453l;
		evtTest.setTotalEvents(tot);
		assertEquals(tot,evtTest.getTotalEvents());
	}

	@Test
	public final void testGetTotalPages()
	{
		assertEquals(totalPages,evtTest.getTotalPages());
	}

	@Test
	public final void testSetTotalPages()
	{
		Integer tot=453;
		evtTest.setTotalPages(tot);
		assertEquals(tot,evtTest.getTotalPages());
	}

	@Test
	public final void testGetArticles()
	{
		assertEquals(dets,evtTest.getEvents());
	}

	@Test
	public final void testSetArticles()
	{
		ArrayList<EventDetails> tdets=new ArrayList<EventDetails>();
		assertNotEquals(tdets,evtTest.getEvents());
		evtTest.setEvents(tdets);
		assertEquals(tdets,evtTest.getEvents());
	}

	@Test
	public final void testNewsFeedNotFound()
	{
		evtTest=EventsReadEvent.newsFeedNotFound();
		assertNotNull(evtTest);
		assertFalse(evtTest.isNewsFeedFound());
		assertFalse(evtTest.isEntityFound());
		assertTrue(evtTest.isInstitutionFound());
		assertFalse(evtTest.isEventsFound());
	}

	@Test
	public final void testInstitutionNotFound()
	{
		evtTest=EventsReadEvent.institutionNotFound();
		assertNotNull(evtTest);
		assertFalse(evtTest.isNewsFeedFound());
		assertFalse(evtTest.isEntityFound());
		assertFalse(evtTest.isInstitutionFound());
		assertFalse(evtTest.isEventsFound());
	}

}
