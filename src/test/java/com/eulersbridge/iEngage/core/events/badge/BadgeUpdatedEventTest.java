package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class BadgeUpdatedEventTest {
    BadgeUpdatedEvent badgeUpdatedEvent = null;
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
        badgeUpdatedEvent = new BadgeUpdatedEvent(badgeId, badgeDetails);
        assertNotNull("constructor returns null", badgeUpdatedEvent);
        badgeUpdatedEvent = new BadgeUpdatedEvent(badgeId);
        assertNotNull("constructor returns null", badgeUpdatedEvent);
    }
}