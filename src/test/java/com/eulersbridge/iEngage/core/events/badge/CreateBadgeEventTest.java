package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class CreateBadgeEventTest
{
    BadgeDetails badgeDetails;
    Long badgeId;
    String name;
    String description;
    Long xpValue;
    CreateBadgeEvent createBadgeEvent = null;

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
        createBadgeEvent = new CreateBadgeEvent(badgeDetails);
        assertNotNull("constructor returns null", createBadgeEvent);
    }
}