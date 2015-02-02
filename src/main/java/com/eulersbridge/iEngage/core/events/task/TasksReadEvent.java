/**
 * 
 */
package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class TasksReadEvent extends ReadEvent
{
	Iterable<TaskDetails> tasks;
	Long electionId=null;
	private boolean electionFound=true;
	
	public TasksReadEvent(Long electionId, Iterable<TaskDetails> elections)
	{
		super(1l);
		this.tasks=elections;
		this.electionId=electionId;
	}

	public TasksReadEvent(Iterable<TaskDetails> elections)
	{
		super(1l);
		this.tasks=elections;
	}

	public TasksReadEvent()
	{
		super(1l);
	}
	
	/**
	 * @return the elections
	 */
	public Iterable<TaskDetails> getTasks() 
	{
		return tasks;
	}

	/**
	 * @param elections the elections to set
	 */
	public void setTasks(Iterable<TaskDetails> tasks) 
	{
		this.tasks = tasks;
	}

	/**
	 * @return the electionId
	 */
	public Long getElectionId() {
		return electionId;
	}

	/**
	 * @param electionId the electionId to set
	 */
	public void setElectionId(Long electionId) {
		this.electionId = electionId;
	}

	/**
	 * @return the electionFound
	 */
	public boolean isElectionFound() {
		return electionFound;
	}

	public static TasksReadEvent electionNotFound() 
	{
		TasksReadEvent nare=new TasksReadEvent();
		nare.electionFound=false;
		nare.entityFound=false;
		return nare;
	}

}
