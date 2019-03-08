package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class CandidateDomainReadEventTest {
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