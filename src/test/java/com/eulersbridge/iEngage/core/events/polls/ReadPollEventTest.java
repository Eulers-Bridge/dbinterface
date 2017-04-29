package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.ReadEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadPollEventTest {
    final Long pollId = new Long(0);
    final String question = "a question";
    final String answers = "some answers";
    final Long start = new Long(1);
    final Long duration = new Long(2);
    PollDetails pollDetails;

    ReadPollEvent readPollEvent;

    @Before
    public void setUp() throws Exception {
        pollDetails = new PollDetails();
        pollDetails.setPollId(pollId);
        pollDetails.setQuestion(question);
        pollDetails.setAnswers(answers);
        pollDetails.setStart(start);
        pollDetails.setDuration(duration);

        readPollEvent = new ReadPollEvent(pollId, pollDetails);
        assertNotNull("readPollEvent is null", readPollEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollId() throws Exception {
        assertEquals("pollId does not match", pollId, readPollEvent.getNodeId());
    }

    @Test
    public void testGetPollDetails() throws Exception {
        assertEquals("pollDetails does not match", pollDetails, readPollEvent.getDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        ReadEvent readPollEvent1 = ReadPollEvent.notFound(new Long(1));
        assertNotNull("notFound() returns null", readPollEvent1);
        assertFalse("entityFound is not null", readPollEvent1.isEntityFound());
    }
}