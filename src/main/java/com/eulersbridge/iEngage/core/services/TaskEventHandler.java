package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.*;
import com.eulersbridge.iEngage.database.domain.Task;
import com.eulersbridge.iEngage.database.repository.TaskRepository;

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
    private static Logger LOG = LoggerFactory.getLogger(TaskService.class);

    private TaskRepository taskRepository;

    public TaskEventHandler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskCreatedEvent createTask(CreateTaskEvent createTaskEvent)
    {
        TaskDetails taskDetails = (TaskDetails) createTaskEvent.getDetails();
        Task task = Task.fromTaskDetails(taskDetails);
        Task result = taskRepository.save(task);
        TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent(result.toTaskDetails());
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
	public TasksReadEvent readTasks(ReadTasksEvent readTasksEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Page <Task>tasks=null;
		ArrayList<TaskDetails> dets=new ArrayList<TaskDetails>();
		TasksReadEvent nare=null;

		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"action");
		tasks=taskRepository.findAll(pageable);
		if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+tasks.getTotalElements()+" total pages ="+tasks.getTotalPages());
		if (tasks!=null)
		{
			Iterator<Task> iter=tasks.iterator();
			while (iter.hasNext())
			{
				Task na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getAction());
				TaskDetails det=na.toTaskDetails();
				dets.add(det);
			}
			nare=new TasksReadEvent(dets);
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
}
