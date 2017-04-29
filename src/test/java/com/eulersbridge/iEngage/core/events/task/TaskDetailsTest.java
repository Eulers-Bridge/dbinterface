package com.eulersbridge.iEngage.core.events.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class TaskDetailsTest {
    Long taskId = new Long(0);
    String action = "action";
    Integer xpValue = new Integer(100);

    TaskDetails taskDetails;

    @Before
    public void setUp() throws Exception {
        taskDetails = new TaskDetails();
        taskDetails.setNodeId(taskId);
        taskDetails.setAction(action);
        taskDetails.setXpValue(xpValue);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("toString() returns null", taskDetails.toString());
    }

    @Test
    public void testEquals() throws Exception {
        TaskDetails taskDetails1 = new TaskDetails();
        taskDetails1.setAction(action);
        taskDetails1.setXpValue(xpValue);
        taskDetails1.setNodeId(taskId);
        assertEquals("badgeDetails does not match", taskDetails, taskDetails1);

    }

    @Test
    public void testGetAction() throws Exception {
        assertEquals("action does not match", action, taskDetails.getAction());
    }

    @Test
    public void testSetAction() throws Exception {
        TaskDetails taskDetails1 = new TaskDetails();
        taskDetails1.setAction("action");
        assertEquals("action does not match", action, taskDetails1.getAction());
    }

    @Test
    public void testGetXpValue() throws Exception {
        assertEquals("xp does not match", xpValue, taskDetails.getXpValue());
    }

    @Test
    public void testSetXpValue() throws Exception {
        TaskDetails taskDetails1 = new TaskDetails();
        taskDetails1.setXpValue(xpValue);
        assertEquals("xp does not match", xpValue, taskDetails1.getXpValue());
    }
}