package com.eulersbridge.iEngage.core.events.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class EventDetailsTest {
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
        assertNotNull("eventDetails is null", eventDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        String str = eventDetails.toString();
        assertNotNull("toString() returns null", str);
    }

	private void checkHashCode(EventDetails test1,EventDetails test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(EventDetails test1,EventDetails test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
    @Test
    public void testHashCode() throws Exception
    {
		EventDetails eventTest=DatabaseDataFixture.populateEvent1().toEventDetails(),event=DatabaseDataFixture.populateEvent1().toEventDetails();
		assertEquals(eventTest.hashCode(),eventTest.hashCode());
		assertEquals(eventTest.hashCode(),event.hashCode());
		eventTest.setEventId(null);
		checkHashCode(event,eventTest);
		event.setEventId(null);
		eventTest.setStarts(null);
		checkHashCode(event,eventTest);
		eventTest.setStarts(event.getStarts());
		eventTest.setEnds(null);
		checkHashCode(event,eventTest);
		eventTest.setEnds(event.getEnds());
		eventTest.setCreated(null);
		checkHashCode(event,eventTest);
		eventTest.setCreated(event.getCreated());
		eventTest.setDescription(null);
		checkHashCode(event,eventTest);
		eventTest.setDescription(event.getDescription());
		eventTest.setLocation(null);
		checkHashCode(event,eventTest);
		eventTest.setLocation(event.getLocation());
		eventTest.setModified(null);
		checkHashCode(event,eventTest);
		eventTest.setModified(event.getModified());
		eventTest.setName(null);
		checkHashCode(event,eventTest);
		eventTest.setName(event.getName());
		eventTest.setOrganizer(null);
		checkHashCode(event,eventTest);
		eventTest.setOrganizer(event.getOrganizer());
		eventTest.setOrganizerEmail(null);
		checkHashCode(event, eventTest);
		eventTest.setOrganizerEmail(event.getOrganizerEmail());
    }

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
    @Test
    public void testEquals() throws Exception {
        EventDetails event = DatabaseDataFixture.populateEvent1().toEventDetails();

		EventDetails eventTest=null;
		assertNotEquals(eventTest,event);
		assertNotEquals(event,eventTest);
		String notElection="";
		assertNotEquals(event,notElection);
		eventTest=DatabaseDataFixture.populateEvent1().toEventDetails();
		assertEquals(eventTest,eventTest);
		assertEquals(eventTest,event);
		eventTest.setEventId(54l);
		checkNotEquals(event,eventTest);
		event.setEventId(null);
		checkNotEquals(event,eventTest);
		eventTest.setEventId(null);
		assertEquals(event, eventTest);
		assertEquals(eventTest, event);
		eventTest.setCreated(4321l);
		assertNotEquals(event, eventTest);
		eventTest.setCreated(null);
		checkNotEquals(event, eventTest);
		eventTest.setCreated(event.getCreated());
		eventTest.setDescription("Some description");
		assertNotEquals(event, eventTest);
		eventTest.setDescription(null);
		checkNotEquals(eventTest, event);
		eventTest.setDescription(event.getDescription());
		
		eventTest.setEnds(54l);
		assertNotEquals(event, eventTest);
		eventTest.setEnds(null);
		checkNotEquals(event, eventTest);
		eventTest.setEnds(event.getEnds());
		
		eventTest.setStarts(54l);
		assertNotEquals(event, eventTest);
		eventTest.setStarts(null);
		checkNotEquals(event, eventTest);
		eventTest.setStarts(event.getStarts());
		
		eventTest.setModified(54l);
		assertNotEquals(event, eventTest);
		eventTest.setModified(null);
		checkNotEquals(event, eventTest);
		eventTest.setModified(event.getModified());
		
		eventTest.setLocation("some location");
		assertNotEquals(event, eventTest);
		eventTest.setLocation(null);
		checkNotEquals(event, eventTest);
		eventTest.setLocation(event.getLocation());
		
		event.setName("some name");
		assertNotEquals(event, eventTest);
		eventTest.setName(null);
		checkNotEquals(event, eventTest);
		eventTest.setName(event.getName());
		
		eventTest.setOrganizer("some Organizer");
		assertNotEquals(event, eventTest);
		eventTest.setOrganizer(null);
		checkNotEquals(event, eventTest);
		eventTest.setOrganizer(event.getOrganizer());
		eventTest.setOrganizerEmail("some OrganizerEmail");
		assertNotEquals(event, eventTest);
		eventTest.setOrganizerEmail(null);
		checkNotEquals(event, eventTest);
		eventTest.setOrganizerEmail(event.getOrganizerEmail());
		
		String[] picture={"picture1","picture2"};
		eventTest.setPicture(picture);
		checkNotEquals(event, eventTest);
		eventTest.setPicture(event.getPicture());
		
		String[] volunteerPositions = {"position 1","position 2"};
		eventTest.setVolunteerPositions(volunteerPositions);
		checkNotEquals(event, eventTest);
		eventTest.setVolunteerPositions(event.getVolunteerPositions());
    
    }

    @Test
    public void testGetEventId() throws Exception {
        assertEquals("id does not match", eventId, eventDetails.getEventId());
    }

    @Test
    public void testSetEventId() throws Exception {
        Long id = new Long(2);
        eventDetails.setEventId(id);
        assertEquals("id does not match", id, eventDetails.getEventId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("name does not match", name, eventDetails.getName());
    }

    @Test
    public void testSetName() throws Exception {
        String na = "na";
        eventDetails.setName(na);
        assertEquals("name does not match", na, eventDetails.getName());
    }

    @Test
    public void testGetLocation() throws Exception {
        assertEquals("location does not match", location, eventDetails.getLocation());
    }

    @Test
    public void testSetLocation() throws Exception {
        String lo = "lo";
        eventDetails.setLocation(lo);
        assertEquals("location does not match", lo, eventDetails.getLocation());
    }

    @Test
    public void testGetStarts() throws Exception {
        assertEquals("date does not match", date, eventDetails.getStarts());
    }

    @Test
    public void testSetStarts() throws Exception {
        Long da = new Long(2);
        eventDetails.setStarts(da);
        assertEquals("date does not match", da, eventDetails.getStarts());
    }

    @Test
    public void testGetEnds() throws Exception {
        assertEquals("date does not match", ends, eventDetails.getEnds());
    }

    @Test
    public void testSetEnds() throws Exception {
        Long da = new Long(2);
        eventDetails.setEnds(da);
        assertEquals("date does not match", da, eventDetails.getEnds());
    }

    @Test
    public void testGetDescription() throws Exception {
        assertEquals("description does not match", description, eventDetails.getDescription());
    }

    @Test
    public void testSetDescription() throws Exception {
        String des = "des";
        eventDetails.setDescription(des);
        assertEquals("description does not match", des, eventDetails.getDescription());
    }

    @Test
    public void testGetPicture() throws Exception {
    }

    @Test
    public void testSetPicture() throws Exception {

    }

    @Test
    public void testGetVolunteerPositions() throws Exception {

    }

    @Test
    public void testSetVolunteerPositions() throws Exception {

    }

    @Test
    public void testGetCreated() throws Exception {
        assertEquals("created does not match", created, eventDetails.getCreated());
    }

    @Test
    public void testSetCreated() throws Exception {
        Long cre = new Long(3);
        eventDetails.setCreated(cre);
        assertEquals("created does not match", cre, eventDetails.getCreated());
    }

    @Test
    public void testGetOrganizer() throws Exception {
        assertEquals("organizer does not match", organizer, eventDetails.getOrganizer());
    }

    @Test
    public void testSetOrganizer() throws Exception {
        String org = "org";
        eventDetails.setOrganizer(org);
        assertEquals("organizer does not match", org, eventDetails.getOrganizer());
    }

    @Test
    public void testGetOrganizerEmail() throws Exception {
        assertEquals("organizerEmail does not match", organizerEmail, eventDetails.getOrganizerEmail());
    }

    @Test
    public void testSetOrganizerEmail() throws Exception {
        String email = "email";
        eventDetails.setOrganizerEmail(email);
        assertEquals("email does not match", email, eventDetails.getOrganizerEmail());
    }

    @Test
    public void testGetModified() throws Exception {
        assertEquals("modified does not match", modified, eventDetails.getModified());
    }

    @Test
    public void testSetModified() throws Exception {
        Long modified = new Long(100);
        eventDetails.setModified(modified);
        assertEquals("modified does not match", modified, eventDetails.getModified());
    }
    @Test
    public void testGetInstitutionId() throws Exception 
    {
        assertEquals("institutionId does not match", institutionId, eventDetails.getInstitutionId());
    }

    @Test
    public void testSetInstitutionId() throws Exception {
        Long id = 3l;
        eventDetails.setInstitutionId(id);
        assertEquals("InstitutionId does not match", id, eventDetails.getInstitutionId());
    }

}