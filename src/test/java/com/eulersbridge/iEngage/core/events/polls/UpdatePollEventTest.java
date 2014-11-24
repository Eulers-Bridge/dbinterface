package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UpdatePollEventTest
{
	final Long pollId = new Long(0);
	final String question = "a question";
	final String answers = "some answers";
	final Long start = new Long(1);
	final Long duration = new Long(2);
	PollDetails pollDetails;

	UpdatePollEvent updatePollEvent;

	@Before
	public void setUp() throws Exception
	{
		pollDetails = new PollDetails();
		pollDetails.setPollId(pollId);
		pollDetails.setQuestion(question);
		pollDetails.setAnswers(answers);
		pollDetails.setStart(start);
		pollDetails.setDuration(duration);

		updatePollEvent = new UpdatePollEvent(pollId, pollDetails);
		assertNotNull("updatePollEvent is null", updatePollEvent);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void testGetPollId() throws Exception
	{
		assertEquals("pollId does not match", pollId,
				updatePollEvent.getNodeId());
	}

	@Test
	public void testGetPollDetails() throws Exception
	{
		assertEquals("pollDetails does not match", pollDetails,
				updatePollEvent.getDetails());
	}
}