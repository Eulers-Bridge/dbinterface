package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UpdatePollEventTest {
    final Long pollId = new Long(0);
    final String title = "a title";
    final Long electionStart = new Long(4);
    final Long start = new Long(1);
    final Long duration = new Long(2);
    PollDetails pollDetails;

    UpdatePollEvent updatePollEvent;

    @Before
    public void setUp() throws Exception {
        pollDetails = new PollDetails();
        pollDetails.setPollId(pollId);
        pollDetails.setTitle(title);
        pollDetails.setElectionStart(electionStart);
        pollDetails.setStart(start);
        pollDetails.setDuration(duration);

        updatePollEvent = new UpdatePollEvent(pollId, pollDetails);
        assertNotNull("updatePollEvent is null", updatePollEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollId() throws Exception {
        assertEquals("pollId does not match", pollId, updatePollEvent.getPollId());
    }

    @Test
    public void testGetPollDetails() throws Exception {
        assertEquals("pollDetails does not match", pollDetails, updatePollEvent.getPollDetails());
    }
}