package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Yikai Gong
 */

public class CreateCommentEvent extends CreateEvent {

    public CreateCommentEvent(CommentDetails commentDetails) {
        super(commentDetails);
    }
}
