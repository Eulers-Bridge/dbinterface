package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateCommentEvent extends UpdateEvent {
    public UpdateCommentEvent(Long commentId, CommentDetails commentDetails) {
        super(commentId, commentDetails);
    }
}
