package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CreatePollEventTest {
    final Long pollId = new Long(0);
    final String question = "a question";
    final String answers = "some answers";
    final Long start = new Long(1);
    final Long duration = new Long(2);
    PollDetails pollDetails;

    CreatePollEvent createPollEvent;

    @Before
    public void setUp() throws Exception {
        pollDetails = new PollDetails();
        pollDetails.setPollId(pollId);
        pollDetails.setQuestion(question);
        pollDetails.setAnswers(answers);
        pollDetails.setStart(start);
        pollDetails.setDuration(duration);

        createPollEvent = new CreatePollEvent(pollDetails);
        assertNotNull("createPollEvent is null", createPollEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollDetails() throws Exception {
        assertEquals("pollDetails does not match", pollDetails, createPollEvent.getPollDetails());
    }

    @Test
    public void testSetPollDetails() throws Exception {
        PollDetails pollDetails1 = new PollDetails();
        pollDetails1.setPollId(pollId);
        CreatePollEvent createPollEvent1 = new CreatePollEvent(pollDetails1);
        assertEquals("pollDetails does not match", pollDetails, createPollEvent1.getPollDetails());
    }
}