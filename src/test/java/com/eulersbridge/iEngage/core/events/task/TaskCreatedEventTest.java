package com.eulersbridge.iEngage.core.events.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class TaskCreatedEventTest {
    Long taskId = new Long(0);
    String action = "action";
    Integer xpValue = new Integer(100);

    TaskDetails taskDetails;
    TaskCreatedEvent taskCreatedEvent;

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
    public void testConstructor() throws Exception {
        taskCreatedEvent = new TaskCreatedEvent(taskDetails);
        assertNotNull("constructor returns null", taskCreatedEvent);
    }
}