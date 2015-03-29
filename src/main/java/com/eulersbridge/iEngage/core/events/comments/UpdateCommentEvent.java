package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateCommentEvent extends UpdateEvent {
    public UpdateCommentEvent(Long commnetId, CommentDetails commentDetails) {
        super(commnetId, commentDetails);
    }
}
