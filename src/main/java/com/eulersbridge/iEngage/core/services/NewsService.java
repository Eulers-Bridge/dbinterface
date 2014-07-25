package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.newsArticles.CreateNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.DeleteNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleCreatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDeletedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleUpdatedEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.ReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.UpdateNewsArticleEvent;

public interface NewsService 
{
	public NewsArticleCreatedEvent createNewsArticle(CreateNewsArticleEvent createNewsArticleEvent);
	public ReadNewsArticleEvent requestReadUser(RequestReadNewsArticleEvent requestReadNewsArticleEvent);
	public NewsArticleUpdatedEvent updateUser(UpdateNewsArticleEvent updateNewsArticleEvent);
	public NewsArticleDeletedEvent deleteUser(DeleteNewsArticleEvent deleteNewsArticleEvent);

}
