package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdatePollEvent extends UpdateEvent {
    private Long pollId;
    private PollDetails pollDetails;

    public UpdatePollEvent(Long pollId, PollDetails pollDetails) {
        this.pollId = pollId;
        this.pollDetails = pollDetails;
        this.pollDetails.setPollId(pollId);
    }

    public Long getPollId() {
        return pollId;
    }

    public PollDetails getPollDetails() {
        return pollDetails;
    }
}
