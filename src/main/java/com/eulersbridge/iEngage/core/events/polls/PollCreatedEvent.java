package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class PollCreatedEvent extends CreatedEvent {
    private PollDetails pollDetails;
    private Long pollId;

    public PollCreatedEvent( Long pollId, PollDetails pollDetails) {
        this.pollDetails = pollDetails;
        this.pollId = pollId;
    }

    public PollCreatedEvent(Long pollId) {
        this.pollId = pollId;
    }

    public PollDetails getPollDetails() {
        return pollDetails;
    }

    @Override
    public PollDetails getDetails()
    {
        return pollDetails;
    }

    public void setPollDetails(PollDetails pollDetails) {
        this.pollDetails = pollDetails;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }
}
