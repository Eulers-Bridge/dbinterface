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

public class CountryCreatedEventTest {
    CountryDetails countrydetails;
    CountryCreatedEvent countryCreatedEvent;
    final Long countryId = new Long(1);
    final Long countryId2 = new Long(2);
    final String France = "France";
    final String Germany = "Germany";


    @Before
    public void setUp() throws Exception {
        countrydetails = new CountryDetails(countryId);
        countrydetails.setCountryName(France);
        countryCreatedEvent = new CountryCreatedEvent(countryId, countrydetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCountryCreatedEvent() throws Exception {
        CountryCreatedEvent countryCreatedEvent1 = new CountryCreatedEvent(new Long(2), new CountryDetails(new Long(2)));
        assertNotNull(countryCreatedEvent1);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Country Id doesn't match",countryCreatedEvent.getId(),countryId);
    }

    @Test
    public void testSetKey() throws Exception {
        countryCreatedEvent.setKey(countryId2);
        assertEquals("Country id doesn't match", countryCreatedEvent.getId(), countryId2);
    }

    @Test
    public void testSetCountryDetails() throws Exception {
    	CountryDetails countryDetails1 = new CountryDetails(countryId2);
    	countryDetails1.setCountryName(Germany);
        CountryDetails countryDetails2 = new CountryDetails(countryId2);
        countryDetails2.setCountryName(Germany);
        countryCreatedEvent.setDetails(countryDetails2);
        assertEquals("Country Detail doesn't match",countryCreatedEvent.getDetails(), countryDetails2);
        assertEquals("Country Detail doesn't match",countryCreatedEvent.getDetails(), countryDetails1);
    }

    @Test
    public void testGetCountryDetails() throws Exception {
        assertEquals("Country Detail doesn't match", countryCreatedEvent.getDetails(), countrydetails);
    }
}