package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class NewsArticleUnlikedEventTest {
    final Long articleId = new Long(1);
    final String userEmail = new String("yikaig@gmail.com");
    final boolean result = true;
    NewsArticleUnlikedEvent newsArticleUnlikedEvent = null;

    @Before
    public void setUp() throws Exception {
        newsArticleUnlikedEvent = new NewsArticleUnlikedEvent(articleId, userEmail, result);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewsArticleUnlikedEvent() throws Exception {
        assertNotNull("NewsArticleUnlikedEvent is null", newsArticleUnlikedEvent);
        NewsArticleUnlikedEvent newsArticleUnlikedEvent1 = new NewsArticleUnlikedEvent(articleId, userEmail);
        NewsArticleUnlikedEvent newsArticleUnlikedEvent2 = new NewsArticleUnlikedEvent(userEmail);
        assertNotNull("NewsArticleUnlikedEvent is null", newsArticleUnlikedEvent1);
        assertNotNull("NewsArticleUnlikedEvent is null", newsArticleUnlikedEvent2);
    }

    @Test
    public void testArticleNotFound() throws Exception {
        NewsArticleUnlikedEvent newsArticleUnlikedEvent1 = NewsArticleUnlikedEvent.articleNotFound(articleId, userEmail);
        assertNotNull("articleNotFound() returns null", newsArticleUnlikedEvent1);
        assertFalse("entityFound is not false", newsArticleUnlikedEvent1.isEntityFound());
        assertFalse("result is not false", newsArticleUnlikedEvent1.isResultSuccess());
    }

    @Test
    public void testUserNotFound() throws Exception {
        NewsArticleUnlikedEvent newsArticleUnlikedEvent1 = NewsArticleUnlikedEvent.userNotFound(articleId, userEmail);
        assertNotNull("userNotFound() returns null", newsArticleUnlikedEvent1);
        assertFalse("userFound is not false", newsArticleUnlikedEvent1.isUserFound());
        assertFalse("result is not false", newsArticleUnlikedEvent1.isResultSuccess());
    }
}