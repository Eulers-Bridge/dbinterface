package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class NewsArticleLikesEventTest {
    private Long newsArticleId = new Long(0);
    private Collection<UserDetails> userDetails = new ArrayList<UserDetails>();
    private NewsArticleLikesEvent newsArticleLikesEvent;

    @Before
    public void setUp() throws Exception {
        newsArticleLikesEvent = new NewsArticleLikesEvent(newsArticleId, userDetails);
        assertNotNull("newsArticleLikesEventTest is null", newsArticleLikesEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testArticleNotFound() throws Exception {
        NewsArticleLikesEvent newsArticleLikesEvent1 = NewsArticleLikesEvent.articleNotFound(newsArticleId);
        assertNotNull("newsArticleLikesEventTest is null", newsArticleLikesEvent1);
        assertFalse("isArticlesFound is not false",newsArticleLikesEvent1.isArticlesFound());

    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("id does not match", newsArticleId, newsArticleLikesEvent.getNewsArticleId());
    }

    @Test
    public void testSetNewsArticleId() throws Exception {
        newsArticleLikesEvent.setNewsArticleId(new Long(2));
        assertEquals("id does not match", new Long(2), newsArticleLikesEvent.getNewsArticleId());
    }

    @Test
    public void testGetUserDetails() throws Exception {
        assertEquals("details does not match", userDetails, newsArticleLikesEvent.getUserDetails());
    }

    @Test
    public void testSetUserDetails() throws Exception {
        UserDetails userDetails = new UserDetails("yikaig@student.unielb.edu.au");
        Collection<UserDetails> userDetails1 = new ArrayList<UserDetails>();
        userDetails1.add(userDetails);
        newsArticleLikesEvent.setUserDetails(userDetails1);
        assertEquals("details does not match", userDetails1, newsArticleLikesEvent.getUserDetails());
    }

    @Test
    public void testIsArticlesFound() throws Exception {
        assertTrue("isArticlesFound is not true", newsArticleLikesEvent.isArticlesFound());
    }

    @Test
    public void testSetArticlesFound() throws Exception {
        newsArticleLikesEvent.setArticlesFound(false);
        assertFalse("isArticlesFound is not false", newsArticleLikesEvent.isArticlesFound());
    }
}