package com.eulersbridge.iEngage.core.events.generalInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class GeneralInfoReadEventTest 
{

    GeneralInfoReadEvent generalInfoReadEvent = null;
	private GeneralInfoDetails dets;
    @Before
    public void setUp() throws Exception 
    {
    	ArrayList<GiCountry> countries=new ArrayList<GiCountry>();
    	dets = new GeneralInfoDetails(countries.iterator());
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