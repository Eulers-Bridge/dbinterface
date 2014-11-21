package com.eulersbridge.iEngage.core.events.polls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class PollDeletedEventTest {
    final Long pollId = new Long(0);
    DeletedEvent pollDeletedEvent;

    @Before
    public void setUp() throws Exception {
        pollDeletedEvent = new PollDeletedEvent(pollId);
        assertNotNull("pollDeletedEvent is null", pollDeletedEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDeletionForbidden() throws Exception {
        DeletedEvent pollDeletedEvent1 = PollDeletedEvent.deletionForbidden(pollId);
        assertNotNull("deletionForbidden() returns null", pollDeletedEvent1);
        assertFalse("deletionCompleted is not false", pollDeletedEvent1.isDeletionCompleted());
    }

    @Test
    public void testNotFound() throws Exception {
        DeletedEvent pollDeletedEvent1 = PollDeletedEvent.notFound(pollId);
        assertNotNull("notFound() returns null", pollDeletedEvent1);
        assertFalse("entityFound is not false", pollDeletedEvent1.isEntityFound());
        assertFalse("deletionCompleted is not false", pollDeletedEvent1.isDeletionCompleted());
    }

    @Test
    public void testIsDeletionCompleted() throws Exception {
        assertTrue("deletionCompleted is not true", pollDeletedEvent.isDeletionCompleted());
    }
}