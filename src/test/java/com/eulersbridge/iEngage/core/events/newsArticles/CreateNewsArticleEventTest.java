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

public class CreateNewsArticleEventTest {
    final Long newsArticleId = new Long(0);
    final String title = new String("title");
    final String content = new String("content");
    final Set<String> picture = new HashSet<>();
    final Set<String> likers = new HashSet<>();
    final Long date = new Long(0);
    final String creatorEmail = new String("yikaig@gmail.com");
    final Long institutionId = new Long(1);
    NewsArticleDetails newsArticleDetails = null;
    CreateNewsArticleEvent createNewsArticleEvent = null;

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

        createNewsArticleEvent = new CreateNewsArticleEvent(newsArticleId, newsArticleDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateNewsArticleEvent() throws Exception {
        assertNotNull("createNewsArticleEvent is null", createNewsArticleEvent);
        CreateNewsArticleEvent createNewsArticleEvent1 = new CreateNewsArticleEvent(newsArticleDetails);
        assertNotNull("createNewsArticleEvent is null", createNewsArticleEvent1);
    }

    @Test
    public void testGetNewsArticleDetails() throws Exception {
        assertEquals("newsArticleDetails does not match", newsArticleDetails, createNewsArticleEvent.getNewsArticleDetails());
    }

    @Test
    public void testSetNewsArticleDetails() throws Exception {
        NewsArticleDetails newsArticleDetails1 = new NewsArticleDetails();
        newsArticleDetails1.setInstitutionId(institutionId);
        newsArticleDetails1.setContent(content);
        newsArticleDetails1.setCreatorEmail(creatorEmail);
        newsArticleDetails1.setDate(date);
        newsArticleDetails1.setNewsArticleId(newsArticleId);
        newsArticleDetails1.setTitle(title);
        newsArticleDetails1.setPicture(picture);
        newsArticleDetails1.setLikers(likers);
        CreateNewsArticleEvent createNewsArticleEvent1 = new CreateNewsArticleEvent(newsArticleDetails1);
        assertEquals("NewsArticleDetails does not match", newsArticleDetails1, createNewsArticleEvent1.getNewsArticleDetails());
        assertEquals("NewsArticleDetails does not match", createNewsArticleEvent.getNewsArticleDetails(), createNewsArticleEvent1.getNewsArticleDetails());
    }
}