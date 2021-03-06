package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadNewsArticleEventTest {
    final Long newsArticleId = new Long(0);
    final String title = new String("title");
    final String content = new String("content");
    final Iterable<PhotoDetails> picture = new HashSet<>();
    final Integer likes = 97;
    final Long date = new Long(0);
    final String creatorEmail = new String("yikaig@gmail.com");
    final Long institutionId = new Long(1);
    NewsArticleDetails newsArticleDetails = null;
    ReadNewsArticleEvent readNewsArticleEvent = null;

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
        readNewsArticleEvent = new ReadNewsArticleEvent(newsArticleId, newsArticleDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadNewsArticleEvent() throws Exception {
        assertNotNull("readNewsArticleEvent is null", readNewsArticleEvent);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("newsArticleId does not match", newsArticleId, readNewsArticleEvent.getNodeId());
    }

    @Test
    public void testGetReadNewsArticleDetails() throws Exception {
        assertEquals("newsArticleDetails does not match", newsArticleDetails, readNewsArticleEvent.getDetails());
    }

    @Test
    public void testNotFound() throws Exception {
        ReadEvent readNewsArticleEvent1 = ReadNewsArticleEvent.notFound(newsArticleId);
        assertNotNull("notFound() returns null", readNewsArticleEvent1);
        assertFalse("entityFound is not false", readNewsArticleEvent1.isEntityFound());
    }
}