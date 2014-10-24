/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class VoteRecordDeletedEventTest
{
	private Long id=45l;
	VoteRecordDeletedEvent evt=new VoteRecordDeletedEvent(id);
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteRecordDeletedEvent#VoteRecordDeletedEvent(java.lang.Long)}.
	 */
	@Test
	public final void testVoteRecordDeletedEvent()
	{
		assertNotNull("Not yet implemented",evt);
		assertTrue("Not yet implemented",evt.isEntityFound());
		assertTrue("Not yet implemented",evt.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteRecordDeletedEvent#getVoteRecordId()}.
	 */
	@Test
	public final void testGetVoteRecordId()
	{
		assertEquals(evt.getVoteRecordId(),id);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteRecordDeletedEvent#isDeletionCompleted()}.
	 */
	@Test
	public final void testIsDeletionCompleted()
	{
		assertTrue("Not yet implemented",evt.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteRecordDeletedEvent#deletionForbidden(java.lang.Long)}.
	 */
	@Test
	public final void testDeletionForbidden()
	{
		evt=VoteRecordDeletedEvent.deletionForbidden(id);
		assertFalse("Not yet implemented",evt.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteReminder.VoteRecordDeletedEvent#notFound(java.lang.Long)}.
	 */
	@Test
	public final void testNotFound()
	{
		evt=VoteRecordDeletedEvent.notFound(id);
		assertFalse("Not yet implemented",evt.isEntityFound());
	}
}
