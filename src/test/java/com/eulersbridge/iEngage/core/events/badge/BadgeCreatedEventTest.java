package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class BadgeCreatedEventTest {
    BadgeCreatedEvent badgeCreatedEvent = null;
    BadgeDetails badgeDetails;
    Long badgeId;
    String name;
    String description;
    Long xpValue;

    @Before
    public void setUp() throws Exception {
        badgeDetails = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        badgeId=badgeDetails.getNodeId();
        name=badgeDetails.getName();
        description=badgeDetails.getDescription();
        xpValue=badgeDetails.getXpValue();
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