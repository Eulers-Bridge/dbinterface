package com.eulersbridge.iEngage.core.events.candidate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CandidateCreatedEventTest {
    CandidateDetails candidateDetails = null;
    Long candidateId = new Long(1);
    String candidateEmail = "test@test.com";
    String information = "info";
    String policyStatement = "policy";
    Set<String> pictures = new HashSet<>();
    String familyName = "Gong";
    String givenName = "Yikai";
    CandidateCreatedEvent candidateCreatedEvent = null;

    @Before
    public void setUp() throws Exception {
        candidateDetails = new CandidateDetails();
        candidateDetails.setNodeId(candidateId);
        candidateDetails.setCandidateEmail(candidateEmail);
        candidateDetails.setInformation(information);
        candidateDetails.setPolicyStatement(policyStatement);
        pictures.add("http://url");
        candidateDetails.setPictures(pictures);
        candidateDetails.setFamilyName(familyName);
        candidateDetails.setGivenName(givenName);
        candidateCreatedEvent = new CandidateCreatedEvent(candidateId, candidateDetails);
        assertNotNull("candidateCreatedEvent is null", candidateCreatedEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCandidateId() throws Exception {
        assertEquals("id does not match", candidateId, candidateCreatedEvent.getCandidateId());
    }

    @Test
    public void testSetCandidateId() throws Exception {
        candidateCreatedEvent.setCandidateId(new Long(10));
        assertEquals("id does not match", new Long(10), candidateCreatedEvent.getCandidateId());
    }
}