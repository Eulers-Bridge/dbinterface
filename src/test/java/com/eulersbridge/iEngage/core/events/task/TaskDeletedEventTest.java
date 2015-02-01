package com.eulersbridge.iEngage.core.events.task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class TaskDeletedEventTest {
    Long taskId = new Long(0);
    String action = "action";
    Integer xpValue = new Integer(100);

    TaskDetails taskDetails;
    TaskDeletedEvent taskDeletedEvent;

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
        taskDeletedEvent = new TaskDeletedEvent(taskId);
        assertNotNull("constructor returns null", taskDeletedEvent);
    }
}