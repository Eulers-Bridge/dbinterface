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

public class CandidateUpdatedEventTest {
    CandidateDetails candidateDetails = null;
    Long candidateId = new Long(1);
    String candidateEmail = "test@test.com";
    String information = "info";
    String policyStatement = "policy";
    Set<String> pictures = new HashSet<>();
    String familyName = "Gong";
    String givenName = "Yikai";
    CandidateUpdatedEvent candidateUpdatedEvent = null;

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
        assertNotNull("candidateDetails is null", candidateDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        candidateUpdatedEvent = new CandidateUpdatedEvent(candidateId);
        assertNotNull("candidateUpdatedEvent is null", candidateUpdatedEvent);
        candidateUpdatedEvent = new CandidateUpdatedEvent(candidateId, candidateDetails);
        assertNotNull("candidateUpdatedEvent is null", candidateUpdatedEvent);
    }
}