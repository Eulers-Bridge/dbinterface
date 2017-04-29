package com.eulersbridge.iEngage.core.events.likes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class LikesLikeableObjectEventTest {
    private Long likeableObjId;
    private LikesLikeableObjectEvent likesLikeableObjectEvent;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception{
        likesLikeableObjectEvent = new LikesLikeableObjectEvent(likeableObjId);
        assertNotNull("likesLikeableObjectEvent is null", likesLikeableObjectEvent);
    }
}