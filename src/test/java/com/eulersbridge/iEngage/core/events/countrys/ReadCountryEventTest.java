package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class ReadCountryEventTest {
    final Long id = new Long(1);
    ReadCountryEvent readCountryEvent =null;

    @Before
    public void setUp() throws Exception {
        readCountryEvent = new ReadCountryEvent(id);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadCountryEvent() throws Exception {
        ReadCountryEvent readCountryEvent1 = new ReadCountryEvent(new Long(2));
        assertNotNull("ReadCountryEvent is null", readCountryEvent1);
    }

    @Test
    public void testGetNodeId() throws Exception {
        assertEquals("id does not match", id, readCountryEvent.getNodeId());
    }
}