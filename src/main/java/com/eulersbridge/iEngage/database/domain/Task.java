package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.task.TaskDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Task {
    @GraphId
    private Long taskId;
    private String action;
    private boolean completed;
    private Long timestamp;
    private Long xpValue;

    private static Logger LOG = LoggerFactory.getLogger(Task.class);

    public Task() {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public static Task fromTaskDetails(TaskDetails taskDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromTaskDetails()");
        Task task = new Task();
        if (LOG.isTraceEnabled()) LOG.trace("taskDetails "+taskDetails);
        task.setTaskId(taskDetails.getNodeId());
        task.setAction(taskDetails.getAction());
        task.setCompleted(taskDetails.isCompleted());
        task.setTimestamp(taskDetails.getTimestamp());
        task.setXpValue(taskDetails.getXpValue());

        if (LOG.isTraceEnabled()) LOG.trace("task "+task);
        return task;
    }

    public TaskDetails toTaskDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toTaskDetails()");
        TaskDetails taskDetails = new TaskDetails();
        if (LOG.isTraceEnabled()) LOG.trace("task "+this);
        taskDetails.setNodeId(getTaskId());
        taskDetails.setAction(getAction());
        taskDetails.setCompleted(isCompleted());
        taskDetails.setTimestamp(getTimestamp());
        taskDetails.setXpValue(getXpValue());
        if (LOG.isTraceEnabled()) LOG.trace("taskDetails; "+ taskDetails);
        return taskDetails;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getTaskId());
        buff.append(", action = ");
        buff.append(getAction());
        buff.append(", completed = ");
        buff.append(isCompleted());
        buff.append(", timestamp = ");
        buff.append(getTimestamp());
        buff.append(", xpValue = ");
        buff.append(getXpValue());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getXpValue() {
        return xpValue;
    }

    public void setXpValue(Long xpValue) {
        this.xpValue = xpValue;
    }
}
