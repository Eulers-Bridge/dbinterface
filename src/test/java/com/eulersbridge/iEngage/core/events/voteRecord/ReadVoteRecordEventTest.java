package com.eulersbridge.iEngage.core.events.voteRecord;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */

public class ReadVoteRecordEventTest 
{
    final Long voteRecordId = new Long(0);
    ReadVoteRecordEvent ReadVoteRecordEvent = null;

    @Before
    public void setUp() throws Exception {
    	ReadVoteRecordEvent = new ReadVoteRecordEvent(voteRecordId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestReadNewsArticleEvent() throws Exception {
        assertNotNull("requestReadNewsArticleEvent is null", ReadVoteRecordEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception 
    {
        assertEquals("voteRecordId does not match", voteRecordId, ReadVoteRecordEvent.getNodeId());
    }
}