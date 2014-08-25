package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class RequestReadEventTest {
    RequestReadEvent requestReadEvent = null;

    @Before
    public void setUp() throws Exception {
        requestReadEvent = new RequestReadEvent();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestReadEvent() throws Exception {
        assertNotNull("requestReadEvent is null" , requestReadEvent);
    }
}