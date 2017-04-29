package com.eulersbridge.iEngage.core.events.badge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class DeleteBadgeEventTest {
    DeleteBadgeEvent deleteBadgeEvent = null;
    Long badgeId = new Long(1);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        deleteBadgeEvent = new DeleteBadgeEvent(badgeId);
        assertNotNull("constructor returns null", deleteBadgeEvent);
    }
}