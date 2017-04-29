package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class RequestReadPollEventTest {
    final Long pollId = new Long(0);
    RequestReadPollEvent requestReadPollEvent;

    @Before
    public void setUp() throws Exception {
        requestReadPollEvent = new RequestReadPollEvent(pollId);

        assertNotNull("requestReadPollEvent is null", requestReadPollEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollId() throws Exception {
        assertEquals("pollId does not match", pollId, requestReadPollEvent.getNodeId());
    }
}