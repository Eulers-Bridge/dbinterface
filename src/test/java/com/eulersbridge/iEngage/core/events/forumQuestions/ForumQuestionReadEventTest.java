/**
 * 
 */
package com.eulersbridge.iEngage.core.events.forumQuestions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class ForumQuestionReadEventTest
{
	ForumQuestionReadEvent evt;
	private Long forumQuestionId;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionId=49l;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionReadEvent#ForumQuestionReadEvent(java.lang.Long)}.
	 */
	@Test
	public final void testForumQuestionReadEventLong()
	{
		ForumQuestionDetails forumQuestionDetails=new ForumQuestionDetails();
		evt=new ForumQuestionReadEvent(forumQuestionId, forumQuestionDetails);
		assertNotNull(evt);
		assertEquals(evt.getNodeId(),forumQuestionId);
		assertEquals(evt.getDetails(),forumQuestionDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionReadEvent#ForumQuestionReadEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails)}.
	 */
	@Test
	public final void testForumQuestionReadEventLongForumQuestionDetails()
	{
		evt=new ForumQuestionReadEvent(forumQuestionId);
		assertNotNull(evt);
		assertEquals(evt.getNodeId(),forumQuestionId);
		assertNull(evt.getDetails());
	}

}
