package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.comments.*;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.core.events.ticket.UpdateTicketEvent;
import com.eulersbridge.iEngage.core.services.CommentService;
import com.eulersbridge.iEngage.rest.domain.Comment;
import com.eulersbridge.iEngage.rest.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort.Direction;

import java.util.Iterator;


/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class CommentController {
    @Autowired
    CommentService commentService;

    private static Logger LOG = LoggerFactory.getLogger(CommentController.class);

    public CommentController() {
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.COMMENT_LABEL)
    public @ResponseBody ResponseEntity<Comment>
    createComment(@RequestBody Comment comment){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create comment "+comment);
        CreateCommentEvent createCommentEvent = new CreateCommentEvent(comment.toCommentDetails());
        CreatedEvent commentCreatedEvent = commentService.createComment(createCommentEvent);
        ResponseEntity<Comment> response;
        if(null == commentCreatedEvent)
        {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if ((commentCreatedEvent.getClass()==CommentCreatedEvent.class)&&(!(((CommentCreatedEvent)commentCreatedEvent).isTargetFound())||!(((CommentCreatedEvent)commentCreatedEvent).isUserFound())))
        {
            response = new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }
        else if((null==commentCreatedEvent.getNodeId())||(commentCreatedEvent.isFailed()))
        {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            Comment result = Comment.fromCommentDetails((CommentDetails) commentCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("comment"+result.toString());
            response = new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return response;
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.COMMENT_LABEL+"/{commentId}")
    public @ResponseBody ResponseEntity<Boolean>
    createComment(@PathVariable Long commentId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete comment. " + commentId);
        DeletedEvent commentDeletedEvent = commentService.deleteComment(new DeleteCommentEvent(commentId));
        ResponseEntity<Boolean> response;

        if (commentDeletedEvent.isDeletionCompleted())
            response=new ResponseEntity<Boolean>(commentDeletedEvent.isDeletionCompleted(),HttpStatus.OK);
        else if (commentDeletedEvent.isEntityFound())
            response=new ResponseEntity<Boolean>(commentDeletedEvent.isDeletionCompleted(),HttpStatus.NOT_FOUND);
        else
            response=new ResponseEntity<Boolean>(commentDeletedEvent.isDeletionCompleted(),HttpStatus.GONE);
        return response;
    }

    //Read All
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.COMMENT_LABEL
            + "/{targetId}")
    public @ResponseBody ResponseEntity<Iterator<Comment>> findComments(
            @PathVariable Long targetId,
            @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
            @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
    {
        int pageNumber = 0;
        int pageLength = 10;
        pageNumber = Integer.parseInt(page);
        pageLength = Integer.parseInt(pageSize);
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to retrieve comments from target " + targetId + '.');

        Direction sortDirection = Direction.DESC;
        if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
        CommentsReadEvent commentsReadEvent = commentService.readComments(new RequestReadCommentsEvent(targetId), sortDirection,
                pageNumber, pageLength);

        if (!commentsReadEvent.isEntityFound())
        {
            return new ResponseEntity<Iterator<Comment>>(HttpStatus.NOT_FOUND);
        }
        Iterator<Comment> candidates = Comment.toCommentIterator(commentsReadEvent.getCommentDetailses().iterator());
        return new ResponseEntity<Iterator<Comment>>(candidates, HttpStatus.OK);
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.COMMENT_LABEL+"/{commentId}")
    public @ResponseBody ResponseEntity<Comment>
    updateComment(@PathVariable Long commentId, @RequestBody Comment comment){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update comment. " + commentId);
        UpdatedEvent commentUpdatedEvent = commentService.updateComment(new UpdateCommentEvent(commentId, comment.toCommentDetails()));
        if(null != commentUpdatedEvent){
            if (LOG.isDebugEnabled()) LOG.debug("commentUpdatedEvent - "+commentUpdatedEvent);
            if(commentUpdatedEvent.isEntityFound()){
                Comment result = Comment.fromCommentDetails((CommentDetails) commentUpdatedEvent.getDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
