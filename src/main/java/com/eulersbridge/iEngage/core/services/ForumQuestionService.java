package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.forumQuestions.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 28/09/14.
 */
public interface ForumQuestionService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public ForumQuestionCreatedEvent createForumQuestion(CreateForumQuestionEvent createForumQuestionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadForumQuestionEvent requestReadForumQuestion(RequestReadForumQuestionEvent requestReadForumQuestionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public ForumQuestionUpdatedEvent updateForumQuestion(UpdateForumQuestionEvent updateForumQuestionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public ForumQuestionDeletedEvent deleteForumQuestion(DeleteForumQuestionEvent deleteForumQuestionEvent);
}