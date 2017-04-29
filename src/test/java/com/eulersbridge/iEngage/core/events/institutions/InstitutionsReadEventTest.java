package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class InstitutionsReadEventTest {
    ArrayList<InstitutionDetails> institutions = null;
    InstitutionDetails institutionDetails = null;
    InstitutionDetails institutionDetails1 = null;
    InstitutionsReadEvent institutionsReadEvent = null;
    final Long countryId = new Long(1);


    @Before
    public void setUp() throws Exception {
        institutions = new ArrayList<>();
        institutionDetails = new InstitutionDetails(new Long(0));
        institutionDetails1 = new InstitutionDetails(new Long(1));
        institutions.add(institutionDetails);
        institutions.add(institutionDetails1);
        institutionsReadEvent = new InstitutionsReadEvent(countryId, institutions);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInstitutionsReadEvent() throws Exception {
        assertNotNull("institutionsReadEvent is null", institutionsReadEvent);
        InstitutionsReadEvent institutionsReadEvent1 = new InstitutionsReadEvent(institutions);
        assertNotNull("institutionsReadEvent is null", institutionsReadEvent1);
    }

    @Test
    public void testGetInstitutions() throws Exception {
        assertEquals("Institutions does not match", institutions, institutionsReadEvent.getInstitutions());
    }

    @Test
    public void testSetInstitutions() throws Exception {
        ArrayList<InstitutionDetails> institutions1 = new ArrayList<>();
        institutions1.add(new InstitutionDetails(new Long(1)));
        institutionsReadEvent.setInstitutions(institutions1);
        assertEquals("Institutions does not match", institutions1, institutionsReadEvent.getInstitutions());
    }

    @Test
    public void testGetCountryId() throws Exception {
        assertEquals("countryId does not match", countryId, institutionsReadEvent.getCountryId());
    }

    @Test
    public void testSetCountryId() throws Exception {
        institutionsReadEvent.setCountryId(new Long(2));
        assertEquals("countryId does not match", new Long(2), institutionsReadEvent.getCountryId());
    }
}