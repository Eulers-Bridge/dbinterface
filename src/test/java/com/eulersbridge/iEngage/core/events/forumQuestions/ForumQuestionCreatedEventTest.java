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
public class ForumQuestionCreatedEventTest
{
	ForumQuestionCreatedEvent evt;
	private ForumQuestionDetails forumQuestionDetails;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionDetails=new ForumQuestionDetails();
		evt=new ForumQuestionCreatedEvent(forumQuestionDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionCreatedEvent#ForumQuestionCreatedEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails)}.
	 */
	@Test
	public final void testForumQuestionCreatedEventForumQuestionDetails()
	{
		assertNotNull(evt);
		assertEquals(evt.getDetails(),forumQuestionDetails);
	}
}
