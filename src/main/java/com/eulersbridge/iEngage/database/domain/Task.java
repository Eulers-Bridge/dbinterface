package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.task.TaskDetails;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Task extends Node{
  private static final Logger LOG = LoggerFactory.getLogger(Task.class);
  @Index
  private String action;
  private String description;
  private Integer xpValue;

  public Task() {}

  public Task(Long taskId, String action, String description, Integer xpValue) {
    super();
    this.nodeId = taskId;
    this.action = action;
    this.description = description;
    this.xpValue = xpValue;
  }

  public static Task fromTaskDetails(TaskDetails taskDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromTaskDetails()");
    Task task = new Task();
    if (LOG.isTraceEnabled()) LOG.trace("taskDetails " + taskDetails);
    task.setNodeId(taskDetails.getNodeId());
    task.setAction(taskDetails.getAction());
    task.setDescription(taskDetails.getDescription());
    task.setXpValue(taskDetails.getXpValue());

    if (LOG.isTraceEnabled()) LOG.trace("task " + task);
    return task;
  }

  public TaskDetails toTaskDetails() {
    TaskDetails taskDetails = new TaskDetails();
    taskDetails.setNodeId(getNodeId());
    taskDetails.setAction(getAction());
    taskDetails.setDescription(getDescription());
    taskDetails.setXpValue(getXpValue());
    return taskDetails;
  }

  @Override
  public String toString() {
    String buff = "[ id = " + getNodeId() +
      ", action = " +
      getAction() +
      ", xpValue = " +
      getXpValue() +
      " ]";
    String retValue;
    retValue = buff;
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getXpValue() {
    return xpValue;
  }

  public void setXpValue(Integer xpValue) {
    this.xpValue = xpValue;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (getNodeId() != null) {
      result = prime * result + getNodeId().hashCode();
    } else {
      result = prime * result + ((action == null) ? 0 : action.hashCode());
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((xpValue == null) ? 0 : xpValue.hashCode());
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Task other = (Task) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
        return false;
      if (action == null) {
        if (other.action != null)
          return false;
      } else if (!action.equals(other.action))
        return false;
      if (description == null) {
        if (other.description != null)
          return false;
      } else if (!description.equals(other.description))
        return false;
      if (xpValue == null) {
        if (other.xpValue != null)
          return false;
      } else if (!xpValue.equals(other.xpValue))
        return false;
    }
    return true;
  }


}
