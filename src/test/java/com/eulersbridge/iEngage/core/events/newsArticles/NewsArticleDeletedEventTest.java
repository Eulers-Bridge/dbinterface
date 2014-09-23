package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class NewsArticleDeletedEventTest {
    final Long newsArticleId = new Long(0);
    final String title = new String("title");
    final String content = new String("content");
    final Set<String> picture = new HashSet<>();
    final Set<String> likers = new HashSet<>();
    final Long date = new Long(0);
    final String creatorEmail = new String("yikaig@gmail.com");
    final String studentYear = new String("2014");
    final Long institutionId = new Long(1);
    NewsArticleDetails newsArticleDetails = null;
    NewsArticleDeletedEvent newsArticleDeletedEvent = null;

    @Before
    public void setUp() throws Exception {
        newsArticleDeletedEvent = new NewsArticleDeletedEvent(newsArticleId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewsArticleDeletedEvent() throws Exception {
        assertNotNull("NewsArticleDeletedEvent is null", newsArticleDeletedEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("newsArticleId does not match", newsArticleId, newsArticleDeletedEvent.getNewsArticleId());
    }

    @Test
    public void testIsDeletionCompleted() throws Exception {
        assertTrue("deletionCompleted is not true", newsArticleDeletedEvent.isDeletionCompleted());
    }

    @Test
    public void testDeletionForbidden() throws Exception {
        NewsArticleDeletedEvent newsArticleDeletedEvent1 = NewsArticleDeletedEvent.deletionForbidden(newsArticleId);
        assertNotNull("newsArticleDeletedEvent is null", newsArticleDeletedEvent1);
        assertFalse("deletionCompleted is not false", newsArticleDeletedEvent1.isDeletionCompleted());
    }

    @Test
    public void testNotFound() throws Exception {
        NewsArticleDeletedEvent newsArticleDeletedEvent1 = NewsArticleDeletedEvent.notFound(newsArticleId);
        assertNotNull("NewsArticleDeletedEvent is null", newsArticleDeletedEvent1);
        assertFalse("entityFound is not false", newsArticleDeletedEvent1.isEntityFound());
        assertFalse("deletion completed is true",newsArticleDeletedEvent1.isDeletionCompleted());
    }
}