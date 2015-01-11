package com.eulersbridge.iEngage.core.events.badge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class BadgeDetailsTest {
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
    public void testToString() throws Exception {
        assertNotNull("toString() returns null", badgeDetails.toString());
    }

    @Test
    public void testEquals() throws Exception {
        BadgeDetails badgeDetails1 = new BadgeDetails();
        badgeDetails1.setNodeId(badgeId);
        assertEquals("badgeDetails does not match", badgeDetails, badgeDetails1);
        badgeDetails1.setName(name);
        badgeDetails1.setAwarded(awarded);
        badgeDetails1.setTimestamp(timestamp);
        badgeDetails1.setXpValue(xpValue);
        assertEquals("badgeDetails does not match", badgeDetails, badgeDetails1);
        badgeDetails1 = new BadgeDetails();
        badgeDetails1.setName(name);
        badgeDetails1.setAwarded(awarded);
        badgeDetails1.setTimestamp(timestamp);
        badgeDetails1.setXpValue(xpValue);
        assertNotEquals("different badgeDetails matchs", badgeDetails, badgeDetails1);
        badgeDetails1 = new BadgeDetails();
        badgeDetails1.setNodeId(new Long(10));
        assertNotEquals("different badgeDetails matchs", badgeDetails, badgeDetails1);
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
    public void testIsAwarded() throws Exception {
        assertEquals("awarded does not match", awarded, badgeDetails.isAwarded());
    }

    @Test
    public void testSetAwarded() throws Exception {
        BadgeDetails badgeDetails1 = new BadgeDetails();
        badgeDetails1.setAwarded(true);
        assertEquals("awarded does not match", true, badgeDetails1.isAwarded());
    }

    @Test
    public void testGetTimestamp() throws Exception {
        assertEquals("timestamp does not match", timestamp, badgeDetails.getTimestamp());
    }

    @Test
    public void testSetTimestamp() throws Exception {
        BadgeDetails badgeDetails1 = new BadgeDetails();
        badgeDetails1.setTimestamp(new Long(100));
        assertEquals("timestamp does not match", new Long(100), badgeDetails1.getTimestamp());
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