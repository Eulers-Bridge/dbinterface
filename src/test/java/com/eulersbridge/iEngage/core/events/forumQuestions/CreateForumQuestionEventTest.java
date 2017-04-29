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
public class CreateForumQuestionEventTest
{
	CreateForumQuestionEvent evt;
	ForumQuestionDetails forumQuestionDetails;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionDetails= new ForumQuestionDetails();
		evt=new CreateForumQuestionEvent(forumQuestionDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.CreateForumQuestionEvent#CreateForumQuestionEvent(com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails)}.
	 */
	@Test
	public final void testCreateForumQuestionEvent()
	{
		assertNotNull("Not yet implemented",evt);
		assertEquals(evt.getDetails(),forumQuestionDetails);
	}

}
