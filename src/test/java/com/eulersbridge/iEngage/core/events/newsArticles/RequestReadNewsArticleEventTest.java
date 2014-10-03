package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class RequestReadNewsArticleEventTest {
    final Long newsArticleId = new Long(0);
    RequestReadNewsArticleEvent requestReadNewsArticleEvent = null;

    @Before
    public void setUp() throws Exception {
        requestReadNewsArticleEvent = new RequestReadNewsArticleEvent(newsArticleId);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestReadNewsArticleEvent() throws Exception {
        assertNotNull("requestReadNewsArticleEvent is null", requestReadNewsArticleEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("newsArticleId does not match", newsArticleId, requestReadNewsArticleEvent.getNewsArticleId());
    }
}