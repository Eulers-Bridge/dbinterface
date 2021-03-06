package com.eulersbridge.iEngage.core.events.elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class RequestReadElectionDomainEventTest {
    RequestReadElectionEvent requestReadElectionEvent;
    final Long electionId = new Long(0);

    @Before
    public void setUp() throws Exception {
        requestReadElectionEvent = new RequestReadElectionEvent(electionId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestReadElectionEvent() throws Exception {
        assertNotNull("requestReadElectionEvent is null", requestReadElectionEvent);
    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("electionId does not match", electionId, requestReadElectionEvent.getNodeId());
    }
}