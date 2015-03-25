package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Yikai Gong
 */

public class CommentsReadEvent extends AllReadEvent{

    Iterable<CommentDetails> commentDetailses;

    public CommentsReadEvent(Long targetId, Iterable<CommentDetails> commentDetailses, Long totalItems, Integer totalPages)
    {
        super(targetId, totalItems, totalPages);
        this.commentDetailses = commentDetailses;
    }

    public CommentsReadEvent(Long targetId, Iterable<CommentDetails> commentDetailses)
    {
        super(targetId);
        this.commentDetailses = commentDetailses;
    }

    public CommentsReadEvent(Long targetId){
        super(targetId);
    }

    public CommentsReadEvent(Iterable<CommentDetails> commentDetailses)
    {
        super(null);
        this.commentDetailses = commentDetailses;
    }

    public static CommentsReadEvent targetNotFound(Long targetId){
        CommentsReadEvent commentsReadEvent = new CommentsReadEvent(targetId);
        commentsReadEvent.entityFound = false;
        return commentsReadEvent;
    }

    public Iterable<CommentDetails> getCommentDetailses() {
        return commentDetailses;
    }

    public void setCommentDetailses(Iterable<CommentDetails> commentDetailses) {
        this.commentDetailses = commentDetailses;
    }
}
