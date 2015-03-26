package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.comments.CommentDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.*;

/**
 * @author Yikai Gong
 */

@RelationshipEntity(type=DatabaseDomainConstants.HAS_COMMENT)
public class Comment {
    @GraphId
    private Long id;
    @StartNode
    private User user;
    @EndNode
    private Commentable target;

    private Long timestamp;
    private String content;

    private static Logger LOG = LoggerFactory.getLogger(Comment.class);


    public static Comment fromCommentDetails(CommentDetails commentDetails){
        Comment comment = null;
        if(commentDetails != null){
            if (LOG.isTraceEnabled()) LOG.trace("fromCommentDetails("+commentDetails+")");
            comment = new Comment();
            comment.setId(commentDetails.getNodeId());
            comment.setContent(commentDetails.getContent());

            if (LOG.isTraceEnabled()) LOG.trace("comment "+comment);
        }
        return comment;
    }

    public CommentDetails toCommentDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toCommentDetails("+this+")");
        CommentDetails commentDetails = new CommentDetails();
        commentDetails.setNodeId(getId());
        commentDetails.setUserName(user.getGivenName());
        commentDetails.setUserEmail(user.getEmail());
        commentDetails.setTargetId(target.getNodeId());
        commentDetails.setTimestamp(getTimestamp());
        commentDetails.setContent(getContent());

        return commentDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commentable getTarget() {
        return target;
    }

    public void setTarget(Commentable target) {
        this.target = target;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
