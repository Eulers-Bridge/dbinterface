package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class PollUpdatedEventTest {
    final Long pollId = new Long(0);
    final String question = "a question";
    final String answers = "some answers";
    final Long start = new Long(1);
    final Long duration = new Long(2);
	final Long ownerId = new Long(31);
	final Long creatorId = new Long(42);
    PollDetails pollDetails;

    PollUpdatedEvent pollUpdatedEvent;

    @Before
    public void setUp() throws Exception {
        pollDetails = new PollDetails();
        pollDetails.setPollId(pollId);
        pollDetails.setQuestion(question);
        pollDetails.setAnswers(answers);
        pollDetails.setStart(start);
        pollDetails.setDuration(duration);
        pollDetails.setOwnerId(ownerId);
        pollDetails.setCreatorId(creatorId);
        pollUpdatedEvent = new PollUpdatedEvent(pollId, pollDetails);
        assertNotNull("pollUpdatedEvent is null", pollUpdatedEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollId() throws Exception {
        assertEquals("pollId does not match", pollId, pollUpdatedEvent.getNodeId());
    }

    @Test
    public void testGetPollDetails() throws Exception {
        assertEquals("pollDetails does not match", pollDetails, pollUpdatedEvent.getDetails());
    }

	@Test
	public void testIsCreatorFound() throws Exception
	{
		assertTrue(pollUpdatedEvent.isCreatorFound());
	}
	
	@Test
	public void testSetCreatorFound() throws Exception
	{
		assertTrue(pollUpdatedEvent.isCreatorFound());
		pollUpdatedEvent.setCreatorFound(false);
		assertFalse(pollUpdatedEvent.isCreatorFound());
		pollUpdatedEvent.setCreatorFound(true);
		assertTrue(pollUpdatedEvent.isCreatorFound());
	}
	
	@Test
	public void testIsOwnerFound() throws Exception
	{
		assertTrue(pollUpdatedEvent.isOwnerFound());
	}
	
	@Test
	public void testSetOwnerFound() throws Exception
	{
		assertTrue(pollUpdatedEvent.isOwnerFound());
		pollUpdatedEvent.setOwnerFound(false);
		assertFalse(pollUpdatedEvent.isOwnerFound());
		pollUpdatedEvent.setOwnerFound(true);
		assertTrue(pollUpdatedEvent.isOwnerFound());
	}
	
    @Test
    public void testNotFound() throws Exception {
        UpdatedEvent pollUpdatedEvent1 = PollUpdatedEvent.notFound(pollId);
        assertNotNull("notFound() returns null", pollUpdatedEvent1);
        assertFalse("entityFound is not false", pollUpdatedEvent1.isEntityFound());
    }
    
	@Test
	public void testCreatorNotFound() throws Exception
	{
		pollUpdatedEvent = PollUpdatedEvent.creatorNotFound(creatorId);
		assertFalse(pollUpdatedEvent.isCreatorFound());
		assertFalse(pollUpdatedEvent.isEntityFound());
		assertNull(pollUpdatedEvent.getDetails());
		assertEquals(pollUpdatedEvent.getNodeId(),creatorId);
	}
	
	@Test
	public void testOwnerNotFound() throws Exception
	{
		pollUpdatedEvent = PollUpdatedEvent.ownerNotFound(ownerId);
		assertFalse(pollUpdatedEvent.isOwnerFound());
		assertFalse(pollUpdatedEvent.isEntityFound());
		assertNull(pollUpdatedEvent.getDetails());
		assertEquals(pollUpdatedEvent.getNodeId(),ownerId);
	}
	@Test
	public void testOwnerNotFoundNull() throws Exception
	{
		pollUpdatedEvent = PollUpdatedEvent.ownerNotFound(null);
		assertFalse(pollUpdatedEvent.isOwnerFound());
		assertFalse(pollUpdatedEvent.isEntityFound());
		assertNull(pollUpdatedEvent.getDetails());
		assertEquals(pollUpdatedEvent.getNodeId(),null);
	}
	@Test
	public void testCreatorNotFoundNull() throws Exception
	{
		pollUpdatedEvent = PollUpdatedEvent.creatorNotFound(null);
		assertFalse(pollUpdatedEvent.isCreatorFound());
		assertFalse(pollUpdatedEvent.isEntityFound());
		assertNull(pollUpdatedEvent.getDetails());
		assertEquals(pollUpdatedEvent.getNodeId(),null);
	}
}