package com.eulersbridge.iEngage.core.events.studentYear;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class StudentYearReadEventTest {
    final Long nodeId = new Long(1);
    final String year = new String("2014");
    final Long start = new Long(2012);
    final Long end = new Long(2015);
    final Long institutionId = new Long(1);
    StudentYearDetails studentYearDetails = null;
    StudentYearReadEvent studentYearReadEvent = null;
    final Long id = new Long(1);

    @Before
    public void setUp() throws Exception {
        studentYearDetails = new StudentYearDetails(year, start, end, institutionId);
        studentYearDetails.setNodeId(nodeId);
        studentYearReadEvent = new StudentYearReadEvent(id,studentYearDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testStudentYearReadEvent() throws Exception {
        assertNotNull("studentYearReadEvent is null", studentYearReadEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("id does not match", id, studentYearReadEvent.getNewsArticleId());
    }

    @Test
    public void testGetReadStudentYearDetails() throws Exception {
        assertEquals("studentYearDetails does not match", studentYearDetails, studentYearReadEvent.getReadStudentYearDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        StudentYearReadEvent studentYearReadEvent1 = StudentYearReadEvent.notFound(id);
        assertNotNull("testNotFound() returns null", studentYearReadEvent1);
        assertFalse("entityFound is not false", studentYearReadEvent1.isEntityFound());
    }
}