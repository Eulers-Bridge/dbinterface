package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class CandidateDomainDeletedEventTest {
    CandidateDeletedEvent candidateDeletedEvent = null;
    Long candidateId = new Long(1);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        candidateDeletedEvent = new CandidateDeletedEvent(candidateId);
        assertNotNull("constructor returns null", candidateDeletedEvent);
    }
}