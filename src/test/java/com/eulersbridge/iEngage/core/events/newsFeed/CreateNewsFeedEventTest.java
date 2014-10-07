package com.eulersbridge.iEngage.core.events.newsFeed;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.newsFeed.CreateNewsFeedEvent;
import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class CreateNewsFeedEventTest {
    final Long nodeId = new Long(1);
    final Long institutionId = new Long(1);
    NewsFeedDetails newsFeedDetails = null;
    CreateNewsFeedEvent createNewsFeedEvent = null;

    @Before
    public void setUp() throws Exception {
        newsFeedDetails = new NewsFeedDetails(institutionId);
        createNewsFeedEvent = new CreateNewsFeedEvent(nodeId, newsFeedDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateNewsFeedEvent() throws Exception {
        assertNotNull("createNewsFeedEvent is null", createNewsFeedEvent);
        CreateNewsFeedEvent createStudentYearEvent1 = new CreateNewsFeedEvent(newsFeedDetails);
        assertNotNull("createNewsFeedEvent is null", createStudentYearEvent1);
    }

    @Test
    public void testGetStudentYearDetails() throws Exception {
        assertEquals("NewsFeedDetails does not match", newsFeedDetails, createNewsFeedEvent.getNewsFeedDetails());
    }

    @Test
    public void testSetNewsFeedDetails() throws Exception {
        Long institutionId1 = new Long(1);
        NewsFeedDetails studentYearDetails1 = new NewsFeedDetails(institutionId1);
        createNewsFeedEvent.setNewsFeedDetails(studentYearDetails1);
        assertEquals("StudentYearDetails does not match", studentYearDetails1, createNewsFeedEvent.getNewsFeedDetails());
    }
}