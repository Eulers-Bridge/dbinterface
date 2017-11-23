package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.RequestHandledEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by darcular on 5/11/14.
 */
public interface LikesService {
  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public LikeableObjectLikesEvent likes(LikesLikeableObjectEvent likesLikeableObjectEvent, Sort.Direction sortDirection, int pageNumber, int pageSize);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public LikedEvent like(LikeEvent likeEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "') and #email==authentication.name")
  public RequestHandledEvent unlike(String email, Long objId);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public LikedEvent isLikedBy(LikeEvent likeEvent);
}
