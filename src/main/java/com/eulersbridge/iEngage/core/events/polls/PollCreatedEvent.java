package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class PollCreatedEvent extends CreatedEvent {
    private Long pollId;

    public PollCreatedEvent( Long pollId, PollDetails pollDetails)
    {
        super(pollDetails);
        this.pollId = pollId;
    }

    public PollCreatedEvent(Long pollId) {
        this.pollId = pollId;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }
}
