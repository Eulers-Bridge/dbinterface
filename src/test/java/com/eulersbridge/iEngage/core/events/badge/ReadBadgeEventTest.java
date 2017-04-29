package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class ReadBadgeEventTest {
    ReadBadgeEvent readBadgeEvent = null;
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

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        readBadgeEvent = new ReadBadgeEvent(badgeId);
        assertNotNull("constructor returns null", readBadgeEvent);
        readBadgeEvent = new ReadBadgeEvent(badgeId, badgeDetails);
        assertNotNull("constructor returns null", readBadgeEvent);
    }
}