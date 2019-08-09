package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.newsArticles.*;
import com.eulersbridge.iEngage.rest.domain.NewsArticleDomain;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

public interface NewsService {
  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public RequestHandledEvent createNewsArticle(NewsArticleDomain newsArticleDomain);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public RequestHandledEvent requestReadNewsArticle(Long articleId, String userEmail);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updateNewsArticle(UpdateNewsArticleEvent updateNewsArticleEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deleteNewsArticle(DeleteNewsArticleEvent deleteNewsArticleEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public NewsArticlesReadEvent readNewsArticles(ReadAllEvent readNewsArticlesEvent, Direction sortDirection, int i, int j);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public NewsArticleLikesEvent likesNewsArticle(LikesNewsArticleEvent likesNewsArticleEvent, Direction sortDirection, int pageNumber, int pageSize);
}
