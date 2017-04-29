/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class CreatePollAnswerEventTest
{
	CreatePollAnswerEvent evt;
	PollAnswerDetails dets;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		dets=DatabaseDataFixture.populatePollAnswer1().toPollAnswerDetails();
		evt=new CreatePollAnswerEvent(dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.polls.CreatePollAnswerEvent#CreatePollAnswerEvent(com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails)}.
	 */
	@Test
	public final void testCreatePollAnswerEvent()
	{
		assertNotNull("Not yet implemented",evt);
		assertEquals(dets,evt.getDetails());
	}

}
