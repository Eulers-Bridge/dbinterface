/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Greg Newitt
 *
 */
public class PollAnswerTest
{
	@Mock
	private ServletRequestAttributes attrs;
	
	PollAnswerDetails dets;
	PollAnswer answer;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		MockHttpServletRequest request=new MockHttpServletRequest();
		
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		dets=DatabaseDataFixture.populatePollAnswer1().toPollAnswerDetails();
		answer=PollAnswer.fromPollAnswerDetails(dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#fromPollAnswerDetails(com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails)}.
	 */
	@Test
	public final void testFromPollAnswerDetails()
	{
		assertEquals(dets.getNodeId(),answer.getNodeId());
		assertEquals(dets.getAnswererId(),answer.getAnswererId());
		assertEquals(dets.getPollId(),answer.getPollId());
		assertEquals(dets.getAnswerIndex(),answer.getAnswerIndex());
		assertEquals(dets.getTimeStamp(),answer.getTimeStamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#toPollAnswerDetails()}.
	 */
	@Test
	public final void testToPollAnswerDetails()
	{
		PollAnswerDetails pollAnswerDetails=answer.toPollAnswerDetails();
		assertEquals(pollAnswerDetails.getNodeId(),answer.getNodeId());
		assertEquals(pollAnswerDetails.getAnswererId(),answer.getAnswererId());
		assertEquals(pollAnswerDetails.getPollId(),answer.getPollId());
		assertEquals(pollAnswerDetails.getAnswerIndex(),answer.getAnswerIndex());
		assertEquals(pollAnswerDetails.getTimeStamp(),answer.getTimeStamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(answer.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=4325l;
		assertNotEquals(answer.getNodeId(),nodeId);
		answer.setNodeId(nodeId);
		assertEquals(answer.getNodeId(),nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#getAnswererId()}.
	 */
	@Test
	public final void testGetAnswererId()
	{
		assertEquals(answer.getAnswererId(),dets.getAnswererId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#setAnswererId(java.lang.Long)}.
	 */
	@Test
	public final void testSetAnswererId()
	{
		Long nodeId=43256l;
		assertNotEquals(answer.getAnswererId(),nodeId);
		answer.setAnswererId(nodeId);
		assertEquals(answer.getAnswererId(),nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#getPollId()}.
	 */
	@Test
	public final void testGetPollId()
	{
		assertEquals(answer.getPollId(),dets.getPollId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#setPollId(java.lang.Long)}.
	 */
	@Test
	public final void testSetPollId()
	{
		Long nodeId=43725l;
		assertNotEquals(answer.getPollId(),nodeId);
		answer.setPollId(nodeId);
		assertEquals(answer.getPollId(),nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#getAnswerIndex()}.
	 */
	@Test
	public final void testGetAnswerIndex()
	{
		assertEquals(answer.getAnswerIndex(),dets.getAnswerIndex());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#setAnswerIndex(java.lang.Integer)}.
	 */
	@Test
	public final void testSetAnswerIndex()
	{
		Integer index=2;
		assertNotEquals(answer.getAnswerIndex(),index);
		answer.setAnswerIndex(index);
		assertEquals(answer.getAnswerIndex(),index);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#getTimeStamp()}.
	 */
	@Test
	public final void testGetTimeStamp()
	{
		assertEquals(answer.getTimeStamp(),dets.getTimeStamp());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.PollAnswer#setTimeStamp(java.lang.Long)}.
	 */
	@Test
	public final void testSetTimeStamp()
	{
		Long ts=43725l;
		assertNotEquals(answer.getTimeStamp(),ts);
		answer.setTimeStamp(ts);
		assertEquals(answer.getTimeStamp(),ts);
	}

}
