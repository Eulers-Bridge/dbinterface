package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class DeletedEventTest {
    DeletedEvent deletedEvent = null;

    @Before
    public void setUp() throws Exception {
        deletedEvent = new DeletedEvent();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDeletedEvent() throws Exception {
        assertNotNull("deletedEvent is null", deletedEvent);
    }

    @Test
    public void testIsEntityFound() throws Exception {
        assertTrue("entityFound is not true", deletedEvent.isEntityFound());
    }
}