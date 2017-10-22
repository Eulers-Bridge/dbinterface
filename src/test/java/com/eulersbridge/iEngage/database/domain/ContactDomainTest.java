/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class ContactDomainTest
{
	private Contact contact;
	private ContactDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		contact=DatabaseDataFixture.populateContact1();
		dets=contact.toContactDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#Contact()}.
	 */
	@Test
	public final void testContact()
	{
		Contact contactTest=new Contact();
		assertEquals("contactTest not of Contact class",contactTest.getClass(),Contact.class);
	}

	private void checkHashCode(Contact test1,Contact test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(Contact test1,Contact test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	


	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#toContactDetails()}.
	 */
	@Test
	public final void testToContactDetails()
	{
		ContactDetails dets=contact.toContactDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),ContactDetails.class);
		assertEquals("",contact.getId(),dets.getNodeId());
		assertEquals("",contact.getContactee().getNodeId(),dets.getContacteeId());
		assertEquals("",contact.getContactor().getNodeId(),dets.getContactorId());
		assertEquals("",contact.getTimestamp(),dets.getTimestamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#getId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals("",contact.getId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#setId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long id=535l;
		assertNotEquals("",contact.getId(),id);
		contact.setId(id);
		assertEquals("",id,contact.getId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#getTimestamp()}.
	 */
	@Test
	public final void testGetTimestamp()
	{
		assertEquals("",contact.getTimestamp(),dets.getTimestamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#setTimestamp(java.lang.Long)}.
	 */
	@Test
	public final void testSetTimestamp()
	{
		Long reqDate=53535353l;
		assertNotEquals("",contact.getTimestamp(),reqDate);
		contact.setTimestamp(reqDate);
		assertEquals("",reqDate,contact.getTimestamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#getContactor()}.
	 */
	@Test
	public final void testGetContactor()
	{
		assertEquals("",contact.getContactor().getNodeId(),dets.getContactorId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#setContactor(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testSetContactor()
	{
		User reqDate=DatabaseDataFixture.populateUserGnewitt2();
		assertNotEquals("",contact.getContactor(),reqDate);
		contact.setContactor(reqDate);
		assertEquals("",reqDate,contact.getContactor());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#getContactee()}.
	 */
	@Test
	public final void testGetContactee()
	{
		assertEquals("",contact.getContactee().getNodeId(),dets.getContacteeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#setContactee(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testSetContactee()
	{
		User reqDate=DatabaseDataFixture.populateUserGnewitt();
		assertNotEquals("",contact.getContactee(),reqDate);
		contact.setContactee(reqDate);
		assertEquals("",reqDate,contact.getContactee());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",contact.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Contact contactTest=DatabaseDataFixture.populateContact1();
		assertEquals(contactTest.hashCode(),contactTest.hashCode());
		contactTest.setId(null);
		checkHashCode(contact,contactTest);
		contact.setId(null);
		
		contactTest.setContactee(null);
		checkHashCode(contact,contactTest);
		contactTest.setContactee(contact.getContactee());
		
		contactTest.setContactor(null);
		checkHashCode(contact,contactTest);
		contactTest.setContactor(contact.getContactor());
		
		contactTest.setTimestamp(null);
		checkHashCode(contact,contactTest);
		contactTest.setTimestamp(contact.getTimestamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Contact#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		Contact contactTest=null;
		assertNotEquals(contactTest,contact);
		assertNotEquals(contact,contactTest);
		String notElection="";
		assertNotEquals(contact,notElection);
		contactTest=DatabaseDataFixture.populateContact1();
		contactTest.setTimestamp(contact.getTimestamp());
		assertEquals(contactTest,contactTest);

		contactTest.setId(54l);
		checkNotEquals(contact,contactTest);
		contact.setId(null);
		checkNotEquals(contact,contactTest);
		contactTest.setId(null);
		

		contactTest.setContactor(DatabaseDataFixture.populateUserGnewitt2());
		assertNotEquals(contact, contactTest);
		contactTest.setContactor(null);
		checkNotEquals(contactTest, contact);
		contactTest.setContactor(contact.getContactor());
		
		contactTest.setContactee(DatabaseDataFixture.populateUserGnewitt());
		assertNotEquals(contact, contactTest);
		contactTest.setContactee(null);
		checkNotEquals(contactTest, contact);
		contactTest.setContactee(contact.getContactee());
		
		contactTest.setTimestamp(523453l);
		assertNotEquals(contact, contactTest);
		contactTest.setTimestamp(null);
		checkNotEquals(contact, contactTest);
		contactTest.setTimestamp(contact.getTimestamp());
	}

}
