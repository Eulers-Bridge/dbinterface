package com.eulersbridge.iEngage.core.events.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class EventDeletedEventTest {
    private Long eventId = new Long(0);
    EventDeletedEvent eventDeletedEvent;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception{
        eventDeletedEvent = new EventDeletedEvent(eventId);
        assertNotNull("eventDeletedEvent is null", eventDeletedEvent);
    }
}