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

public class NewsArticleUpdatedEventTest {
    final Long newsArticleId = new Long(0);
    final String title = new String("title");
    final String content = new String("content");
    final Set<String> picture = new HashSet<>();
    final Set<String> likers = new HashSet<>();
    final Long date = new Long(0);
    final String creatorEmail = new String("yikaig@gmail.com");
    final Long institutionId = new Long(1);
    NewsArticleDetails newsArticleDetails = null;
    NewsArticleUpdatedEvent newsArticleUpdatedEvent = null;

    @Before
    public void setUp() throws Exception {
        newsArticleDetails = new NewsArticleDetails();
        newsArticleDetails.setInstitutionId(institutionId);
        newsArticleDetails.setContent(content);
        newsArticleDetails.setCreatorEmail(creatorEmail);
        newsArticleDetails.setDate(date);
        newsArticleDetails.setNewsArticleId(newsArticleId);
        newsArticleDetails.setTitle(title);
        newsArticleDetails.setPicture(picture);
        newsArticleDetails.setLikers(likers);
        newsArticleUpdatedEvent = new NewsArticleUpdatedEvent(newsArticleId, newsArticleDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewsArticleUpdatedEvent() throws Exception {
        assertNotNull("newsArticleUpdatedEvent is null", newsArticleUpdatedEvent);
        NewsArticleUpdatedEvent newsArticleUpdatedEvent1 = new NewsArticleUpdatedEvent(newsArticleId);
        assertNotNull("newsArticleUpdatedEvent is null", newsArticleUpdatedEvent1);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("articleId does not match", newsArticleId, newsArticleUpdatedEvent.getNewsArticleId());
    }

    @Test
    public void testGetNewsArticleDetails() throws Exception {
        assertEquals("newsArticleDetails does not match", newsArticleDetails, newsArticleUpdatedEvent.getNewsArticleDetails());
    }
}