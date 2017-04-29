package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class DeleteCandidateEventTest {
    DeleteCandidateEvent deleteCandidateEvent = null;
    Long candidateId = new Long(0);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        deleteCandidateEvent = new DeleteCandidateEvent(candidateId);
        assertNotNull("deleteCandidateEvent is null", deleteCandidateEvent);

    }
}