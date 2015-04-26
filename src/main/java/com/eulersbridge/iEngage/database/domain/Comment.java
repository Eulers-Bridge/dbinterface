package com.eulersbridge.iEngage.database.domain;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.comments.CommentDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.*;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Comment {
    @GraphId
    private Long nodeId;

    @RelatedTo(type = DatabaseDomainConstants.POST_COMMENT, direction = Direction.INCOMING)
    @Fetch
    private User user;

    @RelatedTo(type = DatabaseDomainConstants.HAS_COMMENT, direction = Direction.INCOMING)
    private Owner target;

    private Long timestamp;
    private String content;

    private static Logger LOG = LoggerFactory.getLogger(Comment.class);


    public static Comment fromCommentDetails(CommentDetails commentDetails){
        Comment comment = null;
        if(commentDetails != null){
            if (LOG.isTraceEnabled()) LOG.trace("fromCommentDetails("+commentDetails+")");
            comment = new Comment();
            comment.setNodeId(commentDetails.getNodeId());
            comment.setContent(commentDetails.getContent());
            comment.setTimestamp(commentDetails.getTimestamp());
            if (LOG.isTraceEnabled()) LOG.trace("comment "+comment);
        }
        return comment;
    }

    public CommentDetails toCommentDetails()
    {
        if (LOG.isTraceEnabled()) LOG.trace("toCommentDetails("+this+")");
        CommentDetails commentDetails = new CommentDetails();
        commentDetails.setNodeId(getNodeId());
        commentDetails.setUserName(user.getGivenName());
        commentDetails.setUserEmail(user.getEmail());
        commentDetails.setTargetId(target.getNodeId());
        commentDetails.setTimestamp(getTimestamp());
        commentDetails.setContent(getContent());
        Iterator<Photo> photos=null;
        if (getUser().getPhotos()!=null)
        {
        	photos=getUser().getPhotos().iterator();
        	if (photos.hasNext()) commentDetails.setProfilePhotoDetails(photos.next().toPhotoDetails());
        }

        return commentDetails;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Owner getTarget() {
        return target;
    }

    public void setTarget(Owner target) {
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
