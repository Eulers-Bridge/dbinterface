package com.eulersbridge.iEngage.core.events.generalInfo;

import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoReadEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class GeneralInfoReadEventTest 
{

    GeneralInfoReadEvent generalInfoReadEvent = null;
	private GeneralInfoDetails dets = null;
    @Before
    public void setUp() throws Exception {
        generalInfoReadEvent = new GeneralInfoReadEvent(dets);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGeneralInfoReadEvent() throws Exception {
        assertNotNull("generalInfoReadEvent is null", generalInfoReadEvent);
    }

    @Test
    public void testGetDets() throws Exception
    {
        assertEquals("generalInfos does not match", dets , generalInfoReadEvent.getDets());
    }
}