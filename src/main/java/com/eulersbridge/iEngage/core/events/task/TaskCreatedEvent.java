package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class TaskCreatedEvent extends CreatedEvent {
    private Long taskId;

    private static Logger LOG = LoggerFactory.getLogger(TaskCreatedEvent.class);

    public TaskCreatedEvent(Long taskId){
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.taskId = taskId;
    }

    public TaskCreatedEvent(Long taskId, TaskDetails taskDetails){
        super(taskDetails);
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
