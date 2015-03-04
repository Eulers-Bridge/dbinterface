/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.task.TaskCompleteDetails;
import com.eulersbridge.iEngage.rest.controller.TaskController;

/**
 * @author Greg Newitt
 *
 */
public class TaskCompleted extends ResourceSupport
{
    private Long nodeId;
    private Long taskId;
    private Long timestamp;
    private Long userId;

    private static Logger LOG = LoggerFactory.getLogger(TaskCompleted.class);

    public static TaskCompleted fromTaskCompletedDetails(TaskCompleteDetails taskCompleteDetails)
    {
    	TaskCompleted task = new TaskCompleted();
        String simpleName = Task.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        task.setNodeId(taskCompleteDetails.getNodeId());
        task.setTaskId(taskCompleteDetails.getTaskId());
        task.setTimestamp(taskCompleteDetails.getDate());
        task.setUserId(taskCompleteDetails.getUserId());

        // {!begin selfRel}
        task.add(linkTo(TaskController.class).slash(name)
                .slash(task.taskId).withSelfRel());
        // {!end selfRel}

        return task;
    }

    public TaskCompleteDetails toTaskDetails()
    {
    	TaskCompleteDetails taskDetails = new TaskCompleteDetails();
        taskDetails.setNodeId(getNodeId());
        taskDetails.setTaskId(getTaskId());
        taskDetails.setDate(getTimestamp());
        taskDetails.setUserId(getUserId());
        return taskDetails;
    }

    public TaskCompleted() 
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    /**
	 * @return the nodeId
	 */
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

	public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp()
	{
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp)
	{
		this.timestamp = timestamp;
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

	public static Iterator<TaskCompleted> toTasksIterator(
			Iterator<TaskCompleteDetails> iter)
	{
		if (null==iter) return null;
		ArrayList <TaskCompleted> completedTasks=new ArrayList<TaskCompleted>();
		while(iter.hasNext())
		{
			TaskCompleteDetails dets=iter.next();
			TaskCompleted thisTask=TaskCompleted.fromTaskCompletedDetails(dets);
			Link self = thisTask.getLink("self");
			thisTask.removeLinks();
			thisTask.add(self);
			completedTasks.add(thisTask);		
		}
		return completedTasks.iterator();
	}

}
