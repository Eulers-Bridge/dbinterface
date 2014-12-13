package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadTaskEvent extends RequestReadEvent {
    public RequestReadTaskEvent(Long taskId) {
        super(taskId);
    }
}
