package com.eulersbridge.iEngage.core.services;

import org.springframework.data.domain.Sort.Direction;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticlesReadEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticlesEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;

public interface NewsService 
{
	public NewsArticleCreatedEvent createNewsArticle(CreateNewsArticleEvent createNewsArticleEvent);
	public ReadNewsArticleEvent requestReadNewsArticle(RequestReadNewsArticleEvent requestReadNewsArticleEvent);
	public NewsArticleUpdatedEvent updateNewsArticle(UpdateNewsArticleEvent updateNewsArticleEvent);
	public NewsArticleDeletedEvent deleteNewsArticle(DeleteNewsArticleEvent deleteNewsArticleEvent);
//	public NewsArticlesReadEvent readNewsArticles(ReadNewsArticlesEvent readNewsArticlesEvent);
	public NewsArticlesReadEvent readNewsArticles(ReadNewsArticlesEvent readNewsArticlesEvent,Direction sortDirection, int i, int j);
}
