package com.eulersbridge.iEngage.core.events.voteRecord;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 */

public class VoteRecordReadEventTest {
    Long voteRecordId = new Long(1);
    VoteRecordDetails voteRecordDetails;
    VoteRecordReadEvent voteRecordReadEvent = null;

    @Before
    public void setUp() throws Exception {
        voteRecordDetails = DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
        voteRecordId=voteRecordDetails.getNodeId();
        voteRecordReadEvent = new VoteRecordReadEvent(voteRecordId, voteRecordDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testVoteRecordReadEvent() throws Exception {
        assertNotNull("voteRecordReadEvent is null", voteRecordReadEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("voteRecordId does not match", voteRecordId, voteRecordReadEvent.getVoteRecordId());
    }

    @Test
    public void testGetReadNewsArticleDetails() throws Exception {
        assertEquals("voteRecordDetails does not match", voteRecordDetails, voteRecordReadEvent.getVoteRecordDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        VoteRecordReadEvent voteRecordReadEvent1 = VoteRecordReadEvent.notFound(voteRecordId);
        assertNotNull("notFound() returns null", voteRecordReadEvent1);
        assertFalse("entityFound is not false", voteRecordReadEvent1.isEntityFound());
    }
}