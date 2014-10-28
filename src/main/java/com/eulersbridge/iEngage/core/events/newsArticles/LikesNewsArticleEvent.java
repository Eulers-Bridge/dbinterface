package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.LikesEvent;

/**
 * @author Yikai Gong
 */

public class LikesNewsArticleEvent extends LikesEvent {
    private Long newsArticleId;

    public LikesNewsArticleEvent(Long newsArticleId) {
        this.newsArticleId = newsArticleId;
    }

    public Long getNewsArticleId() {
        return newsArticleId;
    }
}
