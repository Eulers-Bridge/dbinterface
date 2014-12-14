package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.*;
import com.eulersbridge.iEngage.core.services.TaskService;
import com.eulersbridge.iEngage.rest.domain.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class TaskController {
    @Autowired
    TaskService taskService;

    private static Logger LOG = LoggerFactory.getLogger(TaskController.class);

    public TaskController() {
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.TASK_LABEL)
    public @ResponseBody ResponseEntity<Task>
    createTask(@RequestBody Task task){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create task "+task);
        CreateTaskEvent createTaskEvent = new CreateTaskEvent(task.toTaskDetails());
        TaskCreatedEvent taskCreatedEvent = taskService.createTask(createTaskEvent);
        if(taskCreatedEvent.getTaskId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Task result = Task.fromTaskDetails((TaskDetails) taskCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("task"+result.toString());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TASK_LABEL + "/{taskId}")
    public @ResponseBody ResponseEntity<Task>
    findTask(@PathVariable Long taskId){
        if (LOG.isInfoEnabled()) LOG.info(taskId+" attempting to get task. ");
        RequestReadTaskEvent requestReadTaskEvent = new RequestReadTaskEvent(taskId);
        ReadEvent readTaskEvent = taskService.requestReadTask(requestReadTaskEvent);
        if(readTaskEvent.isEntityFound()){
            Task task = Task.fromTaskDetails((TaskDetails) readTaskEvent.getDetails());
            return new ResponseEntity<>(task, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.TASK_LABEL+"/{taskId}")
    public @ResponseBody ResponseEntity<Task>
    updateTask(@PathVariable Long taskId, @RequestBody Task task){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update task. " + taskId);
        UpdatedEvent taskUpdatedEvent = taskService.updateTask(new UpdateTaskEvent(taskId, task.toTaskDetails()));
        if(null != taskUpdatedEvent){
            if (LOG.isDebugEnabled()) LOG.debug("taskUpdatedEvent - "+taskUpdatedEvent);
            if(taskUpdatedEvent.isEntityFound()){
                Task result = Task.fromTaskDetails((TaskDetails) taskUpdatedEvent.getDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.TASK_LABEL+"/{taskId}")
    public @ResponseBody ResponseEntity<Boolean>
    deleteTask(@PathVariable Long taskId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete task. " + taskId);
        DeletedEvent taskDeletedEvent = taskService.deleteTask(new DeleteTaskEvent(taskId));
        Boolean isDeletionCompleted = Boolean.valueOf(taskDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }
}
