package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Yikai Gong
 */

public class CreateCandidateEvent extends CreateEvent {
    public CreateCandidateEvent(Details details) {
        super(details);
    }
}

