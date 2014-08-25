package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class InstitutionUpdatedEventTest {
    final Long institutionId = new Long(1);
    final String name = new String("University of Melbourne");
    final String campus = new String("Parkville");
    final String state = new String("Victroia");
    final String countryName = new String("Australia");
    InstitutionDetails institutionDetails = null;
    final Long eventId = new Long(1);
    InstitutionUpdatedEvent institutionUpdatedEvent = null;

    @Before
    public void setUp() throws Exception {
        institutionDetails = new InstitutionDetails(institutionId);
        institutionDetails.setName(name);
        institutionDetails.setCampus(campus);
        institutionDetails.setState(state);
        institutionDetails.setCountryName(countryName);
        institutionUpdatedEvent = new InstitutionUpdatedEvent(eventId, institutionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInstitutionUpdatedEvent() throws Exception {
        assertNotNull("institutionUpdatedEvent is null", institutionUpdatedEvent);
        InstitutionUpdatedEvent institutionUpdatedEvent1 = new InstitutionUpdatedEvent(new Long(2));
        assertNotNull("institutionUpdatedEvent is null", institutionUpdatedEvent1);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("eventId does not match", eventId, institutionUpdatedEvent.getId());
    }

    @Test
    public void testGetInstDetails() throws Exception {
        assertEquals("institutionDetails does not match", institutionDetails, institutionUpdatedEvent.getInstDetails());
    }

    @Test
    public void testCountryNotFound() throws Exception {
        InstitutionUpdatedEvent institutionUpdatedEvent1 = InstitutionUpdatedEvent.countryNotFound(eventId);
        assertNotNull("CountryNotFound return null", institutionUpdatedEvent1);
        assertFalse("countryFound is not false", institutionUpdatedEvent1.isCountryFound());
    }

    @Test
    public void testIsCountryFound() throws Exception {
        assertTrue("countryFound is not true", institutionUpdatedEvent.isCountryFound());
    }
}