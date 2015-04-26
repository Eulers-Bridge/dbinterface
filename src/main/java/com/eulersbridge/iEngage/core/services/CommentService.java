package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.comments.*;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface CommentService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public CreatedEvent createComment(CreateCommentEvent createCommentEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadComment(RequestReadCommentEvent requestReadCommentEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteComment(DeleteCommentEvent deleteCommentEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public AllReadEvent readComments(ReadAllEvent readCommentsEvent,
                                              Sort.Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateComment(UpdateCommentEvent updateCommentEvent);
}
