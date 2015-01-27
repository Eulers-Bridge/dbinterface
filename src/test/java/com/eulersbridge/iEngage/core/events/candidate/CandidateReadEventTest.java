package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CandidateReadEventTest {
    CandidateReadEvent candidateReadEvent;
    CandidateDetails candidateDetails;

    @Before
    public void setUp() throws Exception {
        candidateDetails = DatabaseDataFixture.populateCandidate1().toCandidateDetails();
    }

    @Test
    public void testConstructor() throws Exception {
        assertNotNull("candidateDetails is null", candidateDetails);
        candidateReadEvent = new CandidateReadEvent(candidateDetails.getNodeId());
        assertNotNull("candidateReadEvent is null", candidateReadEvent);
        candidateReadEvent = new CandidateReadEvent(candidateDetails.getNodeId(), candidateDetails);
        assertNotNull("candidateReadEvent is null", candidateReadEvent);
    }
}