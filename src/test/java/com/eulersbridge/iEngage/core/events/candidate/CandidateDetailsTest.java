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

public class CandidateDetailsTest
{
    CandidateDetails candidateDetails = null;
    Long candidateId = new Long(1);
    String candidateEmail = "test@test.com";
    String information = "info";
    String policyStatement = "policy";
    Set<String> pictures = new HashSet<>();
    Long userId = 1414l;
    Long positionId = 4141l;

    @Before
    public void setUp() throws Exception {
        candidateDetails = new CandidateDetails();
        candidateDetails.setNodeId(candidateId);
        candidateDetails.setInformation(information);
        candidateDetails.setPolicyStatement(policyStatement);
        pictures.add("http://url");
        candidateDetails.setPictures(pictures);
        candidateDetails.setUserId(userId);
        candidateDetails.setPositionId(positionId);
        assertNotNull("candidateDetails is null", candidateDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("toString() returns null", candidateDetails.toString());
    }

    @Test
    public void testEquals() throws Exception {
        CandidateDetails candidateDetails1 = new CandidateDetails();
        candidateDetails1.setNodeId(new Long(1));
        assertEquals("candidateDetails does not match", candidateDetails1, candidateDetails);
    }

    @Test
    public void testGetInformation() throws Exception {
        assertEquals("information does not match", information, candidateDetails.getInformation());
    }

    @Test
    public void testSetInformation() throws Exception {
        CandidateDetails candidateDetails1 = new CandidateDetails();
        candidateDetails1.setInformation(information);
        assertEquals("information does not match", information, candidateDetails1.getInformation());
    }

    @Test
    public void testGetPolicyStatement() throws Exception {
        assertEquals("policy does not match", policyStatement, candidateDetails.getPolicyStatement());
    }

    @Test
    public void testSetPolicyStatement() throws Exception {
        CandidateDetails candidateDetails1 = new CandidateDetails();
        candidateDetails1.setPolicyStatement(policyStatement);
        assertEquals("policy does not match", policyStatement, candidateDetails1.getPolicyStatement());
    }

    @Test
    public void testGetPictures() throws Exception {
        assertEquals("pictures does not match", pictures, candidateDetails.getPictures());
    }

    @Test
    public void testSetPictures() throws Exception {
        CandidateDetails candidateDetails1 = new CandidateDetails();
        candidateDetails1.setPictures(pictures);
        assertEquals("pictures does not match", pictures, candidateDetails1.getPictures());
    }

    @Test
    public void testGetUserId() throws Exception
    {
        assertEquals("familyname does not match", userId, candidateDetails.getUserId());
    }

    @Test
    public void testSetUserId() throws Exception {
        CandidateDetails candidateDetails1 = new CandidateDetails();
        candidateDetails1.setUserId(userId);
        assertEquals("familyname does not match", userId, candidateDetails1.getUserId());
    }

    @Test
    public void testGetPositionId() throws Exception {
        assertEquals("givenname does not match", positionId, candidateDetails.getPositionId());
    }

    @Test
    public void testSetPositionId() throws Exception {
        CandidateDetails candidateDetails1 = new CandidateDetails();
        candidateDetails1.setPositionId(positionId);
        assertEquals("givenname does not match", positionId, candidateDetails1.getPositionId());
    }
}