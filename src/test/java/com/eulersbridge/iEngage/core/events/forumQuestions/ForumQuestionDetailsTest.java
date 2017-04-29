/**
 * 
 */
package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.ForumQuestion;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class ForumQuestionDetailsTest
{
	ForumQuestionDetails dets;
	ForumQuestion fq;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		fq=DatabaseDataFixture.populateForumQuestion1();
		dets=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails#getQuestion()}.
	 */
	@Test
	public final void testGetQuestion()
	{
		assertEquals(fq.getQuestion(),dets.getQuestion());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails#setQuestion(java.lang.String)}.
	 */
	@Test
	public final void testSetQuestion()
	{
		String question=dets.getQuestion();
		assertEquals(question,dets.getQuestion());
		question=question+'?';
		assertNotEquals(question,dets.getQuestion());
		dets.setQuestion(question);
		assertEquals(question,dets.getQuestion());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		ForumQuestionDetails fqTest=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		ForumQuestionDetails dets2=dets;
		assertEquals(fqTest.hashCode(),fqTest.hashCode());
		assertEquals(fqTest.hashCode(),dets2.hashCode());
		fqTest.setNodeId(null);
		assertNotEquals(dets2.hashCode(), fqTest.hashCode());
		assertNotEquals(fqTest.hashCode(), dets2.hashCode());
		fqTest.setNodeId(null);
		fqTest.setQuestion(null);
		assertNotEquals(dets2.hashCode(), fqTest.hashCode());
		assertNotEquals(fqTest.hashCode(), dets2.hashCode());
		fqTest.setQuestion(dets2.getQuestion());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
        ForumQuestionDetails dets1 = DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
        assertEquals("electionDetail does not match", dets1, dets);
        
        
        ForumQuestionDetails fqTest=null;
		assertNotEquals(fqTest,dets);
		assertNotEquals(dets,fqTest);
		String notElection="";
		assertNotEquals(dets,notElection);
		fqTest=DatabaseDataFixture.populateForumQuestion1().toForumQuestionDetails();
		assertEquals(fqTest,fqTest);
		assertEquals(fqTest,dets);
		fqTest.setNodeId(54l);
		assertNotEquals(dets, fqTest);
		assertNotEquals(fqTest, dets);
		dets.setNodeId(null);
		fqTest.setNodeId(null);
		assertEquals(dets, fqTest);
		assertEquals(fqTest, dets);
		fqTest.setQuestion("Something else");
		assertNotEquals(dets, fqTest);
		fqTest.setQuestion(null);
		assertNotEquals(dets, fqTest);
		assertNotEquals(fqTest, dets);
		fqTest.setQuestion(dets.getQuestion());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",dets.toString()); // TODO
	}

}
