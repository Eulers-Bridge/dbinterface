/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class VoteReminderDeletedEventTest
{
	private Long id=45l;
	DeletedEvent evt=new VoteReminderDeletedEvent(id);
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDeletedEvent#VoteReminderDeletedEvent(java.lang.Long)}.
	 */
	@Test
	public final void testVoteReminderDeletedEvent()
	{
		assertNotNull("Not yet implemented",evt);
		assertTrue("Not yet implemented",evt.isEntityFound());
		assertTrue("Not yet implemented",evt.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDeletedEvent#getVoteReminderId()}.
	 */
	@Test
	public final void testGetVoteReminderId()
	{
		assertEquals(evt.getNodeId(),id);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDeletedEvent#isDeletionCompleted()}.
	 */
	@Test
	public final void testIsDeletionCompleted()
	{
		assertTrue("Not yet implemented",evt.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDeletedEvent#deletionForbidden(java.lang.Long)}.
	 */
	@Test
	public final void testDeletionForbidden()
	{
		evt=VoteReminderDeletedEvent.deletionForbidden(id);
		assertFalse("Not yet implemented",evt.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDeletedEvent#notFound(java.lang.Long)}.
	 */
	@Test
	public final void testNotFound()
	{
		evt=VoteReminderDeletedEvent.notFound(id);
		assertFalse("Not yet implemented",evt.isEntityFound());
	}
}
