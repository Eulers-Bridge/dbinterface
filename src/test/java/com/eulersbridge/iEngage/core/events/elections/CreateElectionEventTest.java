package com.eulersbridge.iEngage.core.events.elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CreateElectionEventTest {
    ElectionDetails electionDetails;
    final Long electionId = new Long(0);
    final String title = "election title";
    final Long start = new Long(1);
    final Long end = new Long(4);
    final Long startVoting = new Long(2);
    final Long endVoting = new Long(3);
    CreateElectionEvent createElectionEvent;
    @Before
    public void setUp() throws Exception {
        electionDetails = new ElectionDetails();
        electionDetails.setElectionId(electionId);
        electionDetails.setTitle(title);
        electionDetails.setStart(start);
        electionDetails.setEnd(end);
        electionDetails.setStartVoting(startVoting);
        electionDetails.setEndVoting(endVoting);
        createElectionEvent = new CreateElectionEvent(electionId, electionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateElectionEvent() throws Exception {
        assertNotNull("createElectionEvent is null", createElectionEvent);
        CreateElectionEvent createElectionEvent1 = new CreateElectionEvent(electionDetails);
        assertNotNull("createElectionEvent is null", createElectionEvent1);
    }

    @Test
    public void testGetElectionDetails() throws Exception {
        assertEquals("election detail does not match", electionDetails, createElectionEvent.getElectionDetails());
    }

    @Test
    public void testSetElectionDetails() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setElectionId(new Long(4));
        electionDetails1.setTitle("new title");
        electionDetails1.setStart(start);
        electionDetails1.setEnd(end);
        electionDetails1.setStartVoting(startVoting);
        electionDetails1.setEndVoting(endVoting);

        createElectionEvent.setElectionDetails(electionDetails1);
        assertEquals("electionDetails does not match", electionDetails1, createElectionEvent.getElectionDetails());
    }
}