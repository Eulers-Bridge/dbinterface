package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UpdateCountryEventTest {
    final Long eventId = new Long(1);
    final Long detailId = new Long(2);
    final String france = new String("France");
    CountryDetails countryDetails = null;
    UpdateCountryEvent updateCountryEvent = null;

    @Before
    public void setUp() throws Exception {
        countryDetails = new CountryDetails(detailId);
        countryDetails.setCountryName(france);
        updateCountryEvent = new UpdateCountryEvent(eventId, countryDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUpdateCountryEvent() throws Exception {
        CountryDetails countryDetails1 = new CountryDetails(new Long(0));
        UpdateCountryEvent updateCountryEvent1 = new UpdateCountryEvent(new Long(1), countryDetails1);
        assertNotNull("UpdateCountryEvent is null", updateCountryEvent1);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Event id does not match", eventId, updateCountryEvent.getNodeId());
    }

    @Test
    public void testGetCountryDetails() throws Exception {
        assertEquals("CountryDetail does not match", countryDetails, updateCountryEvent.getDetails());
    }
}