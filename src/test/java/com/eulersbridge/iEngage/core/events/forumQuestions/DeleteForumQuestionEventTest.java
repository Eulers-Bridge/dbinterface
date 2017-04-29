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
public class DeleteForumQuestionEventTest
{

	Long forumQuestionId;
	DeleteForumQuestionEvent evt;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionId= 51l;
		evt=new DeleteForumQuestionEvent(forumQuestionId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.DeleteForumQuestionEvent#DeleteForumQuestionEvent(java.lang.Long)}.
	 */
	@Test
	public final void testDeleteForumQuestionEvent()
	{
		assertNotNull("Not yet implemented",evt);
		assertEquals(evt.getNodeId(),forumQuestionId);
	}

}
