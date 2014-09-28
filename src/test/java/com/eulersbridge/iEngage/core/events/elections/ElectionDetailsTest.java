package com.eulersbridge.iEngage.core.events.elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ElectionDetailsTest {
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
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testElectionDetail() throws Exception{
        assertNotNull("electionDetail is null", electionDetails);
    }

    @Test
    public void testSetElectionId() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setElectionId(electionId);
        assertEquals("electionId does not match", electionId, electionDetails1.getElectionId());
    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("electionId does not match", electionId, electionDetails.getElectionId());
    }

    @Test
    public void testSetTitle() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setTitle(title);
        assertEquals("title does not match", title, electionDetails1.getTitle());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("title does not match", title, electionDetails.getTitle());
    }

    @Test
    public void testSetStart() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setStart(start);
        assertEquals("start does not match", start, electionDetails1.getStart());
    }

    @Test
    public void testGetStart() throws Exception {
        assertEquals("start does not match", start, electionDetails.getStart());
    }

    @Test
    public void testSetEnd() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setEnd(end);
        assertEquals("end does not match", end, electionDetails1.getEnd());
    }

    @Test
    public void testGetEnd() throws Exception {
        assertEquals("end does not match", end, electionDetails.getEnd());
    }

    @Test
    public void testSetStartVoting() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setStartVoting(startVoting);
        assertEquals("startVoting does not match", startVoting, electionDetails1.getStartVoting());
    }

    @Test
    public void testGetStartVoting() throws Exception {
        assertEquals("startVoting does not match", startVoting, electionDetails.getStartVoting());
    }

    @Test
    public void testSetEndVoting() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setEndVoting(endVoting);
        assertEquals("endVoting does not match", endVoting, electionDetails1.getEndVoting());
    }

    @Test
    public void testGetEndVoting() throws Exception {
        assertEquals("endVoting does not match", endVoting, electionDetails.getEndVoting());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("toSting is null", electionDetails.toString());
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setElectionId(electionId);
        electionDetails1.setTitle(title);
        electionDetails1.setStart(start);
        electionDetails1.setEnd(end);
        electionDetails1.setStartVoting(startVoting);
        electionDetails1.setEndVoting(endVoting);
        assertEquals("toString does not match", electionDetails1.toString(), electionDetails.toString());
    }

    @Test
    public void testEquals() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setElectionId(electionId);
        assertEquals("electionDetail does not match", electionDetails1, electionDetails);
    }
}