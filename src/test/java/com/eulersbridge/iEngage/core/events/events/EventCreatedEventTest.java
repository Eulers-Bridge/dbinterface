package com.eulersbridge.iEngage.core.events.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class EventCreatedEventTest {
    private Long eventId = new Long(0);
    private String name = "new event";
    private String location = "campus";
    private Long date = 1000000l;
    private Long ends =1000000l;
    private String description = "An event";
    private String picture[] = new String[]{"./path"};
    private String volunteerPositions[] = new String[]{"positon"};
    private Long created = new Long(0);
    private String organizer = "Unimelb";
    private String organizerEmail = "test@test.com";
    private Long modified = new Long(1);
    private Long institutionId=26l;;

    private EventDetails eventDetails;
    private EventCreatedEvent eventCreatedEvent;

    @Before
    public void setUp() throws Exception {
        eventDetails = new EventDetails();
        eventDetails.setEventId(eventId);
        eventDetails.setName(name);
        eventDetails.setLocation(location);
        eventDetails.setStarts(date);
        eventDetails.setEnds(ends);
        eventDetails.setDescription(description);
        eventDetails.setPicture(picture);
        eventDetails.setVolunteerPositions(volunteerPositions);
        eventDetails.setCreated(created);
        eventDetails.setOrganizer(organizer);
        eventDetails.setOrganizerEmail(organizerEmail);
        eventDetails.setModified(modified);
        eventDetails.setInstitutionId(institutionId);

        eventCreatedEvent = new EventCreatedEvent(eventId, eventDetails);
        assertNotNull("eventCreatedEvent is null", eventCreatedEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetEventId() throws Exception {
        assertEquals("Id does not match", eventId, eventCreatedEvent.getEventId());
    }

    @Test
    public void testSetEventId() throws Exception
    {
    	Long thisId=11L;
        assertNotEquals("Id does match", thisId, eventCreatedEvent.getEventId());
        eventCreatedEvent.setEventId(thisId);
        assertEquals("Id does not match", thisId, eventCreatedEvent.getEventId());
    }

    @Test
    public void testIsInstitutionFound() throws Exception {
        assertEquals("IsInstitutionFound is not true", true, eventCreatedEvent.isInstitutionFound());
    }

    @Test
    public void testSetInstitutionFound() throws Exception {
        eventCreatedEvent.setInstitutionFound(false);
        assertEquals("IsInstitutionFound does not match", false, eventCreatedEvent.isInstitutionFound());
    }

    @Test
    public void testInstitutionNotFound() throws Exception {
        EventCreatedEvent eventCreatedEvent1 = EventCreatedEvent.institutionNotFound(new Long(11));
        assertNotNull("eventCreatedEvent1 is null", eventCreatedEvent1);
        assertFalse("IsInstitutionFound is not false", eventCreatedEvent1.isInstitutionFound());
    }
}