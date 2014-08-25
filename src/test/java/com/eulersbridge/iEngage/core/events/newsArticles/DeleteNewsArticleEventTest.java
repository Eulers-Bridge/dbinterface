package com.eulersbridge.iEngage.core.events.newsArticles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class DeleteNewsArticleEventTest {
    final Long id = new Long(0);
    DeleteNewsArticleEvent deleteNewsArticleEvent = null;


    @Before
    public void setUp() throws Exception {
        deleteNewsArticleEvent = new DeleteNewsArticleEvent(id);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("id does not match", id, deleteNewsArticleEvent.getNewsArticleId());
    }
}