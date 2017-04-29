package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class  BadgeDetailsTest
{
    BadgeDetails badgeDetails;
    Long badgeId;
    String name;
    String description;
    Long xpValue;

    @Before
    public void setUp() throws Exception
    {
        badgeDetails = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        badgeId=badgeDetails.getNodeId();
        name=badgeDetails.getName();
        description=badgeDetails.getDescription();
        xpValue=badgeDetails.getXpValue();
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("toString() returns null", badgeDetails.toString());
    }

    @Test
    public void testEquals() throws Exception {
        BadgeDetails badgeDetails1 = DatabaseDataFixture.populateBadge1().toBadgeDetails();
        assertEquals("badgeDetails does not match", badgeDetails, badgeDetails1);
        assertEquals("badgeDetails does not match", badgeDetails1, badgeDetails);
        badgeDetails1=DatabaseDataFixture.populateBadge2().toBadgeDetails();
        assertNotEquals("different badgeDetails matchs", badgeDetails, badgeDetails1);
        badgeDetails=DatabaseDataFixture.populateBadge2().toBadgeDetails();
        assertEquals(" badgeDetails do not match", badgeDetails, badgeDetails1);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("name does not match", name, badgeDetails.getName());
    }

    @Test
    public void testSetName() throws Exception {
        BadgeDetails badgeDetails1 = new BadgeDetails();
        badgeDetails1.setName(name);
        assertEquals("name does not match", name, badgeDetails1.getName());
    }

    @Test
    public void testGetDescription() throws Exception
    {
        assertEquals("timestamp does not match", description, badgeDetails.getDescription());
    }

    @Test
    public void testSetTimestamp() throws Exception {
    	description="Another description";
        assertNotEquals("description should not match but does.", description, badgeDetails.getDescription());
        badgeDetails.setDescription(description);
        assertEquals("description does not match", description, badgeDetails.getDescription());
    }

    @Test
    public void testGetXpValue() throws Exception {
        assertEquals("xpValue does not match", xpValue, badgeDetails.getXpValue());
    }

    @Test
    public void testSetXpValue() throws Exception {
        BadgeDetails badgeDetails1 = new BadgeDetails();
        badgeDetails1.setXpValue(new Long(90));
        assertEquals("xpValue does not match", new Long(90), badgeDetails1.getXpValue());
    }
}