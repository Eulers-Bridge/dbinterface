package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class ForumQuestionDeletedEvent extends DeletedEvent {
    private final Long forumQuestionId;
    private boolean deletionCompleted = true;

    public ForumQuestionDeletedEvent(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public static ForumQuestionDeletedEvent deletionForbidden(Long forumQuestionId){
        ForumQuestionDeletedEvent forumQuestionDeletedEvent = new ForumQuestionDeletedEvent(forumQuestionId);
        forumQuestionDeletedEvent.entityFound = true;
        forumQuestionDeletedEvent.deletionCompleted = false;
        return forumQuestionDeletedEvent;
    }

    public static ForumQuestionDeletedEvent notFound(Long forumQuestionId){
        ForumQuestionDeletedEvent forumQuestionDeletedEvent = new ForumQuestionDeletedEvent(forumQuestionId);
        forumQuestionDeletedEvent.entityFound = false;
        forumQuestionDeletedEvent.deletionCompleted = false;
        return forumQuestionDeletedEvent;
    }

    public boolean isDeletionCompleted(){
        return this.deletionCompleted;
    }
}


