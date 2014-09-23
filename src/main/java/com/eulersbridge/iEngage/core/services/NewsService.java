package com.eulersbridge.iEngage.core.services;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.LikeNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleLikedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUnlikedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UnlikeNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;

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
	public NewsArticleLikedEvent likeNewsArticle(LikeNewsArticleEvent likeNewsArticlesEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public NewsArticleUnlikedEvent unlikeNewsArticle(UnlikeNewsArticleEvent unlikeNewsArticleEvent); 
}
