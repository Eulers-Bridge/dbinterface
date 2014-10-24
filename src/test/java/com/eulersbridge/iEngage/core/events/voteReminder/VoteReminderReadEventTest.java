package com.eulersbridge.iEngage.core.events.voteReminder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */

public class VoteReminderReadEventTest {
    Long voteReminderId = new Long(1);
    VoteReminderDetails voteReminderDetails;
    VoteReminderReadEvent voteReminderReadEvent = null;

    @Before
    public void setUp() throws Exception {
        voteReminderDetails = DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
        voteReminderId=voteReminderDetails.getNodeId();
        voteReminderReadEvent = new VoteReminderReadEvent(voteReminderId, voteReminderDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testVoteReminderReadEvent() throws Exception {
        assertNotNull("voteReminderReadEvent is null", voteReminderReadEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("voteReminderId does not match", voteReminderId, voteReminderReadEvent.getVoteReminderId());
    }

    @Test
    public void testGetReadNewsArticleDetails() throws Exception {
        assertEquals("voteReminderDetails does not match", voteReminderDetails, voteReminderReadEvent.getVoteReminderDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        VoteReminderReadEvent voteReminderReadEvent1 = VoteReminderReadEvent.notFound(voteReminderId);
        assertNotNull("notFound() returns null", voteReminderReadEvent1);
        assertFalse("entityFound is not false", voteReminderReadEvent1.isEntityFound());
    }
}