package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.*;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 21/09/14.
 */
public interface PollService {

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadPoll(RequestReadPollEvent requestReadPollEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public PollCreatedEvent createPoll(CreatePollEvent createPollEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deletePoll(DeletePollEvent deletePollEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updatePoll(UpdatePollEvent updatePollEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
	public PollsReadEvent findPolls(ReadPollsEvent readPollsEvent,
			Direction sortDirection, int pageNumber, int pageLength);
}
