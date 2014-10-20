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

public class NewsArticleCreatedEventTest {
    final Long newsArticleId = new Long(0);
    final String title = new String("title");
    final String content = new String("content");
    final Set<String> picture = new HashSet<>();
    final Integer likes = 15;
    final Long date = new Long(0);
    final String creatorEmail = new String("yikaig@gmail.com");
    final String studentYear = new String("2014");
    final Long institutionId = new Long(1);
    NewsArticleDetails newsArticleDetails = null;
    NewsArticleCreatedEvent newsArticleCreatedEvent = null;

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
        newsArticleDetails.setLikes(likes);
        newsArticleCreatedEvent = new NewsArticleCreatedEvent(newsArticleId, newsArticleDetails);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewsArticleCreatedEvent() throws Exception {
        assertNotNull("NewsArticleCreatedEvent is null", newsArticleCreatedEvent);
        NewsArticleCreatedEvent newsArticleCreatedEvent1 = new NewsArticleCreatedEvent(new Long(2));
        assertNotNull("NewsArticleCreatedEvent is null", newsArticleCreatedEvent1);

    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("NewsArticleId does not match", newsArticleId, newsArticleCreatedEvent.getNewsArticleId());
    }

    @Test
    public void testSetKey() throws Exception {
        newsArticleCreatedEvent.setKey(new Long(2));
        assertEquals("id does not match", new Long(2), newsArticleCreatedEvent.getNewsArticleId());
    }

    @Test
    public void testSetNewsArticleDetails() throws Exception {
        NewsArticleDetails newsArticleDetails1 = new NewsArticleDetails();
        newsArticleDetails1.setNewsArticleId(new Long(2));
        newsArticleCreatedEvent.setNewsArticleDetails(newsArticleDetails1);
        assertEquals("newsArticleDetails does not match", newsArticleDetails1, newsArticleCreatedEvent.getNewsArticleDetails());
    }

    @Test
    public void testGetNewsArticleDetails() throws Exception {
        assertEquals("newsArticleDetails does not match", newsArticleDetails, newsArticleCreatedEvent.getNewsArticleDetails());
    }

    @Test
    public void testIsCreatorFound() throws Exception {
        assertTrue("isCreatorFound is not true", newsArticleCreatedEvent.isCreatorFound());
    }

    @Test
    public void testSetCreatorFound() throws Exception {
        newsArticleCreatedEvent.setCreatorFound(false);
        assertFalse("isCreatorFound is not false", newsArticleCreatedEvent.isCreatorFound());
    }

    @Test
    public void testIsInstitutionFound() throws Exception {
        assertTrue("institutionFound is not true", newsArticleCreatedEvent.isInstitutionFound());
    }

    @Test
    public void testSetInstitutionFound() throws Exception {
        newsArticleCreatedEvent.setInstitutionFound(false);
        assertFalse("institutionFound is not false", newsArticleCreatedEvent.isInstitutionFound());
    }

    @Test
    public void testCreatorNotFound() throws Exception {
        NewsArticleCreatedEvent newsArticleCreatedEvent1 = NewsArticleCreatedEvent.creatorNotFound();
        assertNotNull("creatorNotFound() returns null", newsArticleCreatedEvent1);
        assertFalse("isCreatorFound is not false", newsArticleCreatedEvent1.isCreatorFound());
    }

    @Test
    public void testInstitutionNotFound() throws Exception {
        NewsArticleCreatedEvent newsArticleCreatedEvent1 = NewsArticleCreatedEvent.institutionNotFound();
        assertNotNull("institutionNotFound() returns null", newsArticleCreatedEvent1);
        assertFalse("institutionFound is not false", newsArticleCreatedEvent1.isInstitutionFound());
    }
}