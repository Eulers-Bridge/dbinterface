package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.comments.CommentDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Comment extends Node {
  private static Logger LOG = LoggerFactory.getLogger(Comment.class);

  private Long timestamp;
  private String content;

  @Relationship(type = DataConstants.POST_COMMENT, direction = Relationship.INCOMING)
  private Node user;

  @Relationship(type = DataConstants.HAS_COMMENT, direction = Relationship.INCOMING)
  private Node target;

  public Comment() {
  }

  public Comment(Long nodeId) {
    super(nodeId);
  }

  public static Comment fromCommentDetails(CommentDetails commentDetails) {
    Comment comment = null;
    if (commentDetails != null) {
      if (LOG.isTraceEnabled())
        LOG.trace("fromCommentDetails(" + commentDetails + ")");
      comment = new Comment();
      comment.setNodeId(commentDetails.getNodeId());
      comment.setContent(commentDetails.getContent());
      comment.setTimestamp(commentDetails.getTimestamp());
      if (LOG.isTraceEnabled()) LOG.trace("comment " + comment);
    }
    return comment;
  }

  public CommentDetails toCommentDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toCommentDetails(" + this + ")");
    CommentDetails commentDetails = new CommentDetails();
    commentDetails.setNodeId(getNodeId());
    commentDetails.setTimestamp(getTimestamp());
    commentDetails.setContent(getContent());

    commentDetails.setTargetId(target.nodeId);

    if (user != null && user instanceof User) {
      commentDetails.setUserName(getUser$().getGivenName());
      commentDetails.setUserEmail(getUser$().getEmail());
      PhotoDetails photoDetails = new PhotoDetails();
      photoDetails.setOwnerId(getUser$().nodeId);
      photoDetails.setUrl(getUser$().getProfilePhoto());
      commentDetails.setProfilePhotoDetails(photoDetails);
    }

    return commentDetails;
  }

  public Node getTarget() {
    return target;
  }

  public void setTarget(Node target) {
    this.target = target;
  }

  public User getUser$() {
    return (User) user;
  }

  public Node getUser() {
    return user;
  }

  public void setUser(Node user) {
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
