package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadEventTest {
    ReadEvent readEvent = null;

    @Before
    public void setUp() throws Exception {
        readEvent = new ReadEvent();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadEvent() throws Exception {
        assertNotNull("readEvent is null");
    }

    @Test
    public void testIsEntityFound() throws Exception {
        assertTrue("entityFound is not true", readEvent.isEntityFound());
    }
}