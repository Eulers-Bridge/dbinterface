package com.eulersbridge.iEngage.core.events.ticket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class RequestReadTicketEventTest {
    Long ticketId = new Long(11);
    RequestReadTicketEvent requestReadTicketEvent;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception{
        requestReadTicketEvent = new RequestReadTicketEvent(ticketId);
        assertNotNull("constructor returns null", requestReadTicketEvent);
    }
}