package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class CommentUpdatedEvent extends UpdatedEvent {
    public CommentUpdatedEvent(Long commentId, CommentDetails commentDetails) {
        super(commentId, commentDetails);
    }
}
