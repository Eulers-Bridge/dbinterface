package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.comments.*;
import com.eulersbridge.iEngage.security.SecurityConstants;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface CommentService {
    @PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public CreatedEvent createComment(CreateCommentEvent createCommentEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public ReadEvent requestReadComment(RequestReadCommentEvent requestReadCommentEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public DeletedEvent deleteComment(DeleteCommentEvent deleteCommentEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public AllReadEvent readComments(ReadAllEvent readCommentsEvent,
                                              Sort.Direction sortDirection, int pageNumber, int pageLength);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent updateComment(UpdateCommentEvent updateCommentEvent);
}
