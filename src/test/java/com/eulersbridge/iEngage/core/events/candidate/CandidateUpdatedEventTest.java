package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CandidateUpdatedEventTest
{
    CandidateDetails candidateDetails;
    CandidateUpdatedEvent candidateUpdatedEvent;

    @Before
    public void setUp() throws Exception
    {
        candidateDetails = DatabaseDataFixture.populateCandidate1().toCandidateDetails();
        candidateUpdatedEvent = new CandidateUpdatedEvent(candidateDetails.getNodeId(),candidateDetails);
    }

    @Test
    public void testCandidateUpdatedLong() throws Exception
    {
        assertNotNull("candidateDetails is null", candidateDetails);
        candidateUpdatedEvent = new CandidateUpdatedEvent(candidateDetails.getNodeId());
        assertNotNull("candidateUpdatedEvent is null", candidateUpdatedEvent);
    }
    
    @Test
    public void testCandidateUpdatedLongCandidateDetails() throws Exception
    {
        assertNotNull("candidateDetails is null", candidateDetails);
        assertNotNull("candidateUpdatedEvent is null", candidateUpdatedEvent);
    }
}