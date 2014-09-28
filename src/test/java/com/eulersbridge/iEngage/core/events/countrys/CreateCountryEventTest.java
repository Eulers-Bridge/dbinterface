package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 *
 */

public class CreateCountryEventTest {
    final Long eventId = new Long(1);
    final Long detailId = new Long(1);
    final String france = "France";
    CountryDetails countryDetails = null;
    CreateCountryEvent createCountryEvent = null;

    @Before
    public void setUp() throws Exception {
        countryDetails = new CountryDetails(detailId);
        countryDetails.setCountryName(france);
        createCountryEvent = new CreateCountryEvent(eventId, countryDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateCountryEvent() throws Exception {
        CountryDetails countryDetails1 = new CountryDetails(new Long (1));
        countryDetails1.setCountryName(france);
        CreateCountryEvent createCountryEvent1 = new CreateCountryEvent(new Long(1), countryDetails1);
        assertNotNull("CreateCountryEvent is null", createCountryEvent1);
    }

    @Test
    public void testGetDetails() throws Exception {
        CountryDetails countryDetails1 = new CountryDetails(new Long (1));
        countryDetails1.setCountryName(new String("France"));
        CreateCountryEvent createCountryEvent1 = new CreateCountryEvent(new Long(1), countryDetails1);
        assertEquals("Country detail does not match", countryDetails, createCountryEvent.getDetails());
        assertEquals("Country detail does not match", createCountryEvent1.getDetails(),createCountryEvent.getDetails());

    }

    @Test
    public void testSetInstitutionDetails() throws Exception {
        CreateCountryEvent createCountryEvent1 = new CreateCountryEvent(new Long(1), countryDetails);
        CountryDetails countryDetails1 = new CountryDetails(new Long ("1"));
        countryDetails1.setCountryName(new String("Germany"));
        createCountryEvent1.setInstitutionDetails(countryDetails1);
        assertEquals("Country detail does not match", countryDetails1, createCountryEvent1.getDetails());


    }
}