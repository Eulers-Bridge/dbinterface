/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.events.EventDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class EventTest
{

	private Event event;
	private EventDetails dets;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		event=DatabaseDataFixture.populateEvent1();
		dets=event.toEventDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#Event()}.
	 */
	@Test
	public final void testEvent()
	{
		Event eventTest=new Event();
		assertEquals("eventTest not of Event class",eventTest.getClass(),Event.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#fromEventDetails(com.eulersbridge.iEngage.core.events.events.EventDetails)}.
	 */
	@Test
	public final void testFromEventDetails()
	{
		Event evt2=Event.fromEventDetails(dets);
		assertEquals(dets.getName(),evt2.getName());
		assertEquals(dets.getEventId(),evt2.getEventId());
		assertEquals(dets.getCreated(),evt2.getCreated());
		assertEquals(dets.getDescription(),evt2.getDescription());
		assertEquals(dets.getEnds(),evt2.getEnds());
		assertEquals(dets.getInstitutionId(),evt2.getNewsFeed().getInstitution().getNodeId());
		assertEquals(dets.getLocation(),evt2.getLocation());
		assertEquals(dets.getModified(),evt2.getModified());
		assertEquals(dets.getOrganizer(),evt2.getOrganizer());
		assertEquals(dets.getOrganizerEmail(),evt2.getOrganizerEmail());
		assertNull(evt2.getPhotos());
		assertEquals(dets.getStarts(),evt2.getStarts());
		assertArrayEquals(dets.getVolunteerPositions(),evt2.getVolunteerPositions());
		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#toEventDetails()}.
	 */
	@Test
	public final void testToEventDetails()
	{
		EventDetails dets2=event.toEventDetails();
		assertEquals(dets2.getName(),event.getName());
		assertEquals(dets2.getEventId(),event.getEventId());
		assertEquals(dets2.getCreated(),event.getCreated());
		assertEquals(dets2.getDescription(),event.getDescription());
		assertEquals(dets2.getEnds(),event.getEnds());
		assertEquals(dets2.getInstitutionId(),event.getNewsFeed().getInstitution().getNodeId());
		assertEquals(dets2.getLocation(),event.getLocation());
		assertEquals(dets2.getModified(),event.getModified());
		assertEquals(dets2.getOrganizer(),event.getOrganizer());
		assertEquals(dets2.getOrganizerEmail(),event.getOrganizerEmail());
//TODO		assertEquals(dets2.getPhotos(),event.getPhotos());
		assertEquals(dets2.getStarts(),event.getStarts());
		assertArrayEquals(dets2.getVolunteerPositions(),event.getVolunteerPositions());
		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",event.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getEventId()}.
	 */
	@Test
	public final void testGetEventId()
	{
		assertEquals(dets.getEventId(),event.getEventId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setEventId(java.lang.Long)}.
	 */
	@Test
	public final void testSetEventId()
	{
		Long id=34l;
		event.setEventId(id);
		assertEquals(id, event.getEventId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getName()}.
	 */
	@Test
	public final void testGetName()
	{
		assertEquals(dets.getName(),event.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName()
	{
		String name="Some other name.";
		event.setName(name);
		assertEquals(name, event.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getLocation()}.
	 */
	@Test
	public final void testGetLocation()
	{
		assertEquals(dets.getLocation(),event.getLocation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setLocation(java.lang.String)}.
	 */
	@Test
	public final void testSetLocation()
	{
		String location="Some other location.";
		event.setLocation(location);
		assertEquals(location, event.getLocation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getStarts()}.
	 */
	@Test
	public final void testGetStarts() {
		assertEquals(dets.getStarts(),event.getStarts());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setStarts(java.lang.Long)}.
	 */
	@Test
	public final void testSetStarts() {
		Long starts=34534345l;
		event.setStarts(starts);
		assertEquals(starts, event.getStarts());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getEnds()}.
	 */
	@Test
	public final void testGetEnds() {
		assertEquals(dets.getEnds(),event.getEnds());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setEnds(java.lang.Long)}.
	 */
	@Test
	public final void testSetEnds() {
		Long ends=34534345l;
		event.setEnds(ends);
		assertEquals(ends, event.getEnds());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getDescription()}.
	 */
	@Test
	public final void testGetDescription() {
		assertEquals(dets.getDescription(),event.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		String desc="Some other description.";
		event.setDescription(desc);
		assertEquals(desc, event.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getPhotos()}.
	 */
//TODO
	/*
	@Test
	public final void testGetPhotos()
	{
		String[] expecteds = dets.getPhotos();
		String[] actuals = event.getPhotos();
		assertArrayEquals(expecteds, actuals);
	}
*/
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setPhotos(java.lang.String[])}.
	 */
	//TODO
	/*
	@Test
	public final void testSetPhotos() {
		String[] actuals = {"Picture1.jpg","Picture2.jpg"};
		event.setPhotos(actuals);
		assertArrayEquals(actuals, event.getPhotos());
	}
*/
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getVolunteerPositions()}.
	 */
	@Test
	public final void testGetVolunteerPositions()
	{
		String[] expecteds = dets.getVolunteerPositions();
		String[] actuals = event.getVolunteerPositions();
		assertArrayEquals(expecteds, actuals);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setVolunteerPositions(java.lang.String[])}.
	 */
	@Test
	public final void testSetVolunteerPositions()
	{
		String[] actuals = {"Bouncer","Barman"};
		event.setVolunteerPositions(actuals);
		assertArrayEquals(actuals, event.getVolunteerPositions());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getCreated()}.
	 */
	@Test
	public final void testGetCreated() {
		assertEquals(dets.getCreated(),event.getCreated());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setCreated(java.lang.Long)}.
	 */
	@Test
	public final void testSetCreated() {
		Long created=34534345l;
		event.setCreated(created);
		assertEquals(created, event.getCreated());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getOrganizer()}.
	 */
	@Test
	public final void testGetOrganizer()
	{
		assertEquals(dets.getOrganizer(),event.getOrganizer());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setOrganizer(java.lang.String)}.
	 */
	@Test
	public final void testSetOrganizer() {
		String organizer="Some other organizer.";
		event.setOrganizer(organizer);
		assertEquals(organizer, event.getOrganizer());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getOrganizerEmail()}.
	 */
	@Test
	public final void testGetOrganizerEmail() {
		assertEquals(dets.getOrganizerEmail(),event.getOrganizerEmail());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setOrganizerEmail(java.lang.String)}.
	 */
	@Test
	public final void testSetOrganizerEmail() {
		String organizer="Some other organizer.";
		event.setOrganizerEmail(organizer);
		assertEquals(organizer, event.getOrganizerEmail());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getModified()}.
	 */
	@Test
	public final void testGetModified() {
		assertEquals(dets.getModified(),event.getModified());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setModified(java.lang.Long)}.
	 */
	@Test
	public final void testSetModified()
	{
		Long modified=34534345l;
		event.setModified(modified);
		assertEquals(modified, event.getModified());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#getInstitution()}.
	 */
	@Test
	public final void testGetInstitution() {
		assertEquals(dets.getInstitutionId(),event.getNewsFeed().getInstitution().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Event#setInstitution(com.eulersbridge.iEngage.database.domain.Institution)}.
	 */
	@Test
	public final void testSetInstitution()
	{
		Institution institution=DatabaseDataFixture.populateInstUniMelb();
		NewsFeed newsFeed=DatabaseDataFixture.populateNewsFeed1();
		event.setNewsFeed(newsFeed);
		assertEquals(institution, event.getNewsFeed().getInstitution());
	}

	private void checkHashCode(Event test1,Event test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(Event test1,Event test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Event eventTest=DatabaseDataFixture.populateEvent1();
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
		eventTest.setNewsFeed(null);
		checkHashCode(event,eventTest);
		eventTest.setNewsFeed(event.getNewsFeed());
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
	public final void testEquals()
	{
		Event eventTest=null;
		assertNotEquals(eventTest,event);
		assertNotEquals(event,eventTest);
		String notElection="";
		assertNotEquals(event,notElection);
		eventTest=DatabaseDataFixture.populateEvent1();
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
//TODO		
/*		Iterable<Photos> picture={"picture1","picture2"};
		eventTest.setPhotos(picture);
		checkNotEquals(event, eventTest);
		eventTest.setPhotos(event.getPhotos());
*/		
		String[] volunteerPositions = {"position 1","position 2"};
		eventTest.setVolunteerPositions(volunteerPositions);
		checkNotEquals(event, eventTest);
		eventTest.setVolunteerPositions(event.getVolunteerPositions());
		
		eventTest.setNewsFeed(new NewsFeed());
		assertNotEquals(event, eventTest);
		eventTest.setNewsFeed(null);
		assertNotEquals(event, eventTest);
		assertNotEquals(eventTest, event);
		eventTest.setNewsFeed(event.getNewsFeed());
	}

}
