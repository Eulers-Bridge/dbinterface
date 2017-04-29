package com.eulersbridge.iEngage.core.events.task;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class DeleteTaskEventTest {
    Long taskId = new Long(0);
    DeleteTaskEvent deleteTaskEvent;

    @Test
    public void testConstructor() throws Exception{
        deleteTaskEvent = new DeleteTaskEvent(taskId);
        assertNotNull("constructor returns null", taskId);
    }
}