package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class RequestReadInstitutionEventTest {
    final Long id = new Long(0);
    RequestReadInstitutionEvent requestReadInstitutionEvent = null;

    @Before
    public void setUp() throws Exception {
        requestReadInstitutionEvent = new RequestReadInstitutionEvent(id);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestReadInstitutionEvent() throws Exception {
        assertNotNull("requestReadInstitutionEvent is null", requestReadInstitutionEvent);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("id does not match", id, requestReadInstitutionEvent.getId());
    }
}