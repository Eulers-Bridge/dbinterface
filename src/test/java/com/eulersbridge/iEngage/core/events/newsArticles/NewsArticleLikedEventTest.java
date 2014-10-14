package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class NewsArticleLikedEventTest {
    final Long articleId = new Long(1);
    final String userEmail = new String("yikaig@gmail.com");
    NewsArticleLikedEvent newsArticleLikedEvent = null;

    @Before
    public void setUp() throws Exception {
        newsArticleLikedEvent = new NewsArticleLikedEvent(articleId, userEmail, true);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewsArticleLikedEvent() throws Exception {
        assertNotNull("newsArticleLikedEvent is null", newsArticleLikedEvent);
    }

    @Test
    public void testArticleNotFound() throws Exception {
        NewsArticleLikedEvent newsArticleLikedEvent1 = NewsArticleLikedEvent.articleNotFound(articleId, userEmail);
        assertNotNull("newsArticleLikedEvent is null", newsArticleLikedEvent1);
        assertFalse("entityFound is not false", newsArticleLikedEvent1.isEntityFound());
        assertFalse("result is not false", newsArticleLikedEvent1.isResultSuccess());
    }

    @Test
    public void testUserNotFound() throws Exception {
        NewsArticleLikedEvent newsArticleLikedEvent1 = NewsArticleLikedEvent.userNotFound(articleId, userEmail);
        assertNotNull("newsArticleLikedEvent is null", newsArticleLikedEvent1);
        assertFalse("userFound is not false", newsArticleLikedEvent1.isUserFound());
        assertFalse("result is not false", newsArticleLikedEvent1.isResultSuccess());
    }
}