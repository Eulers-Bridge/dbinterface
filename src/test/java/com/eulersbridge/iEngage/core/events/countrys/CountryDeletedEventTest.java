package com.eulersbridge.iEngage.core.events.countrys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 *
 */

public class CountryDeletedEventTest {
    CountryDeletedEvent countryDeletedEvent = null;
    CountryDetails countryDetails=null;
    final Long id = new Long(1);
    final String france = "France";

    @Before
    public void setUp() throws Exception {
        countryDetails = new CountryDetails(new Long(1));
        countryDetails.setCountryName(france);
        countryDeletedEvent = new CountryDeletedEvent(id, countryDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCountryDeletedEvent() throws Exception {
        CountryDeletedEvent countryDeletedEvent = new CountryDeletedEvent(id, countryDetails);
        assertNotNull("CountryDeletedEvent is null", countryDeletedEvent);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("id does not match", countryDeletedEvent.getNodeId(), id);
    }

    @Test
    public void testGetDetails() throws Exception {
        CountryDetails countryDetails1 = new CountryDetails(new Long(1));
        countryDetails1.setCountryName("France");
        CountryDeletedEvent countryDeletedEvent1 = new CountryDeletedEvent(new Long(1), countryDetails1);
        assertEquals("detail does not match", countryDeletedEvent.getDetails(), countryDetails);
        assertEquals("detail does not match",countryDeletedEvent.getDetails(), countryDeletedEvent1.getDetails());
    }

    @Test
    public void testIsDeletionCompleted() throws Exception {
        assertTrue("deletion is not completed", countryDeletedEvent.isDeletionCompleted());
    }

    @Test
    public void testDeletionForbidden() throws Exception {
        DeletedEvent countryDeletedEvent1_forbid= CountryDeletedEvent.deletionForbidden(id, countryDetails);
        assertTrue("entity has not been found", countryDeletedEvent1_forbid.isEntityFound());
        assertFalse("deletion is not forbidden", countryDeletedEvent1_forbid.isDeletionCompleted());

    }

    @Test
    public void testNotFound() throws Exception {
        DeletedEvent countryDeletedEvent1_notFound = CountryDeletedEvent.notFound(id);
        assertFalse("Entity Found is not false", countryDeletedEvent1_notFound.isEntityFound());

    }

}