package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.CompletedTaskEvent;
import com.eulersbridge.iEngage.core.events.task.CreateTaskEvent;
import com.eulersbridge.iEngage.core.events.task.DeleteTaskEvent;
import com.eulersbridge.iEngage.core.events.task.RequestReadTaskEvent;
import com.eulersbridge.iEngage.core.events.task.TasksReadEvent;
import com.eulersbridge.iEngage.core.events.task.UpdateTaskEvent;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface TaskService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public CreatedEvent createTask(CreateTaskEvent createTaskEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadTask(RequestReadTaskEvent requestReadTaskEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateTask(UpdateTaskEvent updateTaskEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public UpdatedEvent completedTask(CompletedTaskEvent updateTaskEvent);

    	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteTask(DeleteTaskEvent deleteTaskEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
	public TasksReadEvent readTasks(ReadAllEvent readTasksEvent,
			Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasRole('ROLE_USER')")
	public TasksReadEvent readCompletedTasks(ReadAllEvent readCompletedTasksEvent,
			Direction sortDirection, int pageNumber, int pageLength);
    
    @PreAuthorize("hasRole('ROLE_USER')")
	public TasksReadEvent readRemainingTasks(ReadAllEvent readCompletedTasksEvent,
			Direction sortDirection, int pageNumber, int pageLength);
}
