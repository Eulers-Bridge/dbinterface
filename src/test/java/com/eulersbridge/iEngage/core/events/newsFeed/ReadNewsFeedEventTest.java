package com.eulersbridge.iEngage.core.events.newsFeed;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class ReadNewsFeedEventTest {
    final Long nodeId = new Long(1);
    ReadNewsFeedEvent readNewsFeedEvent = null;
    @Before
    public void setUp() throws Exception {
        readNewsFeedEvent = new ReadNewsFeedEvent(nodeId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadNewsFeedEvent() throws Exception {
        assertNotNull("readNewsFeedEvent is null", readNewsFeedEvent);
    }

    @Test
    public void testGetNewsFeedId() throws Exception {
        assertEquals("NewsFeedId does not match", nodeId, readNewsFeedEvent.getNodeId());
    }
}