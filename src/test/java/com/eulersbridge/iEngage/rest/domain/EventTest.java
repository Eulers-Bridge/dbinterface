package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.events.EventDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class EventTest {
    private Long eventId = new Long(0);
    private String name = "new event";
    private String location = "campus";
    private Long date = new Long(1000000);
    private String description = "An event";
    private String picture[] = new String[]{"./path"};
    private String volunteerPositions[] = new String[]{"positon"};
    private Long created = new Long(0);
    private String organizer = "Unimelb";
    private String organizerEmail = "test@test.com";
    private Long modified = new Long(1);

    private EventDetails eventDetails;
    private Event event;

    @Before
    public void setUp() throws Exception {
        event = new Event();
        assertNotNull("constructor returns null", event);
        eventDetails = new EventDetails();
        eventDetails.setEventId(eventId);
        eventDetails.setName(name);
        eventDetails.setLocation(location);
        eventDetails.setDate(date);
        eventDetails.setDescription(description);
        eventDetails.setPicture(picture);
        eventDetails.setVolunteerPositions(volunteerPositions);
        eventDetails.setCreated(created);
        eventDetails.setOrganizer(organizer);
        eventDetails.setOrganizerEmail(organizerEmail);
        eventDetails.setModified(modified);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFromEventDetails() throws Exception {
        Event event1 = Event.fromEventDetails(eventDetails);
        assertNotNull("event is null", event1);
    }

    @Test
    public void testToEventDetails() throws Exception {
        Event event1 = Event.fromEventDetails(eventDetails);
        EventDetails eventDetails1 = event1.toEventDetails();
        assertNotNull("eventDetails is null", eventDetails1);
        assertEquals("eventDetail does not match", eventDetails1, eventDetails);
    }

    @Test
    public void testGetEventId() throws Exception {
        event.setEventId(eventId);
        Long id = event.getEventId();
        assertEquals("id does not match", eventId, id);
    }

    @Test
    public void testGetName() throws Exception {
        event.setName(name);
        String name1 = event.getName();
        assertEquals("name does not match", name1, name);
    }

    @Test
    public void testGetLocation() throws Exception {
        event.setLocation(location);
        String location1 = event.getLocation();
        assertEquals("location does not match", location, location1);
    }

    @Test
    public void testGetDate() throws Exception {
        event.setDate(date);
        Long date1 = event.getDate();
        assertEquals("date does not match", date1, date);
    }


    @Test
    public void testGetDescription() throws Exception {
        event.setDescription(description);
        String des = event.getDescription();
        assertEquals("description does not match", des, description);
    }

    @Test
    public void testGetPicture() throws Exception {
        event.setPicture(picture);
        String pic[] = event.getPicture();
        assertEquals("picture does not match", pic, picture);
    }

    @Test
    public void testGetVolunteerPositions() throws Exception {
        event.setVolunteerPositions(volunteerPositions);
        String[] vo = event.getVolunteerPositions();
        assertEquals("volunteerPositions does not match", vo, volunteerPositions);
    }

    @Test
    public void testGetCreated() throws Exception {
        event.setCreated(created);
        Long cre = event.getCreated();
        assertEquals("created does not match", cre, created);
    }

    @Test
    public void testGetOrganizer() throws Exception {
        event.setOrganizer(organizer);
        String org = event.getOrganizer();
        assertEquals("organizer does not match", org, organizer);
    }

    @Test
    public void testGetOrganizerEmail() throws Exception {
        event.setOrganizerEmail(organizerEmail);
        String email = event.getOrganizerEmail();
        assertEquals("mail does not match", email, organizerEmail);
    }

    @Test
    public void testGetModified() throws Exception {
        event.setModified(modified);
        Long modi = event.getModified();
        assertEquals("modified does not match", modi, modified);
    }
}