package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import com.eulersbridge.iEngage.core.events.task.TaskCompleteDetails;


@RelationshipEntity(type=DatabaseDomainConstants.TASK_COMPLETE_LABEL)
public class TaskComplete 
{
	@GraphId private Long nodeId;
	@StartNode private User user;
	@EndNode private Task task;
	private Long date;
	
    private static Logger LOG = LoggerFactory.getLogger(TaskComplete.class);

	public TaskComplete()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor()");
		date=Calendar.getInstance().getTimeInMillis();
	}

	public TaskCompleteDetails toTaskCompleteDetails()
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toTaskCompleteDetails()");
	    
	    Long userId=((getUser() == null) ? null : getUser().getNodeId());
	    Long taskId=((getTask() == null) ? null : getTask().getNodeId());
	    TaskCompleteDetails details = new TaskCompleteDetails(getNodeId(),userId,taskId,getDate());
	    details.setNodeId(getNodeId());
	    if (LOG.isTraceEnabled()) LOG.trace("taskComplete "+this);

	    BeanUtils.copyProperties(this, details);
	    
	    details.setTaskId(taskId);
	    details.setUserId(userId);
	    if (LOG.isTraceEnabled()) LOG.trace("instDetails "+details);

	    return details;
	}
	
	public static TaskComplete fromTaskCompleteDetails(TaskCompleteDetails details)
	{
	    if (LOG.isTraceEnabled()) LOG.trace("fromTaskCompleteDetails()");
	    
	    TaskComplete taskComplete = new TaskComplete();
	    taskComplete.setNodeId(details.getNodeId());
	    taskComplete.setDate(details.getDate());
	    Task task=new Task();
	    task.setNodeId(details.getTaskId());
	    taskComplete.setTask(task);
	    User user=new User();
	    user.setNodeId(details.getUserId());
	    taskComplete.setUser(user);

	    if (LOG.isTraceEnabled()) LOG.trace("taskComplete "+taskComplete+" taskCompleteDetails "+details);
	    return taskComplete;
	}
	
	public Long getNodeId()
	{
		return nodeId;
	}
	
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public Long getDate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getDate() = "+date);
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
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
	public Task getTask()
	{
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskComplete [nodeId=" + nodeId + ", user=" + user
				+ ", task=" + task + ", date=" + date
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (getNodeId()==null)
		{
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((task == null) ? 0 : task.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		}
		else
		{
			result=getNodeId().hashCode();
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
		if (nodeId != null)
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId != null)
				return false;
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
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
