/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class AddVoteRecordEventTest
{
	VoteRecordDetails voteRecordDetails;
	AddVoteRecordEvent evt;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		voteRecordDetails=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		evt=new AddVoteRecordEvent(voteRecordDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent#AddVoteRecordEvent()}.
	 */
	@Test
	public final void testAddVoteRecordEvent()
	{
		evt=new AddVoteRecordEvent();
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent#AddVoteRecordEvent(com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails)}.
	 */
	@Test
	public final void testAddVoteRecordEventVoteRecordDetails()
	{
		evt=new AddVoteRecordEvent(voteRecordDetails);
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent#getVoteRecordDetails()}.
	 */
	@Test
	public final void testGetVoteRecordDetails()
	{
		assertEquals("Not yet implemented",voteRecordDetails,evt.getVoteRecordDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent#setVoteRecordDetails(com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails)}.
	 */
	@Test
	public final void testSetVoteRecordDetails()
	{
		voteRecordDetails=DatabaseDataFixture.populateVoteRecord2().toVoteRecordDetails();
		evt.setVoteRecordDetails(voteRecordDetails);
		assertEquals("Not yet implemented",voteRecordDetails,evt.getVoteRecordDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull(evt.toString());
	}

}
