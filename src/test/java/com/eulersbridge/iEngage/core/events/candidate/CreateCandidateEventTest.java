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

public class CreateCandidateEventTest {
    CreateCandidateEvent createCandidateEvent = null;
    CandidateDetails candidateDetails = null;
    Long candidateId = new Long(1);
    String candidateEmail = "test@test.com";
    String information = "info";
    String policyStatement = "policy";
    Set<String> pictures = new HashSet<>();
    String familyName = "Gong";
    String givenName = "Yikai";

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
        createCandidateEvent = new CreateCandidateEvent(candidateDetails);
        assertNotNull("createCandidateEvent is null", createCandidateEvent);
    }
}