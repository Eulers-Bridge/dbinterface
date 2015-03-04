/**
 * 
 */
package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class TaskCompleteDetails extends Details
{
	private Long userId;
	private Long taskId;
	private Long date;
	/**
	 * 
	 */
	public TaskCompleteDetails()
	{
		super();
	}
	/**
	 * @param userId
	 * @param taskId
	 * @param date
	 */
	public TaskCompleteDetails(Long nodeId, Long userId, Long taskId, Long date)
	{
		super(nodeId);
		this.userId = userId;
		this.taskId = taskId;
		this.date = date;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	/**
	 * @return the taskId
	 */
	public Long getTaskId()
	{
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Long taskId)
	{
		this.taskId = taskId;
	}
	/**
	 * @return the date
	 */
	public Long getDate()
	{
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Long date)
	{
		this.date = date;
	}
}
