package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.rest.domain.Candidate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CandidateDeletedEventTest {
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