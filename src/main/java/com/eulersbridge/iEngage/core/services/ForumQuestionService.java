package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.*;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 28/09/14.
 */
public interface ForumQuestionService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public ForumQuestionCreatedEvent createForumQuestion(CreateForumQuestionEvent createForumQuestionEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent readForumQuestion(ReadForumQuestionEvent readForumQuestionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateForumQuestion(UpdateForumQuestionEvent updateForumQuestionEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteForumQuestion(DeleteForumQuestionEvent deleteForumQuestionEvent);
}
