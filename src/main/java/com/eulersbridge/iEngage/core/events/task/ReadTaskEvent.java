package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadTaskEvent extends ReadEvent {
    public ReadTaskEvent(Long taskId) {
        super(taskId);
    }

    public ReadTaskEvent(Long taskId, TaskDetails taskDetails) {
        super(taskId, taskDetails);
    }
}
