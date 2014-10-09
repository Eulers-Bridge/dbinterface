package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class NewsArticlesReadEventTest {
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
    ArrayList<NewsArticleDetails> articles = null;
    NewsArticlesReadEvent newsArticlesReadEvent = null;
    final Long syId = new Long(1);

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
        articles = new ArrayList<>();
        articles.add(newsArticleDetails);
        newsArticlesReadEvent = new NewsArticlesReadEvent(institutionId, articles);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewsArticlesReadEvent() throws Exception {
        assertNotNull("newsArticlesReadEvent is null", newsArticlesReadEvent);
    }

    @Test
    public void testGetInstId() throws Exception {
        assertEquals("InstId does not match", institutionId, newsArticlesReadEvent.getInstId());
    }

    @Test
    public void testSetInstId() throws Exception {
        Long instId1 = new Long(2);
        newsArticlesReadEvent.setInstId(instId1);
        assertEquals("InstId does not match", instId1, newsArticlesReadEvent.getInstId());
    }

}