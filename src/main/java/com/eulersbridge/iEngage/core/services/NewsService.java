package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.*;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

public interface NewsService 
{
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public NewsArticleCreatedEvent createNewsArticle(CreateNewsArticleEvent createNewsArticleEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public ReadNewsArticleEvent requestReadNewsArticle(RequestReadNewsArticleEvent requestReadNewsArticleEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public NewsArticleUpdatedEvent updateNewsArticle(UpdateNewsArticleEvent updateNewsArticleEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public NewsArticleDeletedEvent deleteNewsArticle(DeleteNewsArticleEvent deleteNewsArticleEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public NewsArticlesReadEvent readNewsArticles(ReadNewsArticlesEvent readNewsArticlesEvent,Direction sortDirection, int i, int j);
	@PreAuthorize("hasRole('ROLE_USER')")
	public NewsArticleLikedEvent likeNewsArticle(LikeEvent likeNewsArticlesEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public NewsArticleUnlikedEvent unlikeNewsArticle(LikeEvent unlikeNewsArticleEvent);
}
