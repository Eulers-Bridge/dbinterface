package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.task.TaskCompleteDetails;
import org.neo4j.ogm.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.Date;


@RelationshipEntity(type = DataConstants.HAS_COMPLETED_TASK_LABEL)
public class TaskComplete {
  @Id @GeneratedValue
  private Long nodeId;
  @StartNode
  private User user;
  @EndNode
  private Task task;

  private long numOfTimes;
  private String dateList;
  private String tag;

  private static Logger LOG = LoggerFactory.getLogger(TaskComplete.class);



  public TaskComplete() {

  }

  public static TaskComplete init(){
    TaskComplete taskComplete = new TaskComplete();
    if (LOG.isTraceEnabled()) LOG.trace("Constructor()");
    Long date = Calendar.getInstance().getTimeInMillis();
    String dateList = String.valueOf(date);
    long numOfTimes = 1L;
    taskComplete.setDateList(dateList);
    taskComplete.setNumOfTimes(numOfTimes);
    return taskComplete;
  }

  public TaskComplete update() {
    numOfTimes++;
    Long date = new Date().getTime();
    dateList = dateList + "," + date;
    return this;
  }

  public TaskCompleteDetails toTaskCompleteDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toTaskCompleteDetails()");

    Long userId = ((getUser() == null) ? null : getUser().getNodeId());
    Long taskId = ((getTask() == null) ? null : getTask().getNodeId());
    TaskCompleteDetails details = new TaskCompleteDetails(getNodeId(), userId, taskId, null);
    details.setNodeId(getNodeId());
    if (LOG.isTraceEnabled()) LOG.trace("taskComplete " + this);

    BeanUtils.copyProperties(this, details);

    details.setTaskId(taskId);
    details.setUserId(userId);
    if (LOG.isTraceEnabled()) LOG.trace("instDetails " + details);

    return details;
  }

  public static TaskComplete fromTaskCompleteDetails(TaskCompleteDetails details) {
    if (LOG.isTraceEnabled()) LOG.trace("fromTaskCompleteDetails()");

    TaskComplete taskComplete = new TaskComplete();
    taskComplete.setNodeId(details.getNodeId());
//    taskComplete.setDate(details.getDate());
    Task task = new Task();
    task.setNodeId(details.getTaskId());
    taskComplete.setTask(task);
    User user = new User();
    user.setNodeId(details.getUserId());
    taskComplete.setUser(user);

    if (LOG.isTraceEnabled())
      LOG.trace("taskComplete " + taskComplete + " taskCompleteDetails " + details);
    return taskComplete;
  }

  public Long getNodeId() {
    return nodeId;
  }


  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  /**
   * @param nodeId the nodeId to set
   */
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public String getDateList() {
    return dateList;
  }

  public void setDateList(String dateList) {
    this.dateList = dateList;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * @return the task
   */
  public Task getTask() {
    return task;
  }

  /**
   * @param task the task to set
   */
  public void setTask(Task task) {
    this.task = task;
  }

  public long getNumOfTimes() {
    return numOfTimes;
  }

  public void setNumOfTimes(long numOfTimes) {
    this.numOfTimes = numOfTimes;
  }

  /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
  @Override
  public String toString() {
    return "TaskComplete [nodeId=" + nodeId + ", user=" + user
      + ", task=" + task
      + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (getNodeId() == null) {
      result = prime * result
        + ((task == null) ? 0 : task.hashCode());
      result = prime * result + ((user == null) ? 0 : user.hashCode());
    } else {
      result = getNodeId().hashCode();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TaskComplete other = (TaskComplete) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
        return false;
      if (task == null) {
        if (other.task != null)
          return false;
      } else if (!task.equals(other.task))
        return false;
      if (user == null) {
        if (other.user != null)
          return false;
      } else if (!user.equals(other.user))
        return false;
    }
    return true;
  }
}
