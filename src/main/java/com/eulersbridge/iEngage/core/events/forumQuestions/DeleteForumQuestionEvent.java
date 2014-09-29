package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteForumQuestionEvent extends DeleteEvent {
    private final Long forumQuestionId;

    public DeleteForumQuestionEvent(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }
}
