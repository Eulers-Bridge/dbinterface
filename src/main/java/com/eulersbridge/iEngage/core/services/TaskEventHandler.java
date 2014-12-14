package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.*;
import com.eulersbridge.iEngage.database.domain.Task;
import com.eulersbridge.iEngage.database.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public TaskCreatedEvent createTask(CreateTaskEvent createTaskEvent) {
        TaskDetails taskDetails = (TaskDetails) createTaskEvent.getDetails();
        Task task = Task.fromTaskDetails(taskDetails);
        Task result = taskRepository.save(task);
        TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent(result.getTaskId(), result.toTaskDetails());
        return taskCreatedEvent;
    }

    @Override
    public ReadEvent requestReadTask(RequestReadTaskEvent requestReadTaskEvent) {
        Task task = taskRepository.findOne(requestReadTaskEvent.getNodeId());
        ReadEvent readTaskEvent;
        if(task != null){
            readTaskEvent = new ReadTaskEvent(task.getTaskId(), task.toTaskDetails());
        }
        else{
            readTaskEvent = ReadTaskEvent.notFound(requestReadTaskEvent.getNodeId());
        }
        return readTaskEvent;
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
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getTaskId());
            return new TaskUpdatedEvent(result.getTaskId(), result.toTaskDetails());
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
