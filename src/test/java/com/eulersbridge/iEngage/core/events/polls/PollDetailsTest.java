package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class PollDetailsTest {
    final Long pollId = new Long(0);
    final String question = "Who was the best Prime Minister?";
    final String answers = "John Howard, Gough Whitlam, John Curtin, Robert Menzies, Paul Keating, Bob Hawke";
    final Long start = new Long(1);
    final Long duration = new Long(2);
    PollDetails pollDetails;

    @Before
    public void setUp() throws Exception {
        pollDetails = new PollDetails();
        assertNotNull("pollDetails is null", pollDetails);
        pollDetails.setPollId(pollId);
        pollDetails.setQuestion(question);
        pollDetails.setAnswers(answers);
        pollDetails.setStart(start);
        pollDetails.setDuration(duration);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollId() throws Exception {
        assertEquals("pollId does not match", pollId, pollDetails.getPollId());
    }

    @Test
    public void testSetPollId() throws Exception {
        PollDetails pollDetails1 = new PollDetails();
        pollDetails1.setPollId(new Long(1));
        assertEquals("pollId does not match", new Long(1), pollDetails1.getPollId());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("title does not match", question, pollDetails.getQuestion());
    }

    @Test
    public void testSetTitle() throws Exception {
        PollDetails pollDetails1 = new PollDetails();
        String newQuestion="Who was the worst Prime Minister?";
        pollDetails1.setQuestion(newQuestion);
        assertEquals("title does not match", newQuestion, pollDetails1.getQuestion());
    }

    @Test
    public void testGetAnswers() throws Exception {
        assertEquals("ElectionStart does not match", answers, pollDetails.getAnswers());
    }

    @Test
    public void testSetAnswers() throws Exception {
        PollDetails pollDetails1 = new PollDetails();
        String newAnswers="Harold Holt, Billy Hughes, Robert Menzies, McEwen";
        pollDetails1.setAnswers(newAnswers);
        assertEquals("Answers do not match", newAnswers, pollDetails1.getAnswers());
    }

    @Test
    public void testGetStart() throws Exception {
        assertEquals("start does not match", start, pollDetails.getStart());
    }

    @Test
    public void testSetStart() throws Exception {
        PollDetails pollDetails1 = new PollDetails();
        pollDetails1.setStart(new Long(5));
        assertEquals("start does not match", new Long(5), pollDetails1.getStart());
    }

    @Test
    public void testGetDuration() throws Exception {
        assertEquals("duration does not match", duration, pollDetails.getDuration());
    }

    @Test
    public void testSetDuration() throws Exception {
        PollDetails pollDetails1 = new PollDetails();
        pollDetails1.setDuration(new Long(2));
        assertEquals("duration does not match", new Long(2), pollDetails1.getDuration());
    }

    @Test
    public void testToString() throws Exception {
        String str = pollDetails.toString();
        assertNotNull("toString() returns null", str);
    }

    @Test
    public void testEquals() throws Exception {
        PollDetails pollDetails1 = new PollDetails();
        pollDetails1.setPollId(new Long(0));
        assertEquals("pollDetails does not match", pollDetails1, pollDetails);
    }
}