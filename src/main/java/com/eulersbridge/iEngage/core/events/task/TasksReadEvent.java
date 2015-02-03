/**
 * 
 */
package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.AllReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class TasksReadEvent extends AllReadEvent
{
	Iterable<TaskDetails> tasks;
	
	public TasksReadEvent(Iterable<TaskDetails> tasks, Long totalItems, Integer totalPages)
	{
		super(null,totalItems,totalPages);
		this.tasks=tasks;
	}

	public TasksReadEvent(Iterable<TaskDetails> tasks)
	{
		super(null);
		this.tasks=tasks;
	}

	public TasksReadEvent()
	{
		super(null);
	}
	
	/**
	 * @return the tasks
	 */
	public Iterable<TaskDetails> getTasks() 
	{
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(Iterable<TaskDetails> tasks) 
	{
		this.tasks = tasks;
	}
	
	public static TasksReadEvent notFound(Long id)
	{
		TasksReadEvent ev = new TasksReadEvent();
		ev.entityFound = false;
		return ev;
	}
}
