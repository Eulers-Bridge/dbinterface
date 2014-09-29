package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadForumQuestionEvent extends RequestReadEvent {
    private Long forumQuestionId;

    public RequestReadForumQuestionEvent(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }
}
