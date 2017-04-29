package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class LikeNewsArticleEventTest {
    final Long articleId = new Long(0);
    final String emailAddress = new String("yikaig@gmail.com");
    LikeNewsArticleEvent likeNewsArticleEvent = null;

    @Before
    public void setUp() throws Exception {
        likeNewsArticleEvent = new LikeNewsArticleEvent(articleId, emailAddress);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLikeNewsArticleEvent() throws Exception {
        assertNotNull("LikeNewsArticleEvent is null", likeNewsArticleEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("articleId does not match", articleId, likeNewsArticleEvent.getNewsArticleId());
    }

    @Test
    public void testSetNewsArticleId() throws Exception {
        likeNewsArticleEvent.setNewsArticleId(new Long(2));
        assertEquals("articleId does not match", new Long(2), likeNewsArticleEvent.getNewsArticleId());
    }
}