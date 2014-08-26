package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CreatedEventTest {
    CreatedEvent createdEvent = null;

    @Before
    public void setUp() throws Exception {
        createdEvent = new CreatedEvent();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreatedEvent() throws Exception {
        assertNotNull("createdEvent is null", createdEvent);
    }
}