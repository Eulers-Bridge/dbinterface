package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class CommentDeletedEvent extends DeletedEvent{
    public CommentDeletedEvent(Long commentId) {
        super(commentId);
    }
}
