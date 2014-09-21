package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.polls.*;

/**
 * Created by darcular on 21/09/14.
 */
public interface PollService {
    public ReadPollEvent requestReadPoll(RequestReadPollEvent requestReadPollEvent);
    public PollCreatedEvent createPoll(CreatePollEvent createPollEvent);
    public PollDeletedEvent deletePoll(DeletePollEvent deletePollEvent);
    public PollUpdatedEvent updatePoll(UpdatePollEvent updatePollEvent);
}
