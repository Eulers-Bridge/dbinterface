/**
 * 
 */
package com.eulersbridge.iEngage.core.events.forumQuestions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class ForumQuestionDeletedEventTest
{
	ForumQuestionDeletedEvent evt;
	private Long forumQuestionId;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionId=41l;
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDeletedEvent#ForumQuestionDeletedEvent(java.lang.Long)}.
	 */
	@Test
	public final void testForumQuestionDeletedEvent()
	{
		evt=new ForumQuestionDeletedEvent(forumQuestionId);
		assertNotNull(evt);
		assertEquals(evt.getNodeId(),forumQuestionId);
	}

}
