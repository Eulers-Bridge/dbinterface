package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class RequestReadCandidateEventTest {
    RequestReadCandidateEvent requestReadCandidateEvent = null;
    Long candidateId = new Long(0);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        requestReadCandidateEvent = new RequestReadCandidateEvent(candidateId);
        assertNotNull("requestReadCandidateEvent is null", requestReadCandidateEvent);

    }
}