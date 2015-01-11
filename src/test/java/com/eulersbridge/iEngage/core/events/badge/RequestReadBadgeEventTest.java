package com.eulersbridge.iEngage.core.events.badge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class RequestReadBadgeEventTest {
    RequestReadBadgeEvent requestReadBadgeEvent = null;
    Long badgeId = new Long(0);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        requestReadBadgeEvent = new RequestReadBadgeEvent(badgeId);
        assertNotNull("constructor returns null", requestReadBadgeEvent);
    }
}