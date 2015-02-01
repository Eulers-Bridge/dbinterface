package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class TaskCreatedEvent extends CreatedEvent
{
    private Long failedId;

    private static Logger LOG = LoggerFactory.getLogger(TaskCreatedEvent.class);

    public TaskCreatedEvent(Long failedId){
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.failedId = failedId;
    }

    public TaskCreatedEvent(TaskDetails taskDetails)
    {
        super(taskDetails);
    }

	public Long getFailedId() {
        return failedId;
    }

    public void setFailedId(Long failedId) {
        this.failedId = failedId;
    }
}
