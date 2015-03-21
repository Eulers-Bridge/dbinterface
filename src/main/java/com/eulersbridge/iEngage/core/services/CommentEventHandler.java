package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.comments.*;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.CommentReposotory;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * @author Yikai Gong
 */

public class CommentEventHandler implements CommentService {

    private static Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private UserRepository userRepository;
    private CommentReposotory commentReposotory;

    public CommentEventHandler(UserRepository userRepository, CommentReposotory commentReposotory) {
        this.userRepository = userRepository;
        this.commentReposotory = commentReposotory;
    }

    @Override
    public CreatedEvent createComment(CreateCommentEvent createCommentEvent) {
        CommentDetails commentDetails = (CommentDetails) createCommentEvent.getDetails();
        CreatedEvent commentCreatedEvent;
        Long targetId = commentDetails.getTargetId();
        String userEmail = commentDetails.getUserEmail();
        User user = userRepository.findByEmail(userEmail);
        if(user == null){
            commentCreatedEvent = CommentCreatedEvent.userNotFound();
        }
        else{
            NodeObject object = commentReposotory.findCommentTarget(targetId);
            if(object == null || !(object instanceof Commentable)){
                commentCreatedEvent = CommentCreatedEvent.targetNotFound(targetId);
            }
            else{
                Commentable target = (Commentable) object;
                Comment comment = Comment.fromCommentDetails(commentDetails);
                comment.setTimestamp(new Date().getTime());
                comment.setUser(user);
                comment.setTarget(target);
                Comment result = commentReposotory.save(comment);
                if((result==null)||result.getId() == null)
                    commentCreatedEvent = CommentCreatedEvent.failed(commentDetails);
                else
                    commentCreatedEvent = new CommentCreatedEvent(result.toCommentDetails());
            }
        }
        return commentCreatedEvent;
    }

    @Override
    public ReadEvent requestReadComment(RequestReadCommentEvent requestReadCommentEvent) {
        return null;
    }

    @Override
    public DeletedEvent deleteComment(DeleteCommentEvent deleteCommentEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteCommentEvent= "+deleteCommentEvent);
        Long commentId = deleteCommentEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteComment("+commentId+")");
        Comment comment = commentReposotory.findOne(commentId);
        if(comment == null){
            return CommentDeletedEvent.notFound(commentId);
        }
        else{
            commentReposotory.delete(comment);
            CommentDeletedEvent commentDeletedEvent = new CommentDeletedEvent(commentId);
            return commentDeletedEvent;
        }
    }

    @Override
    public CommentsReadEvent readComments(ReadAllEvent requestReadCommentsEvent, Sort.Direction sortDirection, int pageNumber, int pageLength) {
        Long targetId = requestReadCommentsEvent.getParentId();
        Page<Comment> comments = null;
        ArrayList<CommentDetails> dets = new ArrayList<CommentDetails>();
        CommentsReadEvent commentsReadEvent = null;

        if (LOG.isDebugEnabled()) LOG.debug("targetId "+targetId);
        Pageable pageable= new PageRequest(pageNumber, pageLength, sortDirection, "r.timestamp");
        comments = commentReposotory.findByTargetId(targetId, pageable);
        if(comments != null) {
            if (LOG.isDebugEnabled())
                LOG.debug("Total elements = " + comments.getTotalElements() + " total pages =" + comments.getTotalPages());
            Iterator<Comment> iter = comments.iterator();
            while (iter.hasNext()) {
                Comment na = iter.next();
                if (LOG.isTraceEnabled()) LOG.trace("Converting to details - " + na.getId());
                CommentDetails det = na.toCommentDetails();
                dets.add(det);
            }
            if (0 == dets.size()) {
                // Need to check if we actually found instId.
                NodeObject nodeObject = commentReposotory.findCommentTarget(targetId);
                if ((null == nodeObject) || null == nodeObject.getNodeId() || !(nodeObject instanceof Commentable)) {
                    if (LOG.isDebugEnabled()) LOG.debug("Comment-able Object not found");
                    commentsReadEvent = CommentsReadEvent.targetNotFound(targetId);
                } else {
                    commentsReadEvent = new CommentsReadEvent(targetId, dets, comments.getTotalElements(), comments.getTotalPages());
                }
            }
            else {
                commentsReadEvent = new CommentsReadEvent(targetId, dets, comments.getTotalElements(),comments.getTotalPages());
            }
        }
        else{
            if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByTargetId");
            commentsReadEvent = CommentsReadEvent.targetNotFound(targetId);
        }
        return commentsReadEvent;
    }
}
