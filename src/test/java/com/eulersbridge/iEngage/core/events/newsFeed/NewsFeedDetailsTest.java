package com.eulersbridge.iEngage.core.events.newsFeed;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class NewsFeedDetailsTest {
    final Long nodeId = new Long(1);
    final Long institutionId = new Long(1);
    NewsFeedDetails newsFeedDetails = null;
    NewsFeedDetails newsFeedDetails1 = null;

    @Before
    public void setUp() throws Exception {
        newsFeedDetails = new NewsFeedDetails(institutionId);
        newsFeedDetails.setNodeId(nodeId);
        newsFeedDetails1 = new NewsFeedDetails();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testStudentYearDetails() throws Exception {
        assertNotNull("newsFeedDetails is null", newsFeedDetails);
        assertNotNull("newsFeedDetails is null", newsFeedDetails1);
    }

    @Test
    public void testGetNodeId() throws Exception {
        assertEquals("nodeId does not match", nodeId, newsFeedDetails.getNodeId());
    }

    @Test
    public void testSetNodeId() throws Exception {
        Long nodeId1 = new Long(3);
        newsFeedDetails1.setNodeId(nodeId1);
        assertEquals("nodeId does not match", nodeId1, newsFeedDetails1.getNodeId());
    }

    @Test
    public void testGetInstitutionId() throws Exception {
        assertEquals("institutionId does not match", nodeId, newsFeedDetails.getInstitutionId());
    }

    @Test
    public void testSetInstitutionId() throws Exception {
        Long nodeId1 = new Long(3);
        newsFeedDetails1.setInstitutionId(nodeId1);
        assertEquals("institutionId does not match", nodeId1, newsFeedDetails1.getInstitutionId());
    }
}