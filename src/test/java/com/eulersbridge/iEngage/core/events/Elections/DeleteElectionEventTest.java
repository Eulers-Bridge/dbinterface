package com.eulersbridge.iEngage.core.events.Elections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class DeleteElectionEventTest {
    final Long electionId = new Long(0);
    DeleteElectionEvent deleteElectionEvent;

    @Before
    public void setUp() throws Exception {
        deleteElectionEvent = new DeleteElectionEvent(electionId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @After
    public void testDeleteElectionEvent() throws Exception {
        assertNotNull("deleteElectionEvent is null", deleteElectionEvent);
    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("id does not match", electionId, deleteElectionEvent.getElectionId());
    }
}