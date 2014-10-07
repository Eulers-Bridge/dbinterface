package com.eulersbridge.iEngage.core.events.newsFeed;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class NewsFeedCreatedEventTest {
    final Long nodeId = new Long(1);
    final Long institutionId = new Long(1);
    NewsFeedDetails newsFeedDetails = null;
    final Long id = new Long(1);
    NewsFeedCreatedEvent newsFeedCreatedEvent = null;

    @Before
    public void setUp() throws Exception {
        newsFeedDetails = new NewsFeedDetails(institutionId);
        newsFeedDetails.setNodeId(nodeId);
        newsFeedCreatedEvent = new NewsFeedCreatedEvent(id, newsFeedDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewsFeedCreatedEvent() throws Exception {
        assertNotNull("newsFeedCreatedEvent is null", newsFeedCreatedEvent);
        NewsFeedCreatedEvent studentYearCreatedEvent1 = new NewsFeedCreatedEvent(id);
        assertNotNull("newsFeedCreatedEvent is null", studentYearCreatedEvent1);
    }

    @Test
    public void testGetNewsFeedDetails() throws Exception {
        assertEquals("newsFeedDetails does not match", newsFeedDetails, newsFeedCreatedEvent.getNewsFeedDetails());
    }

    @Test
    public void testSetNewsFeedDetails() throws Exception {
        NewsFeedCreatedEvent newsFeedCreatedEvent1 = new NewsFeedCreatedEvent(id);
        newsFeedCreatedEvent1.setNewsFeedDetails(newsFeedDetails);
        assertEquals("newsFeedDetails does not match", newsFeedDetails, newsFeedCreatedEvent1.getNewsFeedDetails());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("id does not match", id ,newsFeedCreatedEvent.getId());
    }

    @Test
    public void testSetId() throws Exception {
        Long id1 = new Long(2);
        newsFeedCreatedEvent.setId(id1);
        assertEquals("id does not match", id1, newsFeedCreatedEvent.getId());
    }

    @Test
    public void testIsInstitutionFound() throws Exception {
        assertTrue("institutionFound is not true", newsFeedCreatedEvent.isInstitutionFound());
    }

    @Test
    public void testSetInstitutionFound() throws Exception {
        newsFeedCreatedEvent.setInstitutionFound(false);
        assertFalse("institutionFound is not false", newsFeedCreatedEvent.isInstitutionFound());

    }

    @Test
    public void testInstitutionNotFound() throws Exception {
        NewsFeedCreatedEvent newsFeedCreatedEvent1 = NewsFeedCreatedEvent.institutionNotFound(id);
        assertNotNull("institutionNotFound() returns null", newsFeedCreatedEvent1);
        assertFalse("institutionFound is not false", newsFeedCreatedEvent1.isInstitutionFound());
    }
}