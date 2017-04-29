/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class DeleteVoteReminderEventTest
{
    final Long id = new Long(0);
    DeleteVoteReminderEvent deleteVoteReminderEvent = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
    	deleteVoteReminderEvent = new DeleteVoteReminderEvent(id);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent#DeleteVoteReminderEvent(java.lang.Long)}.
	 */
	@Test
	public final void testDeleteVoteReminderEvent()
	{
        assertNotNull(deleteVoteReminderEvent);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent#getVoteReminderId()}.
	 */
	@Test
	public final void testGetVoteReminderId()
	{
        assertEquals("id does not match", id, deleteVoteReminderEvent.getNodeId());
	}
}
