package com.eulersbridge.iEngage.core.events.likes;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.database.domain.Like;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class LikeableObjectLikesEventTest {
    private Long likeableObjId = new Long(100);
    private Collection<UserDetails> userDetails = new ArrayList<>();
    private LikeableObjectLikesEvent likeableObjectLikesEvent;

    @Before
    public void setUp() throws Exception {
        UserDetails userDetails1 = new UserDetails("yikaig@test.com");
        UserDetails userDetails2 = new UserDetails("yikaig2@test.com");
        UserDetails userDetails3 = new UserDetails("yikaig3@test.com");
        userDetails.add(userDetails1);
        userDetails.add(userDetails2);
        userDetails.add(userDetails3);

        likeableObjectLikesEvent = new LikeableObjectLikesEvent(likeableObjId, userDetails);
        assertNotNull("likeableObjectLikesEvent is null", likeableObjectLikesEvent);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testObjectNotFound() throws Exception {
        LikeableObjectLikesEvent likeableObjectLikesEvent1 = LikeableObjectLikesEvent.objectNotFound(likeableObjId);
        assertNotNull("likeableObjectLikesEvent1 is null", likeableObjectLikesEvent1);
        assertFalse("objectFound is not false", likeableObjectLikesEvent1.isObjectFound());
    }

    @Test
    public void testGetLikeableObjId() throws Exception {
        assertEquals("id does not match", likeableObjId, likeableObjectLikesEvent.getLikeableObjId());
    }

    @Test
    public void testSetLikeableObjId() throws Exception {
        Long newId = new Long("200");
        likeableObjectLikesEvent.setLikeableObjId(newId);
        assertEquals("id does not match", newId, likeableObjectLikesEvent.getLikeableObjId());
    }

    @Test
    public void testIsObjectFound() throws Exception {
        assertTrue("objectFound is not true", likeableObjectLikesEvent.isObjectFound());
    }

    @Test
    public void testSetObjectFound() throws Exception {
        likeableObjectLikesEvent.setObjectFound(false);
        assertFalse("objectFound does not match", likeableObjectLikesEvent.isObjectFound());
    }

    @Test
    public void testGetUserDetails() throws Exception {
        assertEquals("userDetails does not match", userDetails, likeableObjectLikesEvent.getUserDetails());
    }

    @Test
    public void testSetUserDetails() throws Exception {
        Collection<UserDetails> userDetails1 = new ArrayList<>();
        likeableObjectLikesEvent.setUserDetails(userDetails1);
        assertEquals("userDetails does not match", userDetails1, likeableObjectLikesEvent.getUserDetails());
    }
}