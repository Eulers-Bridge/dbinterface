package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadInstitutionsEventTest {
    final Long countryId = new Long(1);
    ReadInstitutionsEvent readInstitutionsEvent = null;

    @Before
    public void setUp() throws Exception {
        readInstitutionsEvent = new ReadInstitutionsEvent(countryId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadInstitutionsEvent() throws Exception {
        assertNotNull("readInstitutionsEvent is null", readInstitutionsEvent);
    }

    @Test
    public void testGetCountryId() throws Exception {
        assertEquals("countryId does not match", countryId, readInstitutionsEvent.getCountryId());
    }

    @Test
    public void testSetCountryId() throws Exception {
        Long countryId2 = new Long(2);
        readInstitutionsEvent.setCountryId(countryId2);
        assertEquals("countryId does not match", countryId2, readInstitutionsEvent.getCountryId());
    }
}