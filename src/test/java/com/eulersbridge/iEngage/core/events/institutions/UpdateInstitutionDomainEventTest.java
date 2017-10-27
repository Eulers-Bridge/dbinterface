package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class UpdateInstitutionDomainEventTest {
    final Long institutionId = new Long(1);
    final String name = new String("University of Melbourne");
    final String campus = new String("Parkville");
    final String state = new String("Victroia");
    final String countryName = new String("Australia");
    InstitutionDetails institutionDetails = null;
    UpdateInstitutionEvent updateInstitutionEvent = null;

    @Before
    public void setUp() throws Exception {
        institutionDetails = new InstitutionDetails(institutionId);
        institutionDetails.setName(name);
        institutionDetails.setCampus(campus);
        institutionDetails.setState(state);
        institutionDetails.setCountryName(countryName);
        updateInstitutionEvent = new UpdateInstitutionEvent(institutionId, institutionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUpdateInstitutionEvent() throws Exception {
        assertNotNull("updateInstitutionEvent is not null", updateInstitutionEvent);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("eventId does not match", institutionId, updateInstitutionEvent.getNodeId());
    }

    @Test
    public void testGetInstDetails() throws Exception {
        InstitutionDetails institutionDetails1 = new InstitutionDetails(institutionId);
        institutionDetails1.setName(name);
        institutionDetails1.setCampus(campus);
        institutionDetails1.setState(state);
        institutionDetails1.setCountryName(countryName);
        assertEquals("InstitutionDetails does not match", institutionDetails, updateInstitutionEvent.getDetails());
        assertEquals("InstitutionDetails does not match", institutionDetails1, updateInstitutionEvent.getDetails());

    }
}