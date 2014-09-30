package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadForumQuestionEvent extends ReadEvent {
    private Long forumQuestionId;
    private ForumQuestionDetails forumQuestionDetails;

    public ReadForumQuestionEvent(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public ReadForumQuestionEvent(Long forumQuestionId, ForumQuestionDetails forumQuestionDetails) {
        this.forumQuestionId = forumQuestionId;
        this.forumQuestionDetails = forumQuestionDetails;
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }

    public ForumQuestionDetails getForumQuestionDetails() {
        return forumQuestionDetails;
    }

    public static ReadForumQuestionEvent notFound(Long forumQuestionId){
        ReadForumQuestionEvent readForumQuestionEvent = new ReadForumQuestionEvent(forumQuestionId);
        readForumQuestionEvent.entityFound = false;
        return readForumQuestionEvent;
    }
}
