/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class PollQuestionAnswerTest
{
	PollQuestionAnswer answer;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		answer=DatabaseDataFixture.populatePollAnswer1();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#PollQuestionAnswer()}.
	 */
	@Test
	public final void testPollQuestionAnswer()
	{
		answer=new PollQuestionAnswer();
		assertNotNull("Not yet implemented",answer);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#PollQuestionAnswer(com.eulersbridge.iEngage.database.domain.User, com.eulersbridge.iEngage.database.domain.Poll, java.lang.Integer)}.
	 */
	@Test
	public final void testPollQuestionAnswerUserPollInteger()
	{
		User answerer=DatabaseDataFixture.populateUserGnewitt();
		Poll poll=DatabaseDataFixture.populatePoll1();
		Integer answerIndex=3;
		answer=new PollQuestionAnswer(answerer,poll,answerIndex);
		assertNotNull("Not yet implemented",answer);
		assertEquals(answer.getAnswerer(),answerer);
		assertEquals(answer.getPoll(),poll);
		assertEquals(answer.getAnswer(),answerIndex);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#getNodeId()}.
	 */
	@Test
	public final void testGetId()
	{
		PollQuestionAnswer answer2=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(answer.getNodeId(),answer2.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetId()
	{
		PollQuestionAnswer answer2=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(answer.getNodeId(),answer2.getNodeId());
		Long id=answer.getNodeId();
		id++;
		assertNotEquals(id,answer.getNodeId());
		answer.setNodeId(id);
		assertEquals(id, answer.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#getAnswerer()}.
	 */
	@Test
	public final void testGetAnswerer()
	{
		PollQuestionAnswer answer2=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(answer.getAnswerer(),answer2.getAnswerer());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#setAnswerer(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testSetAnswerer()
	{
		PollQuestionAnswer answer2=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(answer.getAnswerer(),answer2.getAnswerer());
		User answerer=DatabaseDataFixture.populateUserGnewitt2();
		assertNotEquals(answerer,answer.getAnswerer());
		answer.setAnswerer(answerer);
		assertEquals(answerer, answer.getAnswerer());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#getPoll()}.
	 */
	@Test
	public final void testGetPoll()
	{
		PollQuestionAnswer answer2=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(answer.getPoll(),answer2.getPoll());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#setPoll(com.eulersbridge.iEngage.database.domain.Poll)}.
	 */
	@Test
	public final void testSetPoll()
	{
		PollQuestionAnswer answer2=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(answer.getPoll(),answer2.getPoll());
		Poll poll=DatabaseDataFixture.populatePoll2();
		assertNotEquals(poll,answer.getPoll());
		answer.setPoll(poll);
		assertEquals(poll, answer.getPoll());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#getTimeStamp()}.
	 */
	@Test
	public final void testGetTimeStamp()
	{
		assertEquals(answer.getTimeStamp(),answer.getTimeStamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#setTimeStamp(java.lang.Long)}.
	 */
	@Test
	public final void testSetTimeStamp()
	{
		Long timestamp=answer.getTimeStamp();
		timestamp++;
		assertNotEquals(timestamp,answer.getTimeStamp());
		answer.setTimeStamp(timestamp);
		assertEquals(timestamp, answer.getTimeStamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#getAnswer()}.
	 */
	@Test
	public final void testGetAnswer()
	{
		PollQuestionAnswer answer2=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(answer.getAnswer(),answer2.getAnswer());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#setAnswer(java.lang.Integer)}.
	 */
	@Test
	public final void testSetAnswer()
	{
		PollQuestionAnswer answer2=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(answer.getAnswer(),answer2.getAnswer());
		Integer answerIndex=answer.getAnswer();
		answerIndex++;
		assertNotEquals(answerIndex,answer.getAnswer());
		answer.setAnswer(answerIndex);
		assertEquals(answerIndex, answer.getAnswer());
	}
	
	@Test
	public final void testToPollAnswerDetails()
	{
		PollAnswerDetails dets=answer.toPollAnswerDetails();
		assertEquals(answer.getAnswer(),dets.getAnswerIndex());
		assertEquals(answer.getAnswerer().getNodeId(),dets.getAnswererId());
		assertEquals(answer.getPoll().getNodeId(),dets.getPollId());
		assertEquals(answer.getNodeId(),dets.getNodeId());
		assertEquals(answer.getTimeStamp(),dets.getTimeStamp());
	}

	@Test
	public final void testFromPollAnswerDetails()
	{
		PollQuestionAnswer answer2=PollQuestionAnswer.fromPollAnswerDetails(answer.toPollAnswerDetails());
		assertEquals(answer.getAnswer(),answer2.getAnswer());
		assertEquals(answer.getAnswerer().getNodeId(),answer2.getAnswerer().getNodeId());
		assertEquals(answer.getPoll().getNodeId(),answer2.getPoll().getNodeId());
		assertEquals(answer.getNodeId(),answer2.getNodeId());
		assertEquals(answer.getTimeStamp(),answer2.getTimeStamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",answer.toString());
	}

	private void checkHashCode(PollQuestionAnswer test1,PollQuestionAnswer test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(PollQuestionAnswer test1,PollQuestionAnswer test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		PollQuestionAnswer pollAnswerTest=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(pollAnswerTest.hashCode(),pollAnswerTest.hashCode());
		assertEquals(pollAnswerTest.hashCode(),answer.hashCode());
		pollAnswerTest.setNodeId(null);
		checkHashCode(answer,pollAnswerTest);
		answer.setNodeId(null);
		pollAnswerTest.setAnswerer(null);;
		checkHashCode(answer,pollAnswerTest);
		pollAnswerTest.setAnswerer(answer.getAnswerer());
		pollAnswerTest.setPoll(null);
		checkHashCode(answer,pollAnswerTest);
		pollAnswerTest.setPoll(answer.getPoll());
		pollAnswerTest.setAnswer(null);;
		checkHashCode(answer,pollAnswerTest);
		pollAnswerTest.setAnswer(answer.getAnswer());
		pollAnswerTest.setTimeStamp(null);;
		checkHashCode(answer,pollAnswerTest);
		pollAnswerTest.setTimeStamp(answer.getTimeStamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PollQuestionAnswer#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		PollQuestionAnswer pollAnswerTest=null;
		assertNotEquals(pollAnswerTest,answer);
		assertNotEquals(answer,pollAnswerTest);
		String notElection="";
		assertNotEquals(answer,notElection);
		pollAnswerTest=DatabaseDataFixture.populatePollAnswer1();
		assertEquals(pollAnswerTest,pollAnswerTest);
		assertEquals(pollAnswerTest,answer);
		pollAnswerTest.setNodeId(54l);
		checkNotEquals(answer,pollAnswerTest);
		answer.setNodeId(null);
		checkNotEquals(answer,pollAnswerTest);
		pollAnswerTest.setNodeId(null);
		pollAnswerTest.setTimeStamp(answer.getTimeStamp());
		assertEquals(answer, pollAnswerTest);
		assertEquals(pollAnswerTest, answer);
		pollAnswerTest.setTimeStamp(4321l);
		assertNotEquals(answer, pollAnswerTest);
		pollAnswerTest.setTimeStamp(null);
		checkNotEquals(answer, pollAnswerTest);
		pollAnswerTest.setTimeStamp(answer.getTimeStamp());
		
		pollAnswerTest.setAnswerer(DatabaseDataFixture.populateUserGnewitt2());
		assertNotEquals(answer, pollAnswerTest);
		pollAnswerTest.setAnswerer(null);
		checkNotEquals(pollAnswerTest, answer);
		pollAnswerTest.setAnswerer(answer.getAnswerer());
		
		pollAnswerTest.setPoll(DatabaseDataFixture.populatePoll2());
		assertNotEquals(answer, pollAnswerTest);
		pollAnswerTest.setPoll(null);
		checkNotEquals(answer, pollAnswerTest);
		pollAnswerTest.setPoll(answer.getPoll());
		
		pollAnswerTest.setAnswer(2);
		assertNotEquals(answer, pollAnswerTest);
		pollAnswerTest.setAnswer(null);
		checkNotEquals(answer, pollAnswerTest);
		pollAnswerTest.setAnswer(answer.getAnswer());
		}

}
