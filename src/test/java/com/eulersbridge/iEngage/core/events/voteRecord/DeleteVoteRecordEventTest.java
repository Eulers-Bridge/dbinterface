/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class DeleteVoteRecordEventTest
{
    final Long id = new Long(0);
    DeleteVoteRecordEvent deleteVoteRecordEvent = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		deleteVoteRecordEvent = new DeleteVoteRecordEvent(id);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.DeleteVoteRecordEvent#DeleteVoteRecordEvent(java.lang.Long)}.
	 */
	@Test
	public final void testDeleteVoteRecordEvent()
	{
        assertNotNull(deleteVoteRecordEvent);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent#getVoteReminderId()}.
	 */
	@Test
	public final void testGetVoteRecordId()
	{
        assertEquals("id does not match", id, deleteVoteRecordEvent.getNodeId());
	}
}
