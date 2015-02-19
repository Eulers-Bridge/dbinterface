package com.eulersbridge.iEngage.rest.domain;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.task.TaskDetails;
import com.eulersbridge.iEngage.rest.controller.TaskController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * @author Yikai Gong
 */

public class Task extends ResourceSupport
{
    private Long taskId;
    private String action;
    private String description;
    private Integer xpValue;

    private static Logger LOG = LoggerFactory.getLogger(Task.class);

    public static Task fromTaskDetails(TaskDetails taskDetails){
        Task task = new Task();
        String simpleName = Task.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        task.setTaskId(taskDetails.getNodeId());
        task.setAction(taskDetails.getAction());
        task.setDescription(taskDetails.getDescription());
        task.setXpValue(taskDetails.getXpValue());

        // {!begin selfRel}
        task.add(linkTo(TaskController.class).slash(name)
                .slash(task.taskId).withSelfRel());
        // {!end selfRel}

        return task;
    }

    public TaskDetails toTaskDetails(){
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setNodeId(getTaskId());
        taskDetails.setAction(getAction());
        taskDetails.setDescription(getDescription());
        taskDetails.setXpValue(getXpValue());
        return taskDetails;
    }

    public Task() {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
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

    /**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getXpValue() {
        return xpValue;
    }

    public void setXpValue(Integer xpValue) {
        this.xpValue = xpValue;
    }
    
	public static Iterator<Task> toTasksIterator(
			Iterator<TaskDetails> iter)
	{
		if (null==iter) return null;
		ArrayList <Task> elections=new ArrayList<Task>();
		while(iter.hasNext())
		{
			TaskDetails dets=iter.next();
			Task thisTask=Task.fromTaskDetails(dets);
			Link self = thisTask.getLink("self");
			thisTask.removeLinks();
			thisTask.add(self);
			elections.add(thisTask);		
		}
		return elections.iterator();
	}

}
