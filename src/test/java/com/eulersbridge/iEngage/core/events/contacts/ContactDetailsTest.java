/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contacts;

import com.eulersbridge.iEngage.database.domain.Contact;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class ContactDetailsTest
{
	Contact testPhoto;
	ContactDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		testPhoto=DatabaseDataFixture.populateContact1();
		dets=testPhoto.toContactDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.contacts.ContactDetails#ContactDetails(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testContactDetails()
	{
		dets=new ContactDetails(testPhoto.getId(), testPhoto.getContactor().getNodeId(), testPhoto.getContactee().getNodeId(), testPhoto.getTimestamp());
		assertNotNull("Not yet implemented",dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.contacts.ContactDetails#getContactorId()}.
	 */
	@Test
	public final void testGetContactorId()
	{
		assertEquals("Not yet implemented",testPhoto.getContactor().getNodeId(),dets.getContactorId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.contacts.ContactDetails#setContactorId(java.lang.Long)}.
	 */
	@Test
	public final void testSetContactorId()
	{
		Long contactorId=324324l;
		assertNotEquals(contactorId,dets.getContactorId());
		dets.setContactorId(contactorId);
		assertEquals(contactorId,dets.getContactorId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.contacts.ContactDetails#getContacteeId()}.
	 */
	@Test
	public final void testGetContacteeId()
	{
		assertEquals("Not yet implemented",testPhoto.getContactee().getNodeId(),dets.getContacteeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.contacts.ContactDetails#setContacteeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetContacteeId()
	{
		Long contacteeId=324324l;
		assertNotEquals(contacteeId,dets.getContacteeId());
		dets.setContacteeId(contacteeId);
		assertEquals(contacteeId,dets.getContacteeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.contacts.ContactDetails#getTimestamp()}.
	 */
	@Test
	public final void testGetTimestamp()
	{
		assertEquals("Not yet implemented",testPhoto.getTimestamp(),dets.getTimestamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.contacts.ContactDetails#setTimestamp(java.lang.Long)}.
	 */
	@Test
	public final void testSetTimestamp()
	{
		Long timestamp=324324l;
		assertNotEquals(timestamp,dets.getTimestamp());
		dets.setTimestamp(timestamp);
		assertEquals(timestamp,dets.getTimestamp());
	}

}
