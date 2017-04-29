/**
 * 
 */
package com.eulersbridge.iEngage.core.events.forumQuestions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class ForumQuestionUpdatedEventTest
{
	ForumQuestionUpdatedEvent evt;
	private Long forumQuestionId;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionId=45l;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionUpdatedEvent#ForumQuestionUpdatedEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails)}.
	 */
	@Test
	public final void testForumQuestionUpdatedEventLongForumQuestionDetails()
	{
		ForumQuestionDetails forumQuestionDetails=new ForumQuestionDetails();
		evt=new ForumQuestionUpdatedEvent(forumQuestionId, forumQuestionDetails);
		assertNotNull(evt);
		assertEquals(evt.getNodeId(),forumQuestionId);
		assertEquals(evt.getDetails(),forumQuestionDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionUpdatedEvent#ForumQuestionUpdatedEvent(java.lang.Long)}.
	 */
	@Test
	public final void testForumQuestionUpdatedEventLong()
	{
		evt=new ForumQuestionUpdatedEvent(forumQuestionId);
		assertNotNull(evt);
		assertEquals(evt.getNodeId(),forumQuestionId);
		assertNull(evt.getDetails());
	}

}
