package com.eulersbridge.iEngage.core.events.polls;

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

        pollUpdatedEvent = new PollUpdatedEvent(pollId, pollDetails);
        assertNotNull("pollUpdatedEvent is null", pollUpdatedEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollId() throws Exception {
        assertEquals("pollId does not match", pollId, pollUpdatedEvent.getPollId());
    }

    @Test
    public void testGetPollDetails() throws Exception {
        assertEquals("pollDetails does not match", pollDetails, pollUpdatedEvent.getPollDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        PollUpdatedEvent pollUpdatedEvent1 = PollUpdatedEvent.notFound(pollId);
        assertNotNull("notFound() returns null", pollUpdatedEvent1);
        assertFalse("entityFound is not false", pollUpdatedEvent1.isEntityFound());
    }
}