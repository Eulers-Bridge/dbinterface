package com.eulersbridge.iEngage.core.events;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class LikedEventTest {
    final String userEmail = new String("yikaig@gmail.com");
    final Long nodeId=3245l;
    LikedEvent likedEvent = null;

    @Before
    public void setUp() throws Exception
    {
        likedEvent = new LikedEvent(nodeId,userEmail,true);
    }

    @Test
    public void testLikedEvent1() throws Exception
    {
        assertNotNull("likedEvent is null", likedEvent);
        assertEquals(nodeId,likedEvent.getNodeId());
        assertEquals(userEmail,likedEvent.getUserEmail());
        assertTrue(likedEvent.isResultSuccess());
    }

    @Test
    public void testLikedEvent2() throws Exception
    {
    	likedEvent= new LikedEvent(nodeId,userEmail);
        assertNotNull("likedEvent is null", likedEvent);
        assertEquals(nodeId,likedEvent.getNodeId());
        assertEquals(userEmail,likedEvent.getUserEmail());
        assertFalse(likedEvent.isResultSuccess());
    }

    @Test
    public void testLikedEvent3() throws Exception
    {
    	likedEvent= new LikedEvent(userEmail);
        assertNotNull("likedEvent is null", likedEvent);
        assertNull(likedEvent.getNodeId());
        assertEquals(userEmail,likedEvent.getUserEmail());
        assertFalse(likedEvent.isResultSuccess());
    }

    @Test
    public void testIsEntityFound() throws Exception
    {
        assertTrue("entityFound is not true", likedEvent.isEntityFound());
    }

    @Test
    public void testIsUserFound() throws Exception
    {
        assertTrue("userFound is not true", likedEvent.isUserFound());
    }

    @Test
    public void testIsResultSuccess() throws Exception
    {
        assertTrue("result is not true", likedEvent.isResultSuccess());
    }
    
    @Test
    public void testEntityNotFound()
    {
    	likedEvent=LikedEvent.entityNotFound(nodeId, userEmail);
        assertEquals(nodeId,likedEvent.getNodeId());
        assertEquals(userEmail,likedEvent.getUserEmail());
        assertFalse(likedEvent.isResultSuccess());
        assertFalse(likedEvent.isEntityFound());
        assertTrue(likedEvent.isUserFound());
    }
    @Test
    public void testUserNotFound()
    {
    	likedEvent=LikedEvent.userNotFound(nodeId, userEmail);
        assertEquals(nodeId,likedEvent.getNodeId());
        assertEquals(userEmail,likedEvent.getUserEmail());
        assertFalse(likedEvent.isResultSuccess());
        assertTrue(likedEvent.isEntityFound());
        assertFalse(likedEvent.isUserFound());
    }
}