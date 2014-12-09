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
public class UpdateForumQuestionEventTest
{
	UpdateForumQuestionEvent evt;
	private Long forumQuestionId;
	private ForumQuestionDetails forumQuestionDetails;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionId= 51l;
		forumQuestionDetails= new ForumQuestionDetails();
		evt=new UpdateForumQuestionEvent(forumQuestionId, forumQuestionDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.UpdateForumQuestionEvent#UpdateForumQuestionEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails)}.
	 */
	@Test
	public final void testUpdateForumQuestionEvent()
	{
		assertNotNull("Not yet implemented",evt);
		assertEquals(evt.getNodeId(),forumQuestionId);
		assertEquals(evt.getDetails(),forumQuestionDetails);
	}

}
