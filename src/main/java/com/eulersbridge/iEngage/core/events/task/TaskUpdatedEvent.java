package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class TaskUpdatedEvent extends UpdatedEvent {
    public TaskUpdatedEvent(Long taskId, TaskDetails taskDetails) {
        super(taskId, taskDetails);
    }

    public TaskUpdatedEvent(Long taskId) {
        super(taskId);
    }
}
