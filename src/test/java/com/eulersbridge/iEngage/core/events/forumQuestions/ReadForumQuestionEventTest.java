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
public class ReadForumQuestionEventTest
{
	Long forumQuestionId;
	ReadForumQuestionEvent evt;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionId= 51l;
		evt=new ReadForumQuestionEvent(forumQuestionId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ReadForumQuestionEvent#ReadForumQuestionEvent(java.lang.Long)}.
	 */
	@Test
	public final void testReadForumQuestionEvent()
	{
		assertNotNull("Not yet implemented",evt);
		assertEquals(evt.getNodeId(),forumQuestionId);
	}

}
