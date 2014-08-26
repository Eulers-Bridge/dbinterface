package com.eulersbridge.iEngage.core.events.studentYear;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadStudentYearEventTest {
    final Long nodeId = new Long(1);
    ReadStudentYearEvent readStudentYearEvent = null;
    @Before
    public void setUp() throws Exception {
        readStudentYearEvent = new ReadStudentYearEvent(nodeId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadStudentYearEvent() throws Exception {
        assertNotNull("readStudentYearEvent is null", readStudentYearEvent);
    }

    @Test
    public void testGetStudentYearId() throws Exception {
        assertEquals("StudentYearId does not match", nodeId, readStudentYearEvent.getStudentYearId());
    }
}