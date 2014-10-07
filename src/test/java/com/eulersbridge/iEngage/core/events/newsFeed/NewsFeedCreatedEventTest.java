package com.eulersbridge.iEngage.core.events.studentYear;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class StudentYearCreatedEventTest {
    final Long nodeId = new Long(1);
    final String year = new String("2014");
    final Long start = new Long(2012);
    final Long end = new Long(2015);
    final Long institutionId = new Long(1);
    StudentYearDetails studentYearDetails = null;
    final Long id = new Long(1);
    StudentYearCreatedEvent studentYearCreatedEvent = null;

    @Before
    public void setUp() throws Exception {
        studentYearDetails = new StudentYearDetails(year, start, end, institutionId);
        studentYearDetails.setNodeId(nodeId);
        studentYearCreatedEvent = new StudentYearCreatedEvent(id, studentYearDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testStudentYearCreatedEvent() throws Exception {
        assertNotNull("studentYearCreatedEvent is null", studentYearCreatedEvent);
        StudentYearCreatedEvent studentYearCreatedEvent1 = new StudentYearCreatedEvent(id);
        assertNotNull("StudentYearCreatedEvent is null", studentYearCreatedEvent1);
    }

    @Test
    public void testGetStudentYearDetails() throws Exception {
        assertEquals("studentYearDetails does not match", studentYearDetails, studentYearCreatedEvent.getStudentYearDetails());
    }

    @Test
    public void testSetStudentYearDetails() throws Exception {
        StudentYearCreatedEvent studentYearCreatedEvent1 = new StudentYearCreatedEvent(id);
        studentYearCreatedEvent1.setStudentYearDetails(studentYearDetails);
        assertEquals("studentYearDetails does not match", studentYearDetails, studentYearCreatedEvent1.getStudentYearDetails());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("id does not match", id ,studentYearCreatedEvent.getId());
    }

    @Test
    public void testSetId() throws Exception {
        Long id1 = new Long(2);
        studentYearCreatedEvent.setId(id1);
        assertEquals("id does not match", id1, studentYearCreatedEvent.getId());
    }

    @Test
    public void testIsInstitutionFound() throws Exception {
        assertTrue("institutionFound is not true", studentYearCreatedEvent.isInstitutionFound());
    }

    @Test
    public void testSetInstitutionFound() throws Exception {
        studentYearCreatedEvent.setInstitutionFound(false);
        assertFalse("institutionFound is not false", studentYearCreatedEvent.isInstitutionFound());

    }

    @Test
    public void testInstitutionNotFound() throws Exception {
        StudentYearCreatedEvent studentYearCreatedEvent1 = StudentYearCreatedEvent.institutionNotFound(id);
        assertNotNull("institutionNotFound() returns null", studentYearCreatedEvent1);
        assertFalse("institutionFound is not false", studentYearCreatedEvent1.isInstitutionFound());
    }
}