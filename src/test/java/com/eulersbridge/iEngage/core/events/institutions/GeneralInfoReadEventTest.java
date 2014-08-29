package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.database.repository.InstitutionRepository.GeneralInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class GeneralInfoReadEventTest {

    GeneralInfoReadEvent generalInfoReadEvent = null;
    Iterator<GeneralInfo> generalInfos = new ArrayList<GeneralInfo>().iterator();
    @Before
    public void setUp() throws Exception {
        generalInfoReadEvent = new GeneralInfoReadEvent(generalInfos);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGeneralInfoReadEvent() throws Exception {
        assertNotNull("generalInfoReadEvent is null", generalInfoReadEvent);
    }

    @Test
    public void testGetGeneralInfos() throws Exception {
        assertEquals("generalInfos does not match", generalInfos, generalInfoReadEvent.getGeneralInfos());
    }
}