package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class PollUpdatedEvent extends UpdatedEvent {
    private Long pollId;
    private PollDetails pollDetails;

    public PollUpdatedEvent(Long pollId, PollDetails pollDetails) {
        this.pollId = pollId;
        this.pollDetails = pollDetails;
    }

    public PollUpdatedEvent(Long pollId) {
        this.pollId = pollId;
    }

    public Long getPollId() {
        return pollId;
    }

    public PollDetails getPollDetails() {
        return pollDetails;
    }

    public static PollUpdatedEvent notFound(Long id){
        PollUpdatedEvent pollUpdatedEvent = new PollUpdatedEvent(id);
        pollUpdatedEvent.entityFound = false;
        return pollUpdatedEvent;
    }
}
