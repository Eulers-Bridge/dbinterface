package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.generalInfo.ReadGeneralInfoEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class ReadGeneralInfoEventTest {
    ReadGeneralInfoEvent readGeneralInfoEvent = null;
    @Before
    public void setUp() throws Exception {
        readGeneralInfoEvent = new ReadGeneralInfoEvent();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadGeneralInfoEvent() throws Exception {
        assertNotNull("readGeneralInfoEvent is null", readGeneralInfoEvent);
    }
}