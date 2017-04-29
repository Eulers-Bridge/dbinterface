package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 *
 */

public class CountryUpdatedEventTest {
    final Long detailId = new Long(1);
    final Long eventId = new Long(1);
    final String france = "France";
    CountryDetails countryDetails = null;
    CountryUpdatedEvent countryUpdatedEvent = null;


    @Before
    public void setUp() throws Exception {
        countryDetails = new CountryDetails(detailId);
        countryDetails.setCountryName(france);
        countryUpdatedEvent = new CountryUpdatedEvent(eventId, countryDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCountryUpdatedEvent() throws Exception{
        CountryUpdatedEvent countryUpdatedEvent1 = new CountryUpdatedEvent(new Long(1));
        CountryUpdatedEvent countryUpdatedEvent2 = new CountryUpdatedEvent(new Long(1), countryDetails);
        assertNotNull("CountryUpdateEvent is null", countryUpdatedEvent1);
        assertNotNull("CountryUpdateEvent is null", countryUpdatedEvent2);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("event id does not match", eventId, countryUpdatedEvent.getNodeId());
    }

    @Test
    public void testGetCountryDetails() throws Exception {
        CountryDetails countryDetails1 = new CountryDetails(new Long(1));
        countryDetails1.setCountryName("France");
        assertEquals("country detail does not match", countryDetails, countryUpdatedEvent.getDetails());
        assertEquals("country detail does not match", countryDetails1, countryUpdatedEvent.getDetails());
    }
}