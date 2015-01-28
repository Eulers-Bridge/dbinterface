package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class RequestReadEventEventTest {
    private Long eventId = new Long(0);
    private RequestReadEvent requestReadEvent;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception{
        requestReadEvent = new RequestReadEventEvent(eventId);
        assertNotNull("requestReadEvent is null", requestReadEvent);
    }
}