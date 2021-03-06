package com.eulersbridge.iEngage.core.events.institutions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class DeleteInstitutionDomainEventTest {
    final Long institutionId = new Long(1);
    DeleteInstitutionEvent deleteInstitutionEvent = null;

    @Before
    public void setUp() throws Exception {
        deleteInstitutionEvent = new DeleteInstitutionEvent(institutionId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDeleteInstitutionEvent() throws Exception {
        DeleteInstitutionEvent deleteInstitutionEvent1 = new DeleteInstitutionEvent(new Long(0));
        assertNotNull("DeleteInstitutionEvent is null", deleteInstitutionEvent1);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("institutionId does not match", institutionId, deleteInstitutionEvent.getNodeId());
    }
}