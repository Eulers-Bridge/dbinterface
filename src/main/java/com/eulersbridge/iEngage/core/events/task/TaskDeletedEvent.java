package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class TaskDeletedEvent extends DeletedEvent {
    public TaskDeletedEvent(Long taskId) {
        super(taskId);
    }
}
