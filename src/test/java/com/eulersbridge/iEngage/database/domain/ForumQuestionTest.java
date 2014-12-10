/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class ForumQuestionTest
{
	ForumQuestion fq;
	String question;
	Long nodeId;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		fq=DatabaseDataFixture.populateForumQuestion1();
		question=fq.getQuestion();
		nodeId=fq.getForumQuestionId();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.ForumQuestion#ForumQuestion()}.
	 */
	@Test
	public final void testForumQuestion()
	{
		fq=new ForumQuestion();
		assertNotNull("Not yet implemented",fq);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.ForumQuestion#fromForumQuestionDetails(com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails)}.
	 */
	@Test
	public final void testFromForumQuestionDetails()
	{
		ForumQuestion fq2=ForumQuestion.fromForumQuestionDetails(fq.toForumQuestionDetails());
		assertEquals(fq.getForumQuestionId(),fq2.getForumQuestionId());
		assertEquals(fq.getQuestion(),fq2.getQuestion());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.ForumQuestion#toForumQuestionDetails()}.
	 */
	@Test
	public final void testToForumQuestionDetails()
	{
		ForumQuestionDetails fqd=fq.toForumQuestionDetails();
		assertEquals(fq.getForumQuestionId(),fqd.getNodeId());
		assertEquals(fq.getQuestion(),fqd.getQuestion());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.ForumQuestion#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",fq.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.ForumQuestion#getForumQuestionId()}.
	 */
	@Test
	public final void testGetForumQuestionId()
	{
		assertEquals(fq.getForumQuestionId(),nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.ForumQuestion#setForumQuestionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetForumQuestionId()
	{
		assertEquals(fq.getForumQuestionId(),nodeId);
		nodeId++;
		assertNotEquals(fq.getForumQuestionId(),nodeId);
		fq.setForumQuestionId(nodeId);
		assertEquals(fq.getForumQuestionId(),nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.ForumQuestion#getQuestion()}.
	 */
	@Test
	public final void testGetQuestion()
	{
		assertEquals(fq.getQuestion(),question);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.ForumQuestion#setQuestion(java.lang.String)}.
	 */
	@Test
	public final void testSetQuestion()
	{
		assertEquals(fq.getQuestion(),question);
		question="Some other question we haven't seen yet.";
		assertNotEquals(fq.getQuestion(),question);
		fq.setQuestion(question);
		assertEquals(fq.getQuestion(),question);
	}

}
