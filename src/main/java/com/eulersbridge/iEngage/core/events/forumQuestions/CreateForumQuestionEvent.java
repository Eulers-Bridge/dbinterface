package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CreateForumQuestionEvent extends CreateEvent {
    private ForumQuestionDetails forumQuestionDetails;

    private static Logger LOG = LoggerFactory.getLogger(CreateForumQuestionEvent.class);

    public CreateForumQuestionEvent(Long id, ForumQuestionDetails forumQuestionDetails) {
        forumQuestionDetails.setForumQuestionId(id);
        this.forumQuestionDetails = forumQuestionDetails;
    }

    public CreateForumQuestionEvent(ForumQuestionDetails forumQuestionDetails) {
        this.forumQuestionDetails = forumQuestionDetails;
    }

    public ForumQuestionDetails getForumQuestionDetails() {
        return forumQuestionDetails;
    }

    public void setForumQuestionDetails(ForumQuestionDetails forumQuestionDetails) {
        this.forumQuestionDetails = forumQuestionDetails;
    }
}
