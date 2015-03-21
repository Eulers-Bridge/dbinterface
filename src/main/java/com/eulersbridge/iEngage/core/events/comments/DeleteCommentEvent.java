package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteCommentEvent extends DeleteEvent{
    public DeleteCommentEvent(Long commentId) {
        super(commentId);
    }
}
