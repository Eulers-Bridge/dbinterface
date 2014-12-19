package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Yikai Gong
 */

public class CreateTaskEvent extends CreateEvent{
    public CreateTaskEvent(Details details) {
        super(details);
    }
}
