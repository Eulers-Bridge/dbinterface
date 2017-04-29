package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class CreateInstitutionEventTest {
    final Long institutionId = new Long(1);
    final String name = new String("University of Melbourne");
    final String campus = new String("Parkville");
    final String state = new String("Victroia");
    final String countryName = new String("Australia");
    InstitutionDetails institutionDetails = null;
    CreateInstitutionEvent createInstitutionEvent = null;
    final Long eventId = new Long(1);

    @Before
    public void setUp() throws Exception {
        institutionDetails = new InstitutionDetails(institutionId);
        institutionDetails.setName(name);
        institutionDetails.setCampus(campus);
        institutionDetails.setState(state);
        institutionDetails.setCountryName(countryName);
        createInstitutionEvent = new CreateInstitutionEvent(institutionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateInstitutionEvent() throws Exception {
        InstitutionDetails institutionDetails1 = new InstitutionDetails(new Long(0));
        CreateInstitutionEvent createInstitutionEvent1 = new CreateInstitutionEvent(institutionDetails1);
        assertNotNull("CreateInstitutionEvent is null", createInstitutionEvent1);
    }

    @Test
    public void testGetDetails() throws Exception {
        assertEquals("InstitutionDetail does not match", institutionDetails, createInstitutionEvent.getDetails());
    }

    @Test
    public void testSetInstitutionDetails() throws Exception {
        InstitutionDetails institutionDetails1 = new InstitutionDetails(institutionId);
        institutionDetails1.setName(name);
        institutionDetails1.setCampus(campus);
        institutionDetails1.setState(state);
        institutionDetails1.setCountryName(countryName);
        createInstitutionEvent.setDetails(institutionDetails1);
        assertEquals("InstitutionDetail does not match", institutionDetails1, createInstitutionEvent.getDetails());
        assertEquals("InstitutionDetail does not match", institutionDetails, createInstitutionEvent.getDetails());
    }
}