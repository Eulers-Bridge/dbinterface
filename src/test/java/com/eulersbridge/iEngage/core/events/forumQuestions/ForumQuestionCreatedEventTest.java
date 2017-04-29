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
