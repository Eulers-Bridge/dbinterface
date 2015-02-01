package com.eulersbridge.iEngage.core.events.ticket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class TicketDeletedEventTest {
    Long ticketId = new Long(11);
    TicketDeletedEvent ticketDeletedEvent;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception{
        ticketDeletedEvent = new TicketDeletedEvent(ticketId);
        assertNotNull("constructor returns null", ticketDeletedEvent);
    }
}