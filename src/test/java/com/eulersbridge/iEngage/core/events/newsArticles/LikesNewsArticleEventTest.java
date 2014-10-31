package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class LikesNewsArticleEventTest {
    private Long newsArticleId = new Long(0);
    private LikesNewsArticleEvent likesNewsArticleEvent;

    @Before
    public void setUp() throws Exception {
        likesNewsArticleEvent = new LikesNewsArticleEvent(newsArticleId);
        assertNotNull("likesNewsArticleEvent is null", likesNewsArticleEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("id does not match", newsArticleId, likesNewsArticleEvent.getNewsArticleId());
    }
}