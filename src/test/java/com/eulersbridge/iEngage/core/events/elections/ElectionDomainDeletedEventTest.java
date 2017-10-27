package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ElectionDomainDeletedEventTest {
    final Long electionId = (long) 10;
    ElectionDeletedEvent electionDeletedEvent;

    @Before
    public void setUp() throws Exception {
        electionDeletedEvent = new ElectionDeletedEvent(electionId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testElectionDeletedEvent() throws Exception {
        assertNotNull("electionDeletedEvent is null", electionDeletedEvent);
    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("electionId does not match", electionId, electionDeletedEvent.getNodeId());
    }

    @Test
    public void testDeletionForbidden() throws Exception {
        DeletedEvent electionDeletedEvent1 = ElectionDeletedEvent.deletionForbidden(electionId);
        assertNotNull("electionDeletedEvent is null", electionDeletedEvent1);
        assertFalse("deletionCompleted is not false", electionDeletedEvent1.isDeletionCompleted());
    }

    @Test
    public void testNotFound() throws Exception {
        DeletedEvent electionDeletedEvent1 = ElectionDeletedEvent.notFound(electionId);
        assertNotNull("electionDeletedEvent is null", electionDeletedEvent1);
        assertFalse("entityFound is not false", electionDeletedEvent1.isEntityFound());


    }
}