package com.eulersbridge.iEngage.core.events.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class UpdateTaskEventTest {
    Long taskId = new Long(0);
    String action = "action";
    Integer xpValue = new Integer(100);

    TaskDetails taskDetails;
    UpdateTaskEvent updateTaskEvent;

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
    public void testConstructor() throws Exception{
        updateTaskEvent = new UpdateTaskEvent(taskId, taskDetails);
        assertNotNull("constructor returns null", updateTaskEvent);
    }
}