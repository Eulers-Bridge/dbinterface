package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.*;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

public interface NewsService 
{
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public NewsArticleCreatedEvent createNewsArticle(CreateNewsArticleEvent createNewsArticleEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public ReadEvent requestReadNewsArticle(RequestReadNewsArticleEvent requestReadNewsArticleEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public UpdatedEvent updateNewsArticle(UpdateNewsArticleEvent updateNewsArticleEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public DeletedEvent deleteNewsArticle(DeleteNewsArticleEvent deleteNewsArticleEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public NewsArticlesReadEvent readNewsArticles(ReadNewsArticlesEvent readNewsArticlesEvent,Direction sortDirection, int i, int j);
	@PreAuthorize("hasRole('ROLE_USER')")
	public NewsArticleLikedEvent likeNewsArticle(LikeEvent likeNewsArticlesEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public NewsArticleUnlikedEvent unlikeNewsArticle(LikeEvent unlikeNewsArticleEvent);
    @PreAuthorize("hasRole('ROLE_USER')")
    public NewsArticleLikesEvent likesNewsArticle(LikesNewsArticleEvent likesNewsArticleEvent, Direction sortDirection, int pageNumber, int pageSize);
}
