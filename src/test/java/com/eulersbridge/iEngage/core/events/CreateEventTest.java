package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CreateEventTest {
    CreateEvent createEvent = null;

    @Before
    public void setUp() throws Exception {
        createEvent = new CreateEvent();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateEvent() throws Exception {
        assertNotNull("createEvent is null", createEvent);
    }
}