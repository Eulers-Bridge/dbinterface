package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.polls.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 21/09/14.
 */
public interface PollService {

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadPollEvent requestReadPoll(RequestReadPollEvent requestReadPollEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public PollCreatedEvent createPoll(CreatePollEvent createPollEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public PollDeletedEvent deletePoll(DeletePollEvent deletePollEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public PollUpdatedEvent updatePoll(UpdatePollEvent updatePollEvent);
}
