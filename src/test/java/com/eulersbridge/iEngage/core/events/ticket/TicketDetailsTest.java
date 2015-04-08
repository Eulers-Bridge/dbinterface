package com.eulersbridge.iEngage.core.events.ticket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class TicketDetailsTest
{
    Long ticketId = new Long(11);
    String name = "ticket name";
    String logo = "logo";
    Set<PhotoDetails> pictures = new HashSet<PhotoDetails>();
    String information = "informations";
    ArrayList<Long> candidateIds;

    TicketDetails ticketDetails;

    @Before
    public void setUp() throws Exception {
        pictures.add(DatabaseDataFixture.populatePhoto1().toPhotoDetails());
        candidateIds = new ArrayList<>();
        candidateIds.add(new Long(0));
        candidateIds.add(new Long(1));

        ticketDetails = new TicketDetails();
        ticketDetails.setNodeId(ticketId);
        ticketDetails.setName(name);
        ticketDetails.setLogo(logo);
        ticketDetails.setPhotos(pictures);
        ticketDetails.setInformation(information);
//        ticketDetails.setCandidateIds(candidateIds);

        assertNotNull("ticketDetails is null", ticketDetails);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("toString() returns null", ticketDetails.toString());
    }

    @Test
    public void testEquals() throws Exception {
        TicketDetails ticketDetails1 = new TicketDetails();
        ticketDetails1.setNodeId(new Long(11));

        assertEquals("ticketDetails does not match", ticketDetails1, ticketDetails);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("name does not match", name, ticketDetails.getName());
    }

    @Test
    public void testSetName() throws Exception {
        TicketDetails ticketDetails1 = new TicketDetails();
        ticketDetails1.setName(name);
        assertEquals("name does not match", name, ticketDetails1.getName());
    }

    @Test
    public void testGetLogo() throws Exception {
        assertEquals("logo does not match", logo, ticketDetails.getLogo());
    }

    @Test
    public void testSetLogo() throws Exception {
        TicketDetails ticketDetails1 = new TicketDetails();
        ticketDetails1.setLogo(logo);
        assertEquals("logo does not match", logo, ticketDetails1.getLogo());
    }

    @Test
    public void testGetPictures() throws Exception {
        assertEquals("pics does not match", pictures, ticketDetails.getPhotos());
    }

    @Test
    public void testSetPictures() throws Exception {
        TicketDetails ticketDetails1 = new TicketDetails();
        ticketDetails1.setPhotos(pictures);
        assertEquals("pics does not match", pictures, ticketDetails1.getPhotos());
    }

    @Test
    public void testGetInformation() throws Exception {
        assertEquals("information does not match", information, ticketDetails.getInformation());
    }

    @Test
    public void testSetInformation() throws Exception {
        TicketDetails ticketDetails1 = new TicketDetails();
        ticketDetails1.setInformation(information);
        assertEquals("information does not match", information, ticketDetails1.getInformation());
    }

//    @Test
//    public void testGetCandidateIds() throws Exception {
//        assertEquals("CandidateIds does not match", candidateIds, ticketDetails.getCandidateIds());
//    }
//
//    @Test
//    public void testSetCandidateIds() throws Exception {
//        TicketDetails ticketDetails1 = new TicketDetails();
//        ticketDetails1.setCandidateIds(candidateIds);
//        assertEquals("CandidateIds does not match", candidateIds, ticketDetails1.getCandidateIds());
//    }
}