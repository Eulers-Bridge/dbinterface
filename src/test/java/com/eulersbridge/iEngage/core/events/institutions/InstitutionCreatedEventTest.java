package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class InstitutionCreatedEventTest {
    final Long institutionId = new Long(1);
    final String name = new String("University of Melbourne");
    final String campus = new String("Parkville");
    final String state = new String("Victroia");
    final String countryName = new String("Australia");
    InstitutionDetails institutionDetails = null;
    InstitutionCreatedEvent institutionCreatedEvent = null;
    final Long eventId = new Long(1);

    @Before
    public void setUp() throws Exception {
        institutionDetails = new InstitutionDetails(institutionId);
        institutionDetails.setName(name);
        institutionDetails.setCampus(campus);
        institutionDetails.setState(state);
        institutionDetails.setCountryName(countryName);
        institutionCreatedEvent = new InstitutionCreatedEvent(eventId, institutionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInstitutionCreatedEvent() throws Exception {
        InstitutionCreatedEvent institutionCreatedEvent1 = new InstitutionCreatedEvent(eventId);
        InstitutionCreatedEvent institutionCreatedEvent2 = new InstitutionCreatedEvent(eventId, institutionDetails);
        assertNotNull("InstitutionCreatedEvent is null", institutionCreatedEvent1);
        assertNotNull("InstitutionCreatedEvent is null", institutionCreatedEvent2);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("eventId does not match", eventId, institutionCreatedEvent.getId());
    }

    @Test
    public void testSetKey() throws Exception {
        Long eventId2 = new Long(2);
        institutionCreatedEvent.setKey(eventId2);
        assertEquals("eventId does not match", eventId2, institutionCreatedEvent.getId());
    }

    @Test
    public void testSetInstitutionDetails() throws Exception {
        InstitutionDetails institutionDetails1 = new InstitutionDetails(institutionId);
        institutionDetails1.setName(name);
        institutionDetails1.setCampus(campus);
        institutionDetails1.setState(state);
        institutionDetails1.setCountryName(countryName);
        institutionCreatedEvent.setInstitutionDetails(institutionDetails1);
        assertEquals("InstitutionDetail does not match", institutionDetails1, institutionCreatedEvent.getInstitutionDetails());
        assertEquals("InstitutionDetail does not match", institutionDetails, institutionCreatedEvent.getInstitutionDetails());
    }

    @Test
    public void testGetInstitutionDetails() throws Exception {
        assertEquals("InstitutionDetail does not match", institutionDetails, institutionCreatedEvent.getInstitutionDetails());
    }

    @Test
    public void testCountryNotFound() throws Exception {
        InstitutionCreatedEvent institutionCreatedEvent1 = InstitutionCreatedEvent.countryNotFound(eventId);
        assertNotNull("InstitutionCreatedEvent is null", institutionCreatedEvent1);
        assertFalse("countryFound is true", institutionCreatedEvent1.isCountryFound());
    }

    @Test
    public void testIsCountryFound() throws Exception {
        assertTrue("countryFound is false", institutionCreatedEvent.isCountryFound());
        InstitutionCreatedEvent institutionCreatedEvent1 = InstitutionCreatedEvent.countryNotFound(eventId);
        assertFalse("countryFound is true", institutionCreatedEvent1.isCountryFound());
    }
}