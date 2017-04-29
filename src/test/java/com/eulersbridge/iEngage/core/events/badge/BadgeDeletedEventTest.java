package com.eulersbridge.iEngage.core.events.badge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class BadgeDeletedEventTest {
    BadgeDeletedEvent badgeDeletedEvent = null;
    Long badgeId = new Long(1);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void TestBadgeDeletedEvent() throws Exception{
        badgeDeletedEvent = new BadgeDeletedEvent(badgeId);
        assertNotNull("badgeDeletedEvent is null", badgeDeletedEvent);
    }
}