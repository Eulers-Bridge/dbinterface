package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CreateCandidateEventTest
{
    CreateCandidateEvent createCandidateEvent;
    CandidateDetails candidateDetails;

    @Before
    public void setUp() throws Exception
    {
        candidateDetails = DatabaseDataFixture.populateCandidate1().toCandidateDetails();
    }

    @Test
    public void testConstructor() throws Exception
    {
        assertNotNull("candidateDetails is null", candidateDetails);
        createCandidateEvent = new CreateCandidateEvent(candidateDetails);
        assertNotNull("createCandidateEvent is null", createCandidateEvent);
    }
}