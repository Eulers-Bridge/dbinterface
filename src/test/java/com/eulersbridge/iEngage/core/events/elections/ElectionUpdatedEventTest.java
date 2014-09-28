package com.eulersbridge.iEngage.core.events.elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ElectionUpdatedEventTest {
    ElectionDetails electionDetails;
    final Long electionId = new Long(0);
    final String title = "election title";
    final Long start = new Long(1);
    final Long end = new Long(4);
    final Long startVoting = new Long(2);
    final Long endVoting = new Long(3);
    ElectionUpdatedEvent electionUpdatedEvent;

    @Before
    public void setUp() throws Exception {
        electionDetails = new ElectionDetails();
        electionDetails.setElectionId(electionId);
        electionDetails.setTitle(title);
        electionDetails.setStart(start);
        electionDetails.setEnd(end);
        electionDetails.setStartVoting(startVoting);
        electionDetails.setEndVoting(endVoting);
        electionUpdatedEvent = new ElectionUpdatedEvent(electionId, electionDetails);
        assertNotNull("electionUpdatedEvent is null", electionUpdatedEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("electionId does not match", electionId, electionUpdatedEvent.getElectionId());
    }

    @Test
    public void testGetElectionDetails() throws Exception {
        assertEquals("electionDetails does not match", electionDetails, electionUpdatedEvent.getElectionDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        ElectionUpdatedEvent electionUpdatedEvent1 = ElectionUpdatedEvent.notFound(new Long(2));
        assertNotNull("notFound() returns null", electionUpdatedEvent1);
        assertFalse("entityFound is not false", electionUpdatedEvent1.isEntityFound());
    }
}