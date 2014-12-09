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
	private Long forumQuestionId;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		forumQuestionId=43l;
		forumQuestionDetails=new ForumQuestionDetails();
		evt=new ForumQuestionCreatedEvent(forumQuestionId, forumQuestionDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionCreatedEvent#ForumQuestionCreatedEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails)}.
	 */
	@Test
	public final void testForumQuestionCreatedEventLongForumQuestionDetails()
	{
		assertNotNull(evt);
		assertEquals(evt.getForumQuestionId(),forumQuestionId);
		assertEquals(evt.getDetails(),forumQuestionDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionCreatedEvent#ForumQuestionCreatedEvent(java.lang.Long)}.
	 */
	@Test
	public final void testForumQuestionCreatedEventLong()
	{
		evt=new ForumQuestionCreatedEvent(forumQuestionId);
		assertNotNull(evt);
		assertEquals(evt.getForumQuestionId(),forumQuestionId);
		assertNull(evt.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionCreatedEvent#getForumQuestionId()}.
	 */
	@Test
	public final void testGetForumQuestionId()
	{
		assertEquals(evt.getForumQuestionId(),forumQuestionId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionCreatedEvent#setForumQuestionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetForumQuestionId()
	{
		assertEquals(evt.getForumQuestionId(),forumQuestionId);
		forumQuestionId++;
		assertNotEquals(evt.getForumQuestionId(),forumQuestionId);
		evt.setForumQuestionId(forumQuestionId);
		assertEquals(evt.getForumQuestionId(),forumQuestionId);
		
	}

}
