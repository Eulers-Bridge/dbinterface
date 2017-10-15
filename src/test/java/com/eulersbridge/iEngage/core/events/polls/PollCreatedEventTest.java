package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class PollCreatedEventTest
{
	final Long pollId = new Long(0);
	final String question = "a question";
	final String answers = "some answers";
	final Long start = new Long(1);
	final Long duration = new Long(2);
	final Long ownerId = new Long(31);
	final Long creatorId = new Long(42);
	PollDetails pollDetails;

	PollCreatedEvent pollCreatedEvent;

	@Before
	public void setUp() throws Exception
	{
		pollDetails = new PollDetails();
		pollDetails.setPollId(pollId);
		pollDetails.setQuestion(question);
		pollDetails.setStart(start);
		pollDetails.setDuration(duration);
		pollDetails.setOwnerId(ownerId);
		pollDetails.setCreatorId(creatorId);

		pollCreatedEvent = new PollCreatedEvent(pollDetails);
		assertNotNull("pollCreatedEvent is null", pollCreatedEvent);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void testPollCreatedEventLong() throws Exception
	{
		pollCreatedEvent = new PollCreatedEvent(pollId);
		assertNotNull("pollCreatedEvent null.", pollCreatedEvent);
		assertNull("pollDetails does not match", pollCreatedEvent.getDetails());
		assertEquals(pollCreatedEvent.getFailedNodeId(),pollId);
	}

	@Test
	public void testPollCreatedEventDetails() throws Exception
	{
		assertNotNull("pollCreatedEvent null.", pollCreatedEvent);
		assertNull("failedNodeId not null.", pollCreatedEvent.getFailedNodeId());
		assertEquals("pollDetails does not match", pollDetails,
				pollCreatedEvent.getDetails());
	}

	@Test
	public void testGetPollDetails() throws Exception
	{
		assertEquals("pollDetails does not match", pollDetails,
				pollCreatedEvent.getDetails());
	}

	@Test
	public void testSetPollDetails() throws Exception
	{
		PollDetails pollDetails1 = new PollDetails();
		pollDetails1.setPollId(pollId);
		pollCreatedEvent.setDetails(pollDetails1);
		assertEquals("PollDetails does not match", pollDetails1,
				pollCreatedEvent.getDetails());
	}

	@Test
	public void testGetFailedNodeId() throws Exception
	{
		pollCreatedEvent = new PollCreatedEvent(pollId);
		assertEquals("pollId does not match", pollId,
				pollCreatedEvent.getFailedNodeId());
	}
	
	@Test
	public void testIsCreatorFound() throws Exception
	{
		assertTrue(pollCreatedEvent.isCreatorFound());
	}
	
	@Test
	public void testSetCreatorFound() throws Exception
	{
		assertTrue(pollCreatedEvent.isCreatorFound());
		pollCreatedEvent.setCreatorFound(false);
		assertFalse(pollCreatedEvent.isCreatorFound());
		pollCreatedEvent.setCreatorFound(true);
		assertTrue(pollCreatedEvent.isCreatorFound());
	}
	
	@Test
	public void testIsOwnerFound() throws Exception
	{
		assertTrue(pollCreatedEvent.isOwnerFound());
	}
	
	@Test
	public void testSetOwnerFound() throws Exception
	{
		assertTrue(pollCreatedEvent.isOwnerFound());
		pollCreatedEvent.setOwnerFound(false);
		assertFalse(pollCreatedEvent.isOwnerFound());
		pollCreatedEvent.setOwnerFound(true);
		assertTrue(pollCreatedEvent.isOwnerFound());
	}
	
	@Test
	public void testCreatorNotFound() throws Exception
	{
		pollCreatedEvent = PollCreatedEvent.creatorNotFound(creatorId);
		assertFalse(pollCreatedEvent.isCreatorFound());
		assertNull(pollCreatedEvent.getDetails());
		assertEquals(pollCreatedEvent.getFailedNodeId(),creatorId);
	}
	
	@Test
	public void testOwnerNotFound() throws Exception
	{
		pollCreatedEvent = PollCreatedEvent.ownerNotFound(ownerId);
		assertFalse(pollCreatedEvent.isOwnerFound());
		assertNull(pollCreatedEvent.getDetails());
		assertEquals(pollCreatedEvent.getFailedNodeId(),ownerId);
	}
}