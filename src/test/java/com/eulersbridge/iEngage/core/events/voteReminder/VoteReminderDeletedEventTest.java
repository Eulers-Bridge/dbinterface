/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class VoteReminderDeletedEventTest
{
	private Long id=45l;
	VoteReminderDeletedEvent evt=new VoteReminderDeletedEvent(id);
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
		assertEquals(evt.getVoteReminderId(),id);
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
