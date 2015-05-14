package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.CompletedTaskEvent;
import com.eulersbridge.iEngage.core.events.task.CreateTaskEvent;
import com.eulersbridge.iEngage.core.events.task.DeleteTaskEvent;
import com.eulersbridge.iEngage.core.events.task.RequestReadTaskEvent;
import com.eulersbridge.iEngage.core.events.task.UpdateTaskEvent;
import com.eulersbridge.iEngage.security.SecurityConstants;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface TaskService
{
    @PreAuthorize("hasAnyRole('"+SecurityConstants.CONTENT_MANAGER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public CreatedEvent createTask(CreateTaskEvent createTaskEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public ReadEvent requestReadTask(RequestReadTaskEvent requestReadTaskEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.CONTENT_MANAGER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent updateTask(UpdateTaskEvent updateTaskEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public UpdatedEvent completedTask(CompletedTaskEvent updateTaskEvent);

    	@PreAuthorize("hasAnyRole('"+SecurityConstants.CONTENT_MANAGER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public DeletedEvent deleteTask(DeleteTaskEvent deleteTaskEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readTasks(ReadAllEvent readTasksEvent,
			Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readCompletedTasks(ReadAllEvent readCompletedTasksEvent,
			Direction sortDirection, int pageNumber, int pageLength);
    
    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readRemainingTasks(ReadAllEvent readCompletedTasksEvent,
			Direction sortDirection, int pageNumber, int pageLength);
}
