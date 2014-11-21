package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.ReadEvent;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadInstitutionEventTest {
    final Long institutionId = new Long(1);
    final String name = new String("University of Melbourne");
    final String campus = new String("Parkville");
    final String state = new String("Victroia");
    final String countryName = new String("Australia");
    InstitutionDetails institutionDetails = null;
    ReadInstitutionEvent readInstitutionEvent = null;
    final Long eventId = new Long(1);

    @Before
    public void setUp() throws Exception {
        institutionDetails = new InstitutionDetails(institutionId);
        institutionDetails.setName(name);
        institutionDetails.setCampus(campus);
        institutionDetails.setState(state);
        institutionDetails.setCountryName(countryName);
        readInstitutionEvent = new ReadInstitutionEvent(eventId,institutionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadInstitutionEvent() throws Exception {
        assertNotNull("readInstitutionEvent is null", readInstitutionEvent);
        ReadInstitutionEvent readInstitutionEvent1 = new ReadInstitutionEvent(new Long(1));
        assertNotNull("readInstitutionEvent is null", readInstitutionEvent1);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("eventId does not match", eventId, readInstitutionEvent.getId());
    }

    @Test
    public void testGetInstitutionDetails() throws Exception {
        assertEquals("institutionDetails does not match", institutionDetails, readInstitutionEvent.getInstitutionDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        ReadEvent readInstitutionEvent1 = ReadInstitutionEvent.notFound(eventId);
        assertFalse("entityFound is not false", readInstitutionEvent1.isEntityFound());
    }
}