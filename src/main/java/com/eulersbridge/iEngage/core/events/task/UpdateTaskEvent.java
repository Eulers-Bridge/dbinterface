package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateTaskEvent extends UpdateEvent{
    public UpdateTaskEvent(Long taskId, TaskDetails taskDetails) {
        super(taskId, taskDetails);
    }
}
