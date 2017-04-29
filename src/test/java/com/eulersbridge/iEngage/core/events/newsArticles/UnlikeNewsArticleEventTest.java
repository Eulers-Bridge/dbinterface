package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class UnlikeNewsArticleEventTest {
    final Long newsArticleId = new Long(1);
    final String userId = new String("yikaig@gmail,com");
    UnlikeNewsArticleEvent unlikeNewsArticleEvent = null;

    @Before
    public void setUp() throws Exception {
        unlikeNewsArticleEvent = new UnlikeNewsArticleEvent(newsArticleId, userId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUnlikeNewsArticleEvent() throws Exception {
        assertNotNull("unlikeNewsArticleEvent is null", unlikeNewsArticleEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("newsArticleId does not match", newsArticleId, unlikeNewsArticleEvent.getNewsArticleId());
    }

    @Test
    public void testSetNewsArticleId() throws Exception {
        Long articleId1 = new Long(2);
        unlikeNewsArticleEvent.setNewsArticleId(articleId1);
        assertEquals("newsArticleId does not match", articleId1, unlikeNewsArticleEvent.getNewsArticleId());
    }
}