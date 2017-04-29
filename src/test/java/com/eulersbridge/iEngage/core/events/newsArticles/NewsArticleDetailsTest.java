package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class NewsArticleDetailsTest {
    final Long newsArticleId = new Long(0);
    final String title = new String("title");
    final String content = new String("content");
    final Iterable<PhotoDetails> picture = new HashSet<>();
    final Integer likes = 25;
    final Long date = new Long(0);
    final String creatorEmail = new String("yikaig@gmail.com");
    final Long institutionId = new Long(1);
    NewsArticleDetails newsArticleDetails = null;

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
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewsArticleDetails() throws Exception {
        NewsArticleDetails newsArticleDetails1 = new NewsArticleDetails();
        assertNotNull("NewsArticleDetails is null", newsArticleDetails1);
    }

    @Test
    public void testGetNewsArticleId() throws Exception {
        assertEquals("NewsArticleId does not match", newsArticleId, newsArticleDetails.getNewsArticleId());
    }

    @Test
    public void testSetNewsArticleId() throws Exception {
        newsArticleDetails.setNewsArticleId(new Long(2));
        assertEquals("NewsArticleId does not match", new Long(2), newsArticleDetails.getNewsArticleId());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("title does not match", title, newsArticleDetails.getTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        String newTitle = new String("newTitle");
        newsArticleDetails.setTitle(newTitle);
        assertEquals("title does not match", "newTitle", newsArticleDetails.getTitle());
    }

    @Test
    public void testGetContent() throws Exception {
        assertEquals("content does not match", content, newsArticleDetails.getContent());
    }

    @Test
    public void testSetContent() throws Exception {
        newsArticleDetails.setContent("newContent");
        assertEquals("content does not match", "newContent", newsArticleDetails.getContent());
    }

    @Test
    public void testGetPicture() throws Exception {
        assertEquals("picture does not match", picture, newsArticleDetails.getPhotos());
    }

    @Test
    public void testSetPicture() throws Exception {
        Set<PhotoDetails> newPicture = new HashSet<>();
        newPicture.add(DatabaseDataFixture.populatePhoto1().toPhotoDetails());
        newsArticleDetails.setPhotos(newPicture);
        assertEquals("picture does not match", newPicture, newsArticleDetails.getPhotos());
    }

    @Test
    public void testGetDate() throws Exception {
        assertEquals("date does not match", date, newsArticleDetails.getDate());
    }

    @Test
    public void testSetDate() throws Exception {
        newsArticleDetails.setDate(new Long(3));
        assertEquals("date does not match", new Long(3), newsArticleDetails.getDate());
    }

    @Test
    public void testSetDate1() throws Exception {
        Calendar date1 = Calendar.getInstance();
        NewsArticleDetails newsArticleDetails1 = new NewsArticleDetails();
        newsArticleDetails1.setDate(date1);
        newsArticleDetails.setDate(date1);
        assertEquals("date does not match", newsArticleDetails1.getDate(), newsArticleDetails.getDate());
    }

    @Test
    public void testGetCreatorEmail() throws Exception {
        assertEquals("CreatorEmail does not match", creatorEmail, newsArticleDetails.getCreatorEmail());
    }

    @Test
    public void testSetCreatorEmail() throws Exception {
        newsArticleDetails.setCreatorEmail("test@gmail.com");
        assertEquals("CreatorEmail does not match", "test@gmail.com", newsArticleDetails.getCreatorEmail());
    }

    @Test
    public void testGetLikers() throws Exception {
        assertEquals("likers does not match", likes, newsArticleDetails.getLikes());
    }

    @Test
    public void testSetLikers() throws Exception {
        Integer likes1 = 9;
        newsArticleDetails.setLikes(likes1);
        assertEquals("likers does not match", likes1, newsArticleDetails.getLikes());
    }

    @Test
    public void testGetInstitutionId() throws Exception {
        assertEquals("institutionId does not match", institutionId, newsArticleDetails.getInstitutionId());
    }

    @Test
    public void testSetInstitutionId() throws Exception {
        newsArticleDetails.setInstitutionId(new Long(3));
        assertEquals("institutionId does not match", new Long(3), newsArticleDetails.getInstitutionId());
    }

    @Test
    public void testToString() throws Exception {
        NewsArticleDetails newsArticleDetails1 = new NewsArticleDetails();
        newsArticleDetails1.setInstitutionId(institutionId);
        newsArticleDetails1.setContent(content);
        newsArticleDetails1.setCreatorEmail(creatorEmail);
        newsArticleDetails1.setDate(date);
        newsArticleDetails1.setNewsArticleId(newsArticleId);
        newsArticleDetails1.setTitle(title);
        newsArticleDetails1.setPhotos(picture);
        newsArticleDetails1.setLikes(likes);
        assertNotNull("toString() return null", newsArticleDetails.toString());
        assertEquals("toString() does not match", newsArticleDetails.toString(), newsArticleDetails1.toString());
    }
}