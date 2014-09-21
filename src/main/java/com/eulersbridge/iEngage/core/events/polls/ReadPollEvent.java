package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadPollEvent extends ReadEvent {
    private Long pollId;
    private PollDetails pollDetails;

    public ReadPollEvent(Long pollId){
        this.pollId = pollId;
    }

    public ReadPollEvent(Long pollId, PollDetails pollDetails){
        this.pollId = pollId;
        this.pollDetails = pollDetails;
    }

    public Long getPollId() {
        return pollId;
    }

    public PollDetails getPollDetails() {
        return pollDetails;
    }

    public static ReadPollEvent notFound(Long pollId){
        ReadPollEvent readPollEvent = new ReadPollEvent(pollId);
        readPollEvent.entityFound = false;
        return readPollEvent;
    }
}
