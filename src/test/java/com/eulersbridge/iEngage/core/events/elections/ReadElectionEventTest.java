package com.eulersbridge.iEngage.core.events.elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.ReadEvent;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadElectionEventTest {
    ReadElectionEvent readElectionEvent;
    ElectionDetails electionDetails;
    final Long electionId = new Long(0);
    final String title = "election title";
    final Long start = new Long(1);
    final Long end = new Long(4);
    final Long startVoting = new Long(2);
    final Long endVoting = new Long(3);


    @Before
    public void setUp() throws Exception {
        electionDetails = new ElectionDetails();
        electionDetails.setElectionId(electionId);
        electionDetails.setTitle(title);
        electionDetails.setStart(start);
        electionDetails.setEnd(end);
        electionDetails.setStartVoting(startVoting);
        electionDetails.setEndVoting(endVoting);
        readElectionEvent = new ReadElectionEvent(electionId, electionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadElectionEvent() throws Exception {
        assertNotNull("readElectionEvent is null", readElectionEvent);
        ReadElectionEvent readElectionEvent1 = new ReadElectionEvent(electionId);
        assertNotNull("readElectionEvent is null", readElectionEvent1);
    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("electionId does not match", electionId, readElectionEvent.getElectionId());
    }

    @Test
    public void testGetElectionDetail() throws Exception {
        assertEquals("electionDetail does not match", electionDetails, readElectionEvent.getElectionDetails());
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setElectionId(electionId);
        assertEquals("electionDetail does not match", electionDetails1, readElectionEvent.getElectionDetails());

    }

    @Test
    public void testNotFound() throws Exception {
        ReadEvent readElectionEvent1 = ReadElectionEvent.notFound(electionId);
        assertNotNull("readElectionEvent is null", readElectionEvent1);
        assertFalse("entityFound is not false", readElectionEvent1.isEntityFound());
    }
}