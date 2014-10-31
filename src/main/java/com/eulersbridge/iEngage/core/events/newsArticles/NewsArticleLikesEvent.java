package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.LikesEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;

import java.util.Collection;

/**
 * @author Yikai Gong
 */

public class NewsArticleLikesEvent extends LikesEvent {
    private Long newsArticleId;

    private boolean articleFound = true;

    private Collection<UserDetails> userDetails;

    public NewsArticleLikesEvent() {
        super();
    }

    public NewsArticleLikesEvent(Long newsArticleId, Collection<UserDetails> userDetails) {
        this.newsArticleId = newsArticleId;
        this.userDetails = userDetails;
    }

    public static NewsArticleLikesEvent articleNotFound(Long id){
        NewsArticleLikesEvent newsArticleLikesEvent = new NewsArticleLikesEvent();
        newsArticleLikesEvent.setArticlesFound(false);
        newsArticleLikesEvent.setNewsArticleId(id);

        return newsArticleLikesEvent;
    }

    public Long getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(Long newsArticleId) {
        this.newsArticleId = newsArticleId;
    }

    public Collection<UserDetails> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Collection<UserDetails> userDetails) {
        this.userDetails = userDetails;
    }

    public boolean isArticlesFound() {
        return articleFound;
    }

    public void setArticlesFound(boolean articlesFound) {
        this.articleFound = articlesFound;
    }
}
