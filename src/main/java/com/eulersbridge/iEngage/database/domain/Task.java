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
public class Task
{
    @GraphId
    private Long nodeId;
    private String action;
//    private boolean completed;
//    private Long timestamp;
    private Integer xpValue;

    private static Logger LOG = LoggerFactory.getLogger(Task.class);

    public Task() {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    /**
	 * @param nodeId
	 * @param action
	 * @param xpValue
	 */
	public Task(Long taskId, String action, Integer xpValue)
	{
		super();
		this.nodeId = taskId;
		this.action = action;
		this.xpValue = xpValue;
	}

	public static Task fromTaskDetails(TaskDetails taskDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromTaskDetails()");
        Task task = new Task();
        if (LOG.isTraceEnabled()) LOG.trace("taskDetails "+taskDetails);
        task.setNodeId(taskDetails.getNodeId());
        task.setAction(taskDetails.getAction());
        task.setXpValue(taskDetails.getXpValue());

        if (LOG.isTraceEnabled()) LOG.trace("task "+task);
        return task;
    }

    public TaskDetails toTaskDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toTaskDetails()");
        TaskDetails taskDetails = new TaskDetails();
        if (LOG.isTraceEnabled()) LOG.trace("task "+this);
        taskDetails.setNodeId(getNodeId());
        taskDetails.setAction(getAction());
        taskDetails.setXpValue(getXpValue());
        if (LOG.isTraceEnabled()) LOG.trace("taskDetails; "+ taskDetails);
        return taskDetails;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", action = ");
        buff.append(getAction());
        buff.append(", xpValue = ");
        buff.append(getXpValue());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long taskId) {
        this.nodeId = taskId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getXpValue() {
        return xpValue;
    }

    public void setXpValue(Integer xpValue) {
        this.xpValue = xpValue;
    }
}
