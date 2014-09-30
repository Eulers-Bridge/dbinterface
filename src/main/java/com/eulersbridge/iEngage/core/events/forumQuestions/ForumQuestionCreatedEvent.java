package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ForumQuestionCreatedEvent extends CreatedEvent {
    private Long forumQuestionId;
    private ForumQuestionDetails forumQuestionDetails;

    public ForumQuestionCreatedEvent(Long forumQuestionId, ForumQuestionDetails forumQuestionDetails) {
        this.forumQuestionDetails = forumQuestionDetails;
        this.forumQuestionId = forumQuestionId;
    }

    public ForumQuestionCreatedEvent(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }

    public void setForumQuestionId(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public ForumQuestionDetails getForumQuestionDetails() {
        return forumQuestionDetails;
    }

    public void setForumQuestionDetails(ForumQuestionDetails forumQuestionDetails) {
        this.forumQuestionDetails = forumQuestionDetails;
    }
}
