package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class LikedEventTest {
    final String userEmail = new String("yikaig@gmail.com");
    LikedEvent likedEvent = null;

    @Before
    public void setUp() throws Exception {
        likedEvent = new LikedEvent();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLikedEvent() throws Exception {
        assertNotNull("likedEvent is null", likedEvent);
    }

    @Test
    public void testIsEntityFound() throws Exception {
        assertTrue("entityFound is not true", likedEvent.isEntityFound());
    }

    @Test
    public void testIsUserFound() throws Exception {
        assertTrue("userFound is not true", likedEvent.isUserFound());
    }

    @Test
    public void testIsResultSuccess() throws Exception {
        assertTrue("result is not true", likedEvent.isResultSuccess());
    }
}