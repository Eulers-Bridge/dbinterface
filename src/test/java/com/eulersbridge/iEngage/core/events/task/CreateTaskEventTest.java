package com.eulersbridge.iEngage.core.events.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class CreateTaskEventTest {
    Long taskId = new Long(0);
    String action = "action";
    Integer xpValue = new Integer(100);
    TaskDetails taskDetails;

    CreateTaskEvent createTaskEvent;

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
    public void constructor() throws Exception{
        createTaskEvent = new CreateTaskEvent(taskDetails);
        assertNotNull("constructor returns null", createTaskEvent);
    }
}