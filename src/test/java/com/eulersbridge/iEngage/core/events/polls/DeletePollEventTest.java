package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class DeletePollEventTest {
    final Long pollId = new Long(0);
    DeletePollEvent deletePollEvent;

    @Before
    public void setUp() throws Exception {
        deletePollEvent = new DeletePollEvent(pollId);
        assertNotNull("deletePollEvent is null", deletePollEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPollId() throws Exception {
        assertEquals("pollId does not match", pollId, deletePollEvent.getNodeId());
    }
}