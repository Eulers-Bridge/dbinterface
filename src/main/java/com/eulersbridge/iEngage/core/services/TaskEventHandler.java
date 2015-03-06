package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.*;
import com.eulersbridge.iEngage.database.domain.Task;
import com.eulersbridge.iEngage.database.domain.TaskComplete;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.TaskRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author Yikai Gong
 */

public class TaskEventHandler implements TaskService {
    private static Logger LOG = LoggerFactory.getLogger(TaskEventHandler.class);

    private TaskRepository taskRepository;

	private UserRepository userRepository;

    public TaskEventHandler(TaskRepository taskRepository, UserRepository userRepository)
    {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CreatedEvent createTask(CreateTaskEvent createTaskEvent)
    {
        TaskDetails taskDetails = (TaskDetails) createTaskEvent.getDetails();
        CreatedEvent taskCreatedEvent;
        Task task = Task.fromTaskDetails(taskDetails);
        Task result = taskRepository.save(task);
        if ((null==result)||(null==result.getNodeId()))
        	taskCreatedEvent = CreatedEvent.failed(taskDetails);
        else
        	taskCreatedEvent = new TaskCreatedEvent(result.toTaskDetails());
        return taskCreatedEvent;
    }

    @Override
    public ReadEvent requestReadTask(RequestReadTaskEvent requestReadTaskEvent) {
        Task task = taskRepository.findOne(requestReadTaskEvent.getNodeId());
        ReadEvent readTaskEvent;
        if(task != null){
            readTaskEvent = new ReadTaskEvent(task.getNodeId(), task.toTaskDetails());
        }
        else{
            readTaskEvent = ReadTaskEvent.notFound(requestReadTaskEvent.getNodeId());
        }
        return readTaskEvent;
    }

	@Override
	public TasksReadEvent readTasks(ReadAllEvent readTasksEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Page <Task>tasks=null;
		ArrayList<TaskDetails> dets=new ArrayList<TaskDetails>();
		TasksReadEvent nare=null;

		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"action");
		tasks=taskRepository.findAll(pageable);
		if (tasks!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+tasks.getTotalElements()+" total pages ="+tasks.getTotalPages());
			Iterator<Task> iter=tasks.iterator();
			while (iter.hasNext())
			{
				Task na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getAction());
				TaskDetails det=na.toTaskDetails();
				dets.add(det);
			}
			nare=new TasksReadEvent(dets,tasks.getTotalElements(),tasks.getTotalPages());
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findAll");
			nare=(TasksReadEvent) TasksReadEvent.notFound(null);
		}
		return nare;
	}

    @Override
    public UpdatedEvent updateTask(UpdateTaskEvent updateTaskEvent) {
        TaskDetails taskDetails = (TaskDetails) updateTaskEvent.getDetails();
        Task task = Task.fromTaskDetails(taskDetails);
        Long taskId = taskDetails.getNodeId();
        if(LOG.isDebugEnabled()) LOG.debug("taskId is " + taskId);
        Task taskOld = taskRepository.findOne(taskId);
        if(taskOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("task entity not found " + taskId);
            return TaskUpdatedEvent.notFound(taskId );
        }
        else{
            Task result = taskRepository.save(task);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            return new TaskUpdatedEvent(result.getNodeId(), result.toTaskDetails());
        }
    }

    @Override
    public UpdatedEvent completedTask(CompletedTaskEvent updateTaskEvent)
    {
        TaskCompleteDetails taskDetails = (TaskCompleteDetails) updateTaskEvent.getDetails();
        Long taskId = taskDetails.getTaskId();
        Long userId = taskDetails.getUserId();
        UpdatedEvent response=null;
        if(LOG.isDebugEnabled()) LOG.debug("taskId is " + taskId+" userId - "+userId);
        Task task = taskRepository.findOne(taskId);
        if(task == null)
        {
            if(LOG.isDebugEnabled()) LOG.debug("task entity not found " + taskId);
            response = TaskUpdatedEvent.notFound(taskId );
        }
        else
        {
            User user = userRepository.findOne(userId);
        	if (null==user)
        	{
                if(LOG.isDebugEnabled()) LOG.debug("user entity not found " + taskId);
                response = TaskUpdatedEvent.notFound(userId );
        	}
        	else
        	{
        		TaskComplete tc = TaskComplete.fromTaskCompleteDetails(taskDetails);
                TaskComplete result = taskRepository.taskCompleted(tc.getTask().getNodeId(),tc.getUser().getNodeId());
                if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
                response = new UpdatedEvent(result.getNodeId(), result.toTaskCompleteDetails());
        	}
        }
        return response;
    }

    @Override
    public DeletedEvent deleteTask(DeleteTaskEvent deleteTaskEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteTaskEvent= "+deleteTaskEvent);
        Long taskId = deleteTaskEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteTask("+taskId+")");
        Task task = taskRepository.findOne(taskId);
        if(task == null){
            return TaskDeletedEvent.notFound(taskId);
        }
        else{
            taskRepository.delete(task);
            TaskDeletedEvent taskDeletedEvent = new TaskDeletedEvent(taskId);
            return taskDeletedEvent;
        }
    }

	@Override
	public TasksReadEvent readCompletedTasks(
			ReadAllEvent readCompletedTasksEvent, Direction sortDirection,
			int pageNumber, int pageLength)
	{
		Page <Task>tasks=null;
		ArrayList<TaskDetails> dets=new ArrayList<TaskDetails>();
		TasksReadEvent nare=null;
		Long userId=readCompletedTasksEvent.getParentId();

		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"action");
		tasks=taskRepository.findCompletedTasks(userId,pageable);
		if (tasks!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+tasks.getTotalElements()+" total pages ="+tasks.getTotalPages());
			Iterator<Task> iter=tasks.iterator();
			while (iter.hasNext())
			{
				Task na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getAction());
				TaskDetails det=na.toTaskDetails();
				dets.add(det);
			}
			nare=new TasksReadEvent(dets,tasks.getTotalElements(),tasks.getTotalPages());
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findAll");
			nare=(TasksReadEvent) TasksReadEvent.notFound(null);
		}
		return nare;
	}
}
