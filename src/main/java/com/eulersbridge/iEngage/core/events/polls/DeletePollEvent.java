package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeletePollEvent extends DeleteEvent{
    private final Long pollId;

    public DeletePollEvent(Long pollId) {
        this.pollId = pollId;
    }

    public Long getPollId() {
        return pollId;
    }
}
