package com.eulersbridge.iEngage.core.events.studentYear;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class StudentYearDetailsTest {
    final Long nodeId = new Long(1);
    final String year = new String("2014");
    final Long start = new Long(2012);
    final Long end = new Long(2015);
    final Long institutionId = new Long(1);
    StudentYearDetails studentYearDetails = null;
    StudentYearDetails studentYearDetails1 = null;

    @Before
    public void setUp() throws Exception {
        studentYearDetails = new StudentYearDetails(year, start, end, institutionId);
        studentYearDetails.setNodeId(nodeId);
        studentYearDetails1 = new StudentYearDetails();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testStudentYearDetails() throws Exception {
        assertNotNull("studentYearDetails is null", studentYearDetails);
        assertNotNull("studentYearDetails is null", studentYearDetails1);
    }

    @Test
    public void testGetNodeId() throws Exception {
        assertEquals("nodeId does not match", nodeId, studentYearDetails.getNodeId());
    }

    @Test
    public void testSetNodeId() throws Exception {
        Long nodeId1 = new Long(3);
        studentYearDetails1.setNodeId(nodeId1);
        assertEquals("nodeId does not match", nodeId1, studentYearDetails1.getNodeId());
    }

    @Test
    public void testGetYear() throws Exception {
        assertEquals("year does not match", year, studentYearDetails.getYear());
    }

    @Test
    public void testSetYear() throws Exception {
        String year1 = new String("2013");
        studentYearDetails1.setYear(year1);
        assertEquals("year does not match", year1, studentYearDetails1.getYear());
    }
}