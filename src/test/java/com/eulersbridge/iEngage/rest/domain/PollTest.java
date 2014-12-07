/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class PollTest
{
	@Mock
	private ServletRequestAttributes attrs;
	
	Poll poll;
	PollDetails dets;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		MockHttpServletRequest request=new MockHttpServletRequest();
		
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		dets=DatabaseDataFixture.populatePoll1().toPollDetails();
		poll=Poll.fromPollDetails(dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#fromPollDetails(com.eulersbridge.iEngage.core.events.polls.PollDetails)}.
	 */
	@Test
	public final void testFromPollDetails()
	{
		assertEquals(dets.getQuestion(),poll.getQuestion());
		assertEquals(dets.getAnswers(),poll.getAnswers());
		assertEquals(dets.getNodeId(),poll.getNodeId());
		assertEquals(dets.getStart(),poll.getStart());
		assertEquals(dets.getDuration(),poll.getDuration());
		assertEquals(dets.getOwnerId(),poll.getOwnerId());
		assertEquals(dets.getCreatorId(),poll.getCreatorId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#toPollDetails()}.
	 */
	@Test
	public final void testToPollDetails()
	{
		PollDetails pollDetails=poll.toPollDetails();
		assertEquals(pollDetails.getQuestion(),poll.getQuestion());
		assertEquals(pollDetails.getAnswers(),poll.getAnswers());
		assertEquals(pollDetails.getNodeId(),poll.getNodeId());
		assertEquals(pollDetails.getStart(),poll.getStart());
		assertEquals(pollDetails.getDuration(),poll.getDuration());
		assertEquals(pollDetails.getOwnerId(),poll.getOwnerId());
		assertEquals(pollDetails.getCreatorId(),poll.getCreatorId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#toPollsIterator(java.util.Iterator)}.
	 */
	@Test
	public final void testToPollsIterator()
	{
		ArrayList <PollDetails> detIter=new ArrayList<PollDetails>();
		detIter.add(DatabaseDataFixture.populatePoll1().toPollDetails());
		detIter.add(DatabaseDataFixture.populatePoll2().toPollDetails());
		Iterator<Poll> pollIter=Poll.toPollsIterator(null);
		assertNull(pollIter);
		pollIter=Poll.toPollsIterator(detIter.iterator());
		assertNotNull(pollIter);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(poll.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=4325l;
		assertNotEquals(poll.getNodeId(),nodeId);
		poll.setNodeId(nodeId);
		assertEquals(poll.getNodeId(),nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#getQuestion()}.
	 */
	@Test
	public final void testGetQuestion()
	{
		assertEquals(poll.getQuestion(),dets.getQuestion());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#setQuestion(java.lang.String)}.
	 */
	@Test
	public final void testSetQuestion()
	{
		String question="Other Question";
		assertNotEquals(poll.getQuestion(),question);
		poll.setQuestion(question);
		assertEquals(poll.getQuestion(),question);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#getAnswers()}.
	 */
	@Test
	public final void testGetAnswers()
	{
		assertEquals(poll.getAnswers(),dets.getAnswers());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#setAnswers(java.lang.String)}.
	 */
	@Test
	public final void testSetAnswers()
	{
		String answers="Other Answers";
		assertNotEquals(poll.getAnswers(),answers);
		poll.setAnswers(answers);
		assertEquals(poll.getAnswers(),answers);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#getStart()}.
	 */
	@Test
	public final void testGetStart()
	{
		assertEquals(poll.getStart(),dets.getStart());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#setStart(java.lang.Long)}.
	 */
	@Test
	public final void testSetStart()
	{
		Long start=4325l;
		assertNotEquals(poll.getStart(),start);
		poll.setStart(start);
		assertEquals(poll.getStart(),start);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#getDuration()}.
	 */
	@Test
	public final void testGetDuration()
	{
		assertEquals(poll.getDuration(),dets.getDuration());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#setDuration(java.lang.Long)}.
	 */
	@Test
	public final void testSetDuration()
	{
		Long duration=4325l;
		assertNotEquals(poll.getDuration(),duration);
		poll.setDuration(duration);
		assertEquals(poll.getDuration(),duration);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#getOwnerId()}.
	 */
	@Test
	public final void testGetOwnerId()
	{
		assertEquals(poll.getOwnerId(),dets.getOwnerId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#setOwnerId(java.lang.Long)}.
	 */
	@Test
	public final void testSetOwnerId()
	{
		Long ownerId=4325l;
		assertNotEquals(poll.getOwnerId(),ownerId);
		poll.setOwnerId(ownerId);
		assertEquals(poll.getOwnerId(),ownerId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#getCreatorId()}.
	 */
	@Test
	public final void testGetCreatorId()
	{
		assertEquals(poll.getCreatorId(),dets.getCreatorId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Poll#setCreatorId(java.lang.Long)}.
	 */
	@Test
	public final void testSetCreatorId()
	{
		Long creatorId=4325l;
		assertNotEquals(poll.getCreatorId(),creatorId);
		poll.setCreatorId(creatorId);
		assertEquals(poll.getCreatorId(),creatorId);
	}

}
