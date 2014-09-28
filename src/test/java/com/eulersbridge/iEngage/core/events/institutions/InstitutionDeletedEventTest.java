package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class InstitutionDeletedEventTest {
    final Long institutionId = new Long(1);
    final String name = new String("University of Melbourne");
    final String campus = new String("Parkville");
    final String state = new String("Victroia");
    final String countryName = new String("Australia");
    InstitutionDetails institutionDetails = null;
    InstitutionDeletedEvent institutionDeletedEvent = null;
    final Long eventId = new Long(1);

    @Before
    public void setUp() throws Exception {
        institutionDetails = new InstitutionDetails(institutionId);
        institutionDetails.setName(name);
        institutionDetails.setCampus(campus);
        institutionDetails.setState(state);
        institutionDetails.setCountryName(countryName);
        institutionDeletedEvent = new InstitutionDeletedEvent(eventId, institutionDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInstitutionDeletedEvent() throws Exception {
        assertNotNull("institutionDeletedEvent is null", institutionDeletedEvent);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("eventId does not match", eventId, institutionDeletedEvent.getId());
    }

    @Test
    public void testGetDetails() throws Exception {
        assertEquals("detail does not match", institutionDetails, institutionDeletedEvent.getDetails());
    }

    @Test
    public void testIsDeletionCompleted() throws Exception {
        assertTrue("deletionCompleted is not true", institutionDeletedEvent.isDeletionCompleted());
        InstitutionDeletedEvent institutionDeletedEvent1 = InstitutionDeletedEvent.deletionForbidden(eventId, institutionDetails);
        assertFalse("forbid deletionCompleted is true", institutionDeletedEvent1.isDeletionCompleted());
    }

    @Test
    public void testDeletionForbidden() throws Exception {
        InstitutionDeletedEvent institutionDeletedEvent1 = InstitutionDeletedEvent.deletionForbidden(eventId, institutionDetails);
        assertNotNull("InstitutionDeletedEvent is null", institutionDeletedEvent1);
        assertTrue("entityFound is not true", institutionDeletedEvent1.isEntityFound());
        assertFalse("forbid deletionCompleted is true", institutionDeletedEvent1.isDeletionCompleted());
    }

    @Test
    public void testNotFound() throws Exception {
        InstitutionDeletedEvent institutionDeletedEvent1 = InstitutionDeletedEvent.notFound(eventId);
        assertNotNull("InstitutionDeletedEvent is null", institutionDeletedEvent1);
        assertFalse("entityFound is not false", institutionDeletedEvent1.isEntityFound());

    }
}