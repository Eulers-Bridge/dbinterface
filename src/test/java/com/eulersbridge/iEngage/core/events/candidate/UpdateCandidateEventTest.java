package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UpdateCandidateEventTest {
    UpdateCandidateEvent updateCandidateEvent;
    CandidateDetails candidateDetails;

    @Before
    public void setUp() throws Exception {
        candidateDetails = DatabaseDataFixture.populateCandidate1().toCandidateDetails();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception
    {
        assertNotNull("candidateDetails is null", candidateDetails);
        updateCandidateEvent = new UpdateCandidateEvent(candidateDetails.getNodeId(), candidateDetails);
        assertNotNull("updateCandidateEvent is null", updateCandidateEvent);
    }
}