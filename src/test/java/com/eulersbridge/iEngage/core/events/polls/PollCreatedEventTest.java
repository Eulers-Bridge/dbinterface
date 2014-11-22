package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class PollCreatedEventTest {
    final Long pollId = new Long(0);
    final String question = "a question";
    final String answers = "some answers";
    final Long start = new Long(1);
    final Long duration = new Long(2);
    PollDetails pollDetails;

    PollCreatedEvent pollCreatedEvent;

    @Before
    public void setUp() throws Exception {
        pollDetails = new PollDetails();
        pollDetails.setPollId(pollId);
        pollDetails.setQuestion(question);
        pollDetails.setAnswers(answers);
        pollDetails.setStart(start);
        pollDetails.setDuration(duration);

        pollCreatedEvent = new PollCreatedEvent(pollId, pollDetails);
        assertNotNull("pollCreatedEvent is null", pollCreatedEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollDetails() throws Exception {
        assertEquals("pollDetails does not match", pollDetails, pollCreatedEvent.getDetails());
    }

    @Test
    public void testSetPollDetails() throws Exception {
        PollDetails pollDetails1 = new PollDetails();
        pollDetails1.setPollId(pollId);
        pollCreatedEvent.setDetails(pollDetails1);
        assertEquals("PollDetails does not match", pollDetails1, pollCreatedEvent.getDetails());
    }

    @Test
    public void testGetPollId() throws Exception {
        assertEquals("pollId does not match", pollId, pollCreatedEvent.getPollId());
    }

    @Test
    public void testSetPollId() throws Exception {
        Long newId = new Long(3);
        pollCreatedEvent.setPollId(newId);
        assertEquals("pollId does not match", newId, pollCreatedEvent.getPollId());
    }
}