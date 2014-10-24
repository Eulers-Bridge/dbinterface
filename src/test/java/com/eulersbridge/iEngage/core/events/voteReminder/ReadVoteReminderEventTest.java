package com.eulersbridge.iEngage.core.events.voteReminder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */

public class ReadVoteReminderEventTest 
{
    final Long voteReminderId = new Long(0);
    ReadVoteReminderEvent readVoteReminderEvent = null;

    @Before
    public void setUp() throws Exception {
    	readVoteReminderEvent = new ReadVoteReminderEvent(voteReminderId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestReadNewsArticleEvent() throws Exception {
        assertNotNull("requestReadNewsArticleEvent is null", readVoteReminderEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception 
    {
        assertEquals("voteReminderId does not match", voteReminderId, readVoteReminderEvent.getVoteReminderId());
    }
}