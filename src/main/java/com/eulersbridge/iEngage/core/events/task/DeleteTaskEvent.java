package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteTaskEvent extends DeleteEvent {
    public DeleteTaskEvent(Long taskId) {
        super(taskId);
    }
}
