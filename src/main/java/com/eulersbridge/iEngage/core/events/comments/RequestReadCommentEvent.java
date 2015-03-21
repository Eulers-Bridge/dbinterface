package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadCommentEvent extends RequestReadEvent {
    public RequestReadCommentEvent(Long commentId) {
        super(commentId);
    }
}
