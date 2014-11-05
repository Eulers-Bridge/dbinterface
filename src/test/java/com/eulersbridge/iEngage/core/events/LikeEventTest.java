package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class LikeEventTest {
    final String emailAddress = new String("yikaig@gmail.com");
    Long nodeId=23l;
    LikeEvent likeEvent = null;

    @Before
    public void setUp() throws Exception
    {
        likeEvent = new LikeEvent(nodeId, emailAddress);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLikeEvent() throws Exception {
        assertNotNull("likeEvent is null", likeEvent);
    }

    @Test
    public void testGetEmailAddress() throws Exception {
        assertEquals("emailAddress does not match", emailAddress, likeEvent.getEmailAddress());
    }

    @Test
    public void testSetEmailAddress() throws Exception {
        String emailAddress1 = new String("test@gmail.com");
        likeEvent.setEmailAddress(emailAddress1);
        assertEquals("emailAddress does not match", emailAddress1, likeEvent.getEmailAddress());
    }
    @Test
    public void testGetNodeId() throws Exception
    {
        assertEquals("emailAddress does not match", nodeId, likeEvent.getNodeId());
    }

    @Test
    public void testSetNodeId() throws Exception {
        Long nodeId1 = 45l;
        assertNotEquals("emailAddress does not match", nodeId1, likeEvent.getNodeId());
        likeEvent.setNodeId(nodeId1);
        assertEquals("emailAddress does not match", nodeId1, likeEvent.getNodeId());
    }
}