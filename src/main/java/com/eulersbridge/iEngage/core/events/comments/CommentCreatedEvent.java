package com.eulersbridge.iEngage.core.events.comments;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.Details;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CommentCreatedEvent extends CreatedEvent{
    private Long failedId;
    private boolean userFound = true;
    private boolean targetFound = true;

    private static Logger LOG = LoggerFactory.getLogger(CommentCreatedEvent.class);

    public CommentCreatedEvent(CommentDetails commentDetails) {
        super(commentDetails);
    }

    public CommentCreatedEvent(Long failedId) {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.failedId = failedId;
    }

    public CommentCreatedEvent() {
    }

    public boolean isUserFound() {
        return userFound;
    }

    public void setUserFound(boolean userFound) {
        this.userFound = userFound;
    }

    public boolean isTargetFound() {
        return targetFound;
    }

    public void setTargetFound(boolean targetFound) {
        this.targetFound = targetFound;
    }

    public Long getFailedId() {
        return failedId;
    }

    public void setFailedId(Long failedId) {
        this.failedId = failedId;
    }

    public static CommentCreatedEvent userNotFound(){
        CommentCreatedEvent failedEvent = new CommentCreatedEvent();
        failedEvent.setFailed(true);
        failedEvent.setUserFound(false);
        return failedEvent;
    }

    public static CommentCreatedEvent targetNotFound(Long targetId)
    {
        CommentCreatedEvent failedEvent = new CommentCreatedEvent(targetId);
        failedEvent.setFailed(true);
        failedEvent.setTargetFound(false);
        return failedEvent;
    }
}
