package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.task.TaskDetails;
import com.eulersbridge.iEngage.rest.controller.TaskController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * @author Yikai Gong
 */

public class Task extends ResourceSupport{
    private Long taskId;
    private String action;
    private boolean completed;
    private Long timestamp;
    private Long xpValue;

    private static Logger LOG = LoggerFactory.getLogger(Task.class);

    public static Task fromTaskDetails(TaskDetails taskDetails){
        Task task = new Task();
        String simpleName = Task.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        task.setTaskId(taskDetails.getNodeId());
        task.setAction(taskDetails.getAction());
        task.setCompleted(taskDetails.isCompleted());
        task.setTimestamp(taskDetails.getTimestamp());
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
        taskDetails.setCompleted(isCompleted());
        taskDetails.setTimestamp(getTimestamp());
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
