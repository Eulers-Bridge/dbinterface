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

public class ReadNewsArticleEventTest {
    final Long newsArticleId = new Long(0);
    final String title = new String("title");
    final String content = new String("content");
    final Set<String> picture = new HashSet<>();
    final Set<String> likers = new HashSet<>();
    final Long date = new Long(0);
    final String creatorEmail = new String("yikaig@gmail.com");
    final Long institutionId = new Long(1);
    NewsArticleDetails newsArticleDetails = null;
    ReadNewsArticleEvent readNewsArticleEvent = null;

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
        readNewsArticleEvent = new ReadNewsArticleEvent(newsArticleId, newsArticleDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadNewsArticleEvent() throws Exception {
        assertNotNull("readNewsArticleEvent is null", readNewsArticleEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("newsArticleId does not match", newsArticleId, readNewsArticleEvent.getNewsArticleId());
    }

    @Test
    public void testGetReadNewsArticleDetails() throws Exception {
        assertEquals("newsArticleDetails does not match", newsArticleDetails, readNewsArticleEvent.getReadNewsArticleDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        ReadNewsArticleEvent readNewsArticleEvent1 = ReadNewsArticleEvent.notFound(newsArticleId);
        assertNotNull("notFound() returns null", readNewsArticleEvent1);
        assertFalse("entityFound is not false", readNewsArticleEvent1.isEntityFound());
    }
}