package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UpdatedEventTest {
    UpdatedEvent updatedEvent = null;

    @Before
    public void setUp() throws Exception {
        updatedEvent = new UpdatedEvent();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUpdatedEventTest() throws Exception {
        assertNotNull("updatedEvent is null" , updatedEvent);
    }
}