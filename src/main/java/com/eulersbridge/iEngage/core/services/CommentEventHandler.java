package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.comments.*;
import com.eulersbridge.iEngage.core.services.interfacePack.CommentService;
import com.eulersbridge.iEngage.database.domain.Comment;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.CommentRepository;
import com.eulersbridge.iEngage.database.repository.NodeRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * @author Yikai Gong
 */
@Service
public class CommentEventHandler implements CommentService {

  private static Logger LOG = LoggerFactory.getLogger(CommentEventHandler.class);

  private UserRepository userRepository;
  private CommentRepository commentRepository;
  private NodeRepository nodeRepository;

  @Autowired
  public CommentEventHandler(UserRepository userRepository, CommentRepository commentRepository, NodeRepository nodeRepository) {
    this.userRepository = userRepository;
    this.commentRepository = commentRepository;
    this.nodeRepository = nodeRepository;
  }

  @Override
  public CreatedEvent createComment(CreateCommentEvent createCommentEvent) {
    CommentDetails commentDetails = (CommentDetails) createCommentEvent.getDetails();
    CreatedEvent commentCreatedEvent;
    Long targetId = commentDetails.getTargetId();
    String userEmail = commentDetails.getUserEmail();
    User user = userRepository.findByEmail(userEmail);
    if (user == null) {
      commentCreatedEvent = CommentCreatedEvent.userNotFound();
    } else {
      Node object = nodeRepository.findById(targetId, 0).get();
      if (object == null) {
        commentCreatedEvent = CommentCreatedEvent.targetNotFound(targetId);
      } else {
        Comment comment = Comment.fromCommentDetails(commentDetails);
        comment.setTimestamp(new Date().getTime());
        comment.setUser(user.toNode());
        comment.setTarget(object);
        Comment result = commentRepository.save(comment);
        if ((result == null) || result.getNodeId() == null)
          commentCreatedEvent = CommentCreatedEvent.failed(commentDetails);
        else
          commentCreatedEvent = new CommentCreatedEvent(result.toCommentDetails());
      }
    }
    return commentCreatedEvent;
  }

  @Override
  public ReadEvent requestReadComment(RequestReadCommentEvent requestReadCommentEvent) {
    Comment comment = commentRepository.findById(requestReadCommentEvent.getNodeId()).get();
    ReadEvent commentReadEvent;
    if (comment != null) {
      commentReadEvent = new CommentReadEvent(comment.getNodeId(), comment.toCommentDetails());
    } else {
      commentReadEvent = CommentReadEvent.notFound(requestReadCommentEvent.getNodeId());
    }
    return commentReadEvent;
  }

  @Override
  public DeletedEvent deleteComment(DeleteCommentEvent deleteCommentEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deleteCommentEvent= " + deleteCommentEvent);
    Long commentId = deleteCommentEvent.getNodeId();
    if (LOG.isDebugEnabled()) LOG.debug("deleteComment(" + commentId + ")");
    Comment comment = commentRepository.findById(commentId).get();
    if (comment == null) {
      return CommentDeletedEvent.notFound(commentId);
    } else {
      commentRepository.delete(comment);
      CommentDeletedEvent commentDeletedEvent = new CommentDeletedEvent(commentId);
      return commentDeletedEvent;
    }
  }

  @Override
  public AllReadEvent readComments(ReadAllEvent requestReadCommentsEvent, Sort.Direction sortDirection, int pageNumber, int pageLength) {
    Long targetId = requestReadCommentsEvent.getParentId();
    Page<Comment> comments = null;
    ArrayList<CommentDetails> dets = new ArrayList<CommentDetails>();
    AllReadEvent commentsReadEvent = null;

    if (LOG.isDebugEnabled()) LOG.debug("targetId " + targetId);
    Pageable pageable = PageRequest.of(pageNumber, pageLength, sortDirection, "r.timestamp");
    comments = commentRepository.findByTargetId(targetId, pageable);
    if (comments != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + comments.getTotalElements() + " total pages =" + comments.getTotalPages());
      Iterator<Comment> iter = comments.iterator();
      while (iter.hasNext()) {
        Comment na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getNodeId());
        CommentDetails det = na.toCommentDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found instId.
        Node owner = nodeRepository.findById(targetId, 0).get();
        if ((null == owner) || (null == owner.getNodeId())) {
          if (LOG.isDebugEnabled()) LOG.debug("Comment-able Object not found");
          commentsReadEvent = AllReadEvent.notFound(targetId);
        } else {
          commentsReadEvent = new AllReadEvent(targetId, dets, comments.getTotalElements(), comments.getTotalPages());
        }
      } else {
        commentsReadEvent = new AllReadEvent(targetId, dets, comments.getTotalElements(), comments.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByTargetId");
      commentsReadEvent = AllReadEvent.notFound(null);
    }
    return commentsReadEvent;
  }

  @Override
  public UpdatedEvent updateComment(UpdateCommentEvent updateCommentEvent) {
    CommentDetails commentDetails = (CommentDetails) updateCommentEvent.getDetails();
    Comment comment = Comment.fromCommentDetails(commentDetails);
    Long commentId = commentDetails.getNodeId();
    Long targetId = commentDetails.getTargetId();
    String userEmail = commentDetails.getUserEmail();

    User user = userRepository.findByEmail(userEmail);
    Node object = nodeRepository.findById(targetId, 0).get();

    if (LOG.isDebugEnabled()) LOG.debug("commentId is " + commentId);
    Comment commentOld = commentRepository.findById(commentId).get();
    if (commentOld == null || user == null || object == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("comment or user/target entity not found " + commentId);
      return CommentUpdatedEvent.notFound(commentId);
    } else {
      comment.setTimestamp(new Date().getTime());
      comment.setUser(user.toNode());
      comment.setTarget(object);
      Comment result = commentRepository.save(comment, 0);
      if (LOG.isDebugEnabled())
        LOG.debug("updated successfully" + result.getNodeId());
      return new CommentUpdatedEvent(result.getNodeId(), result.toCommentDetails());
    }
  }
}
