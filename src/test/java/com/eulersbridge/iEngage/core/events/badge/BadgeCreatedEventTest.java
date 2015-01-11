package com.eulersbridge.iEngage.core.events.badge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class BadgeCreatedEventTest {
    BadgeCreatedEvent badgeCreatedEvent = null;
    BadgeDetails badgeDetails;
    Long badgeId = new Long(0);
    String name = "badgename";
    boolean awarded = false;
    Long timestamp = new Long(0);
    Long xpValue = new Long(10);

    @Before
    public void setUp() throws Exception {
        badgeDetails = new BadgeDetails();
        badgeDetails.setNodeId(badgeId);
        badgeDetails.setName(name);
        badgeDetails.setAwarded(awarded);
        badgeDetails.setTimestamp(timestamp);
        badgeDetails.setXpValue(xpValue);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        badgeCreatedEvent = new BadgeCreatedEvent(badgeId);
        assertNotNull("constructor returns null");
        badgeCreatedEvent = new BadgeCreatedEvent(badgeId, badgeDetails);
        assertNotNull("constructor returns null");
    }

    @Test
    public void testGetBadgeId() throws Exception {
        badgeCreatedEvent = new BadgeCreatedEvent(badgeId);
        assertEquals("id does not match", badgeId, badgeCreatedEvent.getBadgeId());
    }

    @Test
    public void testSetBadgeId() throws Exception {
        badgeCreatedEvent = new BadgeCreatedEvent(badgeId);
        badgeCreatedEvent.setBadgeId(new Long(4));
        assertEquals("id does not match", new Long(4), badgeCreatedEvent.getBadgeId());
    }
}