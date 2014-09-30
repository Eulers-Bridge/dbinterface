package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class ForumQuestionUpdatedEvent extends UpdatedEvent {
    private Long forumQuestionId;
    private ForumQuestionDetails forumQuestionDetails;

    public ForumQuestionUpdatedEvent(Long forumQuestionId, ForumQuestionDetails forumQuestionDetails) {
        this.forumQuestionId = forumQuestionId;
        this.forumQuestionDetails = forumQuestionDetails;
    }

    public ForumQuestionUpdatedEvent(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }

    public ForumQuestionDetails getForumQuestionDetails() {
        return forumQuestionDetails;
    }

    public static ForumQuestionUpdatedEvent notFound(Long id){
        ForumQuestionUpdatedEvent forumQuestionUpdatedEvent = new ForumQuestionUpdatedEvent(id);
        forumQuestionUpdatedEvent.entityFound = false;
        return forumQuestionUpdatedEvent;
    }
}
