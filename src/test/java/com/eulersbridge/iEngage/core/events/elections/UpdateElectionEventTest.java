package com.eulersbridge.iEngage.core.events.elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class UpdateElectionEventTest {
    ElectionDetails electionDetails;
    final Long electionId = new Long(0);
    final String title = "election title";
    final Long start = new Long(1);
    final Long end = new Long(4);
    final Long startVoting = new Long(2);
    final Long endVoting = new Long(3);
    UpdateElectionEvent updateElectionEvent;

    @Before
    public void setUp() throws Exception {
        electionDetails = new ElectionDetails();
        electionDetails.setElectionId(electionId);
        electionDetails.setTitle(title);
        electionDetails.setStart(start);
        electionDetails.setEnd(end);
        electionDetails.setStartVoting(startVoting);
        electionDetails.setEndVoting(endVoting);
        updateElectionEvent = new UpdateElectionEvent(electionId, electionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUpdateElectionEvent() throws Exception {
        assertNotNull("updateElectionEvent is null", updateElectionEvent);
    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("electionId does not match", electionId, updateElectionEvent.getElectionId());
    }

    @Test
    public void testGetElectionDetails() throws Exception {
        assertEquals("electionDetail does not match", electionDetails, updateElectionEvent.getElectionDetails());
    }
}