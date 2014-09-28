package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.forumQuestions.*;
import com.eulersbridge.iEngage.database.repository.ForumQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ForumQuestionEventHandler implements ForumQuestionService {
    private static Logger LOG = LoggerFactory.getLogger(ForumQuestionService.class);

    private ForumQuestionRepository forumQuestionRepository;

    public ForumQuestionEventHandler(ForumQuestionRepository forumQuestionRepository){
        this.forumQuestionRepository = forumQuestionRepository;
    }

    @Override
    public ForumQuestionCreatedEvent createForumQuestion(CreateForumQuestionEvent createForumQuestionEvent) {
        return null;
    }

    @Override
    public ReadForumQuestionEvent requestReadForumQuestion(RequestReadForumQuestionEvent requestReadForumQuestionEvent) {
        return null;
    }

    @Override
    public ForumQuestionUpdatedEvent updateForumQuestion(UpdateForumQuestionEvent updateForumQuestionEvent) {
        return null;
    }

    @Override
    public ForumQuestionDeletedEvent deleteForumQuestion(DeleteForumQuestionEvent deleteForumQuestionEvent) {
        return null;
    }
}
