package com.eulersbridge.iEngage.core.events.elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ElectionDomainCreatedEventTest
{
	ElectionDetails electionDetails;
	final Long electionId = new Long(0);
	final String title = "election title";
	final Long start = new Long(1);
	final Long end = new Long(4);
	final Long startVoting = new Long(2);
	final Long endVoting = new Long(3);
	ElectionCreatedEvent electionCreatedEvent;

	@Before
	public void setUp() throws Exception
	{
		electionDetails = new ElectionDetails();
		electionDetails.setElectionId(electionId);
		electionDetails.setTitle(title);
		electionDetails.setStart(start);
		electionDetails.setEnd(end);
		electionDetails.setStartVoting(startVoting);
		electionDetails.setEndVoting(endVoting);
		electionCreatedEvent = new ElectionCreatedEvent(electionId,
				electionDetails);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void testElectionCreatedEvent() throws Exception
	{
		assertNotNull("electionCreatedEvent is null", electionCreatedEvent);
		ElectionCreatedEvent electionCreatedEvent1 = new ElectionCreatedEvent(
				electionId);
		assertNotNull("electionCreatedEvent is null", electionCreatedEvent1);
	}

	@Test
	public void testGetElectionId() throws Exception
	{
		assertEquals("electionId does not match", electionId,
				electionCreatedEvent.getElectionId());
	}

	@Test
	public void testSetKey() throws Exception
	{
		electionCreatedEvent.setKey(new Long(10));
		assertEquals("id does not match", new Long(10),
				electionCreatedEvent.getElectionId());
	}

	@Test
	public void testSetElectionDetails() throws Exception
	{
		ElectionDetails electionDetails1 = new ElectionDetails();
		electionDetails1.setElectionId(new Long(10));
		electionDetails1.setTitle("new Title");
		electionDetails1.setStart(start);
		electionDetails1.setEnd(end);
		electionDetails1.setStartVoting(startVoting);
		electionDetails1.setEndVoting(endVoting);

		electionCreatedEvent.setDetails(electionDetails1);
		assertEquals("electionDetail does not match", electionDetails1,
				electionCreatedEvent.getDetails());
	}

	@Test
	public void testGetElectionDetails() throws Exception
	{
		assertEquals("electionDetail does not match", electionDetails,
				electionCreatedEvent.getDetails());
	}

	@Test
	public void testInsitutionNotFound() throws Exception
	{
		Long id = 5l;
		ElectionCreatedEvent evt = ElectionCreatedEvent.institutionNotFound(id);
		assertEquals(id, evt.getElectionId());
		assertNull(evt.getDetails());
		assertFalse(evt.isInstitutionFound());
	}
}