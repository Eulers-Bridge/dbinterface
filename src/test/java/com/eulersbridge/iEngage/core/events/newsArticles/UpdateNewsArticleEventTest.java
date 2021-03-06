package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class UpdateNewsArticleEventTest {
    final Long newsArticleId = new Long(0);
    final String title = new String("title");
    final String content = new String("content");
    final Iterable<PhotoDetails> picture = new HashSet<>();
    final Integer likes = 81;
    final Long date = new Long(0);
    final String creatorEmail = new String("yikaig@gmail.com");
    final Long institutionId = new Long(1);
    NewsArticleDetails newsArticleDetails = null;
    UpdateNewsArticleEvent updateNewsArticleEvent = null;

    @Before
    public void setUp() throws Exception {
        newsArticleDetails = new NewsArticleDetails();
        newsArticleDetails.setInstitutionId(institutionId);
        newsArticleDetails.setContent(content);
        newsArticleDetails.setCreatorEmail(creatorEmail);
        newsArticleDetails.setDate(date);
        newsArticleDetails.setNewsArticleId(newsArticleId);
        newsArticleDetails.setTitle(title);
        newsArticleDetails.setPhotos(picture);
        newsArticleDetails.setLikes(likes);
        updateNewsArticleEvent = new UpdateNewsArticleEvent(newsArticleId, newsArticleDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUpdateNewsArticleEvent() throws Exception {
        assertNotNull("updateNewsArticleEvent is null", updateNewsArticleEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("ArticleId does not match", newsArticleId, updateNewsArticleEvent.getNodeId());
    }

    @Test
    public void testGetUNewsArticleDetails() throws Exception {
        assertEquals("NewsArticleDetails does not match", newsArticleDetails, updateNewsArticleEvent.getDetails());
    }
}