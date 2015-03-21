package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class CommentReadEvent extends ReadEvent{
    public CommentReadEvent(Long commentId) {
        super(commentId);
    }

    public CommentReadEvent(Long commentId, CommentDetails commentDetails) {
        super(commentId, commentDetails);
    }
}
