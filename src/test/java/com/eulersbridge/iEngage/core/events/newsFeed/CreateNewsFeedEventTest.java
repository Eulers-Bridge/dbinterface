package com.eulersbridge.iEngage.core.events.studentYear;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CreateStudentYearEventTest {
    final Long nodeId = new Long(1);
    final String year = new String("2014");
    final Long start = new Long(2012);
    final Long end = new Long(2015);
    final Long institutionId = new Long(1);
    StudentYearDetails studentYearDetails = null;
    CreateStudentYearEvent createStudentYearEvent = null;

    @Before
    public void setUp() throws Exception {
        studentYearDetails = new StudentYearDetails(year, start, end, institutionId);
        createStudentYearEvent = new CreateStudentYearEvent(nodeId, studentYearDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateStudentYearEvent() throws Exception {
        assertNotNull("createStudentYearEvent is null", createStudentYearEvent);
        CreateStudentYearEvent createStudentYearEvent1 = new CreateStudentYearEvent(studentYearDetails);
        assertNotNull("CreateStudentYearEvent is null", createStudentYearEvent1);
    }

    @Test
    public void testGetStudentYearDetails() throws Exception {
        assertEquals("StudentYearDetails does not match", studentYearDetails, createStudentYearEvent.getStudentYearDetails());
    }

    @Test
    public void testSetStudentYearDetails() throws Exception {
        String year1 = new String("2013");
        Long start1 = new Long(2010);
        Long end1 = new Long(2013);
        Long institutionId1 = new Long(1);
        StudentYearDetails studentYearDetails1 = new StudentYearDetails(year1,start1, end1, institutionId1);
        createStudentYearEvent.setStudentYearDetails(studentYearDetails1);
        assertEquals("StudentYearDetails does not match", studentYearDetails1, createStudentYearEvent.getStudentYearDetails());
    }
}