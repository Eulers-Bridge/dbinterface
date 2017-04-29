package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class DeleteCountryEventTest {
    DeleteCountryEvent deleteCountryEvent = null;
    final Long countryId = new Long(1);


    @Before
    public void setUp() throws Exception {
        deleteCountryEvent = new DeleteCountryEvent(countryId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDeleteCountryEvent() throws Exception {
        DeleteCountryEvent deleteCountryEvent1 = new DeleteCountryEvent(new Long(2));
        assertNotNull("DeleteCountryEvent is null", deleteCountryEvent1);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("CountryId does not match", countryId, deleteCountryEvent.getNodeId());
    }
}