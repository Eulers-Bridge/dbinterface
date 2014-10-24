/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class AddVoteReminderEventTest
{
	VoteReminderDetails voteRecordDetails;
	AddVoteReminderEvent evt;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		voteRecordDetails=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		evt=new AddVoteReminderEvent(voteRecordDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteReminderEvent#AddVoteReminderEvent()}.
	 */
	@Test
	public final void testAddVoteReminderEvent()
	{
		evt=new AddVoteReminderEvent();
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteReminderEvent#AddVoteReminderEvent(com.eulersbridge.iEngage.core.events.voteRecord.VoteReminderDetails)}.
	 */
	@Test
	public final void testAddVoteReminderEventVoteReminderDetails()
	{
		evt=new AddVoteReminderEvent(voteRecordDetails);
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteReminderEvent#getVoteReminderDetails()}.
	 */
	@Test
	public final void testGetVoteReminderDetails()
	{
		assertEquals("Not yet implemented",voteRecordDetails,evt.getVoteReminderDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteReminderEvent#setVoteReminderDetails(com.eulersbridge.iEngage.core.events.voteRecord.VoteReminderDetails)}.
	 */
	@Test
	public final void testSetVoteReminderDetails()
	{
		voteRecordDetails=DatabaseDataFixture.populateVoteReminder2().toVoteReminderDetails();
		evt.setVoteReminderDetails(voteRecordDetails);
		assertEquals("Not yet implemented",voteRecordDetails,evt.getVoteReminderDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteReminderEvent#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull(evt.toString());
	}

}
