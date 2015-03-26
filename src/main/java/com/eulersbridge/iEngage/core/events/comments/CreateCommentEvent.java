package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Yikai Gong
 */

public class CreateCommentEvent extends CreateEvent {

    public CreateCommentEvent(CommentDetails commentDetails) {
        super(commentDetails);
    }
}
