package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.ReadEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 *
 */

public class CountryReadEventTest {
    CountryReadEvent countryReadEvent = null;
    CountryDetails countryDetails = null;
    final Long eventId = new Long(1);
    final Long detailId = new Long(1);
    final String france = "France";



    @Before
    public void setUp() throws Exception {
        countryDetails = new CountryDetails(detailId);
        countryDetails.setCountryName(france);
        countryReadEvent = new CountryReadEvent(eventId, countryDetails);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCountryReadEvent() throws Exception {
        CountryReadEvent countryReadEvent1 = new CountryReadEvent(new Long(1));
        CountryReadEvent countryReadEvent2 = new CountryReadEvent(new Long(2), countryDetails);
        assertNotNull("countryReadEvent is null", countryReadEvent1);
        assertNotNull("countryReadEvent is null", countryReadEvent2);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Event id does not match", countryReadEvent.getNodeId(), eventId);
    }

    @Test
    public void testGetCountryDetails() throws Exception {
        CountryDetails countryDetails1 = new CountryDetails(new Long(1));
        countryDetails1.setCountryName(france);
        assertEquals("Event detail does not match", countryReadEvent.getDetails(), countryDetails);
        assertEquals("Event detail does not match", countryReadEvent.getDetails(), countryDetails1);
    }

    @Test
    public void testNotFound() throws Exception {
        ReadEvent countryReadEvent1 = CountryReadEvent.notFound(new Long(1));
        assertFalse("EntityFound is not false",countryReadEvent1.isEntityFound());
    }

}