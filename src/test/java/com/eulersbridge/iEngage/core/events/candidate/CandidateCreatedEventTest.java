package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CandidateCreatedEventTest {
    CandidateDetails candidateDetails;
    CandidateCreatedEvent candidateCreatedEvent;

    @Before
    public void setUp() throws Exception {
        candidateDetails = DatabaseDataFixture.populateCandidate1().toCandidateDetails();
        candidateCreatedEvent = new CandidateCreatedEvent(candidateDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCandidateId() throws Exception {
        assertNotNull("candidateCreatedEvent is null", candidateCreatedEvent);
        assertEquals("id does not match", candidateDetails.getNodeId(), candidateCreatedEvent.getNodeId());
    }

    @Test
    public void testSetCandidateId() throws Exception {
        assertNotNull("candidateCreatedEvent is null", candidateCreatedEvent);
        candidateCreatedEvent.setNodeId(new Long(10));
        assertEquals("id does not match", new Long(10), candidateCreatedEvent.getNodeId());
    }
}