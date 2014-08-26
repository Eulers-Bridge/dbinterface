package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadCountrysEventTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadCountrysEvent() throws Exception {
        ReadCountrysEvent readCountrysEvent = new ReadCountrysEvent();
        assertNotNull("ReadCountrysEvent is null", readCountrysEvent);
    }
}