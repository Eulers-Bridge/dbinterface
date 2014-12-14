package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.task.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface TaskService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public TaskCreatedEvent createTask(CreateTaskEvent createTaskEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadTask(RequestReadTaskEvent requestReadTaskEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateCandidate(UpdateTaskEvent updateTaskEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteCandidate(DeleteTaskEvent deleteTaskEvent);
}
