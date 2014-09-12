package com.eulersbridge.iEngage.core.events.Elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ElectionDetailTest {
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
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testElectionDetail() throws Exception{
        assertNotNull("electionDetail is null", electionDetail);
    }

    @Test
    public void testSetElectionId() throws Exception {
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setElectionId(electionId);
        assertEquals("electionId does not match", electionId, electionDetail1.getElectionId());
    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("electionId does not match", electionId, electionDetail.getElectionId());
    }

    @Test
    public void testSetTitle() throws Exception {
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setTitle(title);
        assertEquals("title does not match", title, electionDetail1.getTitle());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("title does not match", title, electionDetail.getTitle());
    }

    @Test
    public void testSetStart() throws Exception {
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setStart(start);
        assertEquals("start does not match", start, electionDetail1.getStart());
    }

    @Test
    public void testGetStart() throws Exception {
        assertEquals("start does not match", start, electionDetail.getStart());
    }

    @Test
    public void testSetEnd() throws Exception {
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setEnd(end);
        assertEquals("end does not match", end, electionDetail1.getEnd());
    }

    @Test
    public void testGetEnd() throws Exception {
        assertEquals("end does not match", end, electionDetail.getEnd());
    }

    @Test
    public void testSetStartVoting() throws Exception {
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setStartVoting(startVoting);
        assertEquals("startVoting does not match", startVoting, electionDetail1.getStartVoting());
    }

    @Test
    public void testGetStartVoting() throws Exception {
        assertEquals("startVoting does not match", startVoting, electionDetail.getStartVoting());
    }

    @Test
    public void testSetEndVoting() throws Exception {
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setEndVoting(endVoting);
        assertEquals("endVoting does not match", endVoting, electionDetail1.getEndVoting());
    }

    @Test
    public void testGetEndVoting() throws Exception {
        assertEquals("endVoting does not match", endVoting, electionDetail.getEndVoting());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("toSting is null", electionDetail.toString());
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setElectionId(electionId);
        electionDetail1.setTitle(title);
        electionDetail1.setStart(start);
        electionDetail1.setEnd(end);
        electionDetail1.setStartVoting(startVoting);
        electionDetail1.setEndVoting(endVoting);
        assertEquals("toString does not match", electionDetail1.toString(), electionDetail.toString());
    }

    @Test
    public void testEquals() throws Exception {
        ElectionDetail electionDetail1 = new ElectionDetail();
        electionDetail1.setElectionId(electionId);
        assertEquals("electionDetail does not match", electionDetail1, electionDetail);
    }
}