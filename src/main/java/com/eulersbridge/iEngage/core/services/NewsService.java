package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.*;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

public interface NewsService {
  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public NewsArticleCreatedEvent createNewsArticle(CreateNewsArticleEvent createNewsArticleEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent requestReadNewsArticle(RequestReadNewsArticleEvent requestReadNewsArticleEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updateNewsArticle(UpdateNewsArticleEvent updateNewsArticleEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deleteNewsArticle(DeleteNewsArticleEvent deleteNewsArticleEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public NewsArticlesReadEvent readNewsArticles(ReadAllEvent readNewsArticlesEvent, Direction sortDirection, int i, int j);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public NewsArticleLikesEvent likesNewsArticle(LikesNewsArticleEvent likesNewsArticleEvent, Direction sortDirection, int pageNumber, int pageSize);
}
