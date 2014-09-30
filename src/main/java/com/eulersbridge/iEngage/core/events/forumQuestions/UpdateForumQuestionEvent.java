package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateForumQuestionEvent extends UpdateEvent {
    private Long forumQuestionId;
    private ForumQuestionDetails forumQuestionDetails;

    public UpdateForumQuestionEvent(Long forumQuestionId, ForumQuestionDetails forumQuestionDetails) {
        this.forumQuestionId = forumQuestionId;
        this.forumQuestionDetails = forumQuestionDetails;
        this.forumQuestionDetails.setForumQuestionId(forumQuestionId);
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }

    public ForumQuestionDetails getForumQuestionDetails() {
        return forumQuestionDetails;
    }
}
