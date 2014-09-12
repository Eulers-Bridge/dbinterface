package com.eulersbridge.iEngage.core.events.Elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadElectionEventTest {
    ReadElectionEvent readElectionEvent;
    ElectionDetail electionDetail;
    final Long electionId = new Long(0);
    final String title = "election title";
    final Long start = new Long(1);
    final Long end = new Long(4);
    final Long startVoting = new Long(2);
    final Long endVoting = new Long(3);


    @Before
    public void setUp() throws Exception {
        electionDetail = new ElectionDetail();
        electionDetail.setElectionId(electionId);
        electionDetail.setTitle(title);
        electionDetail.setStart(start);
        electionDetail.setEnd(end);
        electionDetail.setStartVoting(startVoting);
        electionDetail.setEndVoting(endVoting);
        readElectionEvent = new ReadElectionEvent(electionId, electionDetail);
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
        assertEquals("electionDetail does not match", electionDetail, readElectionEvent.getElectionDetail());
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setElectionId(electionId);
        assertEquals("electionDetail does not match", electionDetail1, readElectionEvent.getElectionDetail());

    }

    @Test
    public void testNotFound() throws Exception {
        ReadElectionEvent readElectionEvent1 = ReadElectionEvent.notFound(electionId);
        assertNotNull("readElectionEvent is null", readElectionEvent1);
        assertFalse("entityFound is not false", readElectionEvent1.isEntityFound());
    }
}