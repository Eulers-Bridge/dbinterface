package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadCommentsEvent extends ReadAllEvent{
    public RequestReadCommentsEvent(Long targetId) {
        super(targetId);
    }
}
