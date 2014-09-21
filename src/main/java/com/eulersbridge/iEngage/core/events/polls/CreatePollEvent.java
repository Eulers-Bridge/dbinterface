package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CreatePollEvent extends CreateEvent {
    private PollDetails pollDetails;

    private static Logger LOG = LoggerFactory.getLogger(CreatePollEvent.class);

    public CreatePollEvent(Long id, PollDetails pollDetails){
        if (LOG.isDebugEnabled()) LOG.debug("CreatePoll("+id+","+pollDetails+") = ");
        pollDetails.setPollId(id);
        this.pollDetails = pollDetails;
    }

    public CreatePollEvent(PollDetails pollDetails){
        if (LOG.isDebugEnabled()) LOG.debug("CreatePollEvent("+pollDetails+") = ");
        this.pollDetails = pollDetails;
    }

    public PollDetails getPollDetails() {
        return pollDetails;
    }

    public void setPollDetails(PollDetails pollDetails) {
        this.pollDetails = pollDetails;
    }
}
