package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadPollEvent extends RequestReadEvent {
    private Long pollId;

    public RequestReadPollEvent(Long pollId){
        this.pollId = pollId;
    }

    public Long getPollId() {
        return pollId;
    }
}
