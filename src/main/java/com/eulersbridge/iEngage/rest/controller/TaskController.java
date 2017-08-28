package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.task.*;
import com.eulersbridge.iEngage.core.services.interfacePack.TaskService;
import com.eulersbridge.iEngage.rest.domain.FindsParent;
import com.eulersbridge.iEngage.rest.domain.Response;
import com.eulersbridge.iEngage.rest.domain.Task;
import com.eulersbridge.iEngage.rest.domain.TaskCompleted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

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
        CreatedEvent taskCreatedEvent = taskService.createTask(createTaskEvent);
        if((null==taskCreatedEvent)||(null == taskCreatedEvent.getNodeId()))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Task result = Task.fromTaskDetails((TaskDetails) taskCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("task"+result.toString());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
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

	/**
	 * Is passed all the necessary data to read tasks from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the tasks read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the task objects to be read.
	 * @return the tasks.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TASKS_LABEL)
	public @ResponseBody ResponseEntity<FindsParent> findTasks(
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve tasks.");
		ResponseEntity<FindsParent> response;

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent taskEvent = taskService.readTasks(
				new ReadAllEvent(null), sortDirection,
				pageNumber, pageLength);

		if (!taskEvent.isEntityFound())
		{
			response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Task> tasks = Task
					.toTasksIterator(taskEvent.getDetails().iterator());
			FindsParent theTasks = FindsParent.fromArticlesIterator(tasks, taskEvent.getTotalItems(), taskEvent.getTotalPages());
			response = new ResponseEntity<FindsParent>(theTasks, HttpStatus.OK);
		}
		return response;
	}

	/**
	 * Is passed all the necessary data to read tasks from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the tasks read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the task objects to be read.
	 * @return the tasks.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TASKS_LABEL+"/complete/{userId}")
	public @ResponseBody ResponseEntity<FindsParent> findCompletedTasks(
			@PathVariable Long userId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		ResponseEntity<FindsParent> response;
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve completed tasks for "+userId+".");

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent taskEvent = taskService.readCompletedTasks(
				new ReadCompletedTasksEvent(userId), sortDirection,
				pageNumber, pageLength);

		if (!taskEvent.isEntityFound())
		{
			response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Task> tasks = Task
					.toTasksIterator(taskEvent.getDetails().iterator());
			FindsParent theTasks = FindsParent.fromArticlesIterator(tasks, taskEvent.getTotalItems(), taskEvent.getTotalPages());
			response = new ResponseEntity<FindsParent>(theTasks, HttpStatus.OK);
		}
		return response;
	}

	/**
	 * Is passed all the necessary data to read tasks from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the tasks read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the task objects to be read.
	 * @return the tasks.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TASKS_LABEL+"/remaining/{userId}")
	public @ResponseBody ResponseEntity<FindsParent> findRemainingTasks(
			@PathVariable Long userId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		ResponseEntity<FindsParent> response;
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve remaining tasks for "+userId+".");

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent taskEvent = taskService.readRemainingTasks(
				new ReadCompletedTasksEvent(userId), sortDirection,
				pageNumber, pageLength);

		if (!taskEvent.isEntityFound())
		{
			response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Task> tasks = Task
					.toTasksIterator(taskEvent.getDetails().iterator());
			FindsParent theTasks = FindsParent.fromArticlesIterator(tasks, taskEvent.getTotalItems(), taskEvent.getTotalPages());
			response = new ResponseEntity<FindsParent>(theTasks, HttpStatus.OK);
		}

		return response;
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

    //Completed Task
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.TASK_LABEL+"/{taskId}/complete/{userId}")
    public @ResponseBody ResponseEntity<TaskCompleted>
    completedTask(@PathVariable Long taskId, @PathVariable Long userId)
    {
        if (LOG.isInfoEnabled()) LOG.info("User "+userId+" completed task. " + taskId);
        TaskCompleteDetails dets=new TaskCompleteDetails(null, userId, taskId, null);
        UpdatedEvent taskCompletedEvent = taskService.completedTask(new CompletedTaskEvent(dets));
        if(null != taskCompletedEvent)
        {
            if (LOG.isDebugEnabled()) LOG.debug("taskUpdatedEvent - "+taskCompletedEvent);
            if(taskCompletedEvent.isEntityFound()){
                TaskCompleted result = TaskCompleted.fromTaskCompletedDetails((TaskCompleteDetails) taskCompletedEvent.getDetails());
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
    public @ResponseBody ResponseEntity<Response>
    deleteTask(@PathVariable Long taskId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete task. " + taskId);
        ResponseEntity<Response> response;

        DeletedEvent taskDeletedEvent = taskService.deleteTask(new DeleteTaskEvent(taskId));
        Response restEvent;
        if (!taskDeletedEvent.isEntityFound()){
            restEvent = Response.failed("Not found");
            response = new ResponseEntity<Response>(restEvent, HttpStatus.NOT_FOUND);
        }
        else{
            if (taskDeletedEvent.isDeletionCompleted()){
                restEvent = new Response();
                response=new ResponseEntity<Response>(restEvent,HttpStatus.OK);
            }
            else {
                restEvent = Response.failed("Could not delete");
                response=new ResponseEntity<Response>(restEvent,HttpStatus.GONE);
            }
        }
        return response;
    }
}
