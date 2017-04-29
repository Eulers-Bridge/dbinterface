package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class ReadTicketEventTest {
    Long ticketId = new Long(11);
    String name = "ticket name";
    String logo = "logo";
    Set<PhotoDetails> pictures;
    String information = "informations";
    ArrayList<String> candidateNames;

    TicketDetails ticketDetails;
    ReadTicketEvent readTicketEvent;

    @Before
    public void setUp() throws Exception {
        pictures = new HashSet<PhotoDetails>();
        pictures.add(DatabaseDataFixture.populatePhoto1().toPhotoDetails());
        candidateNames = new ArrayList<>();
        candidateNames.add("Yikai");
        candidateNames.add("Greg");

        ticketDetails = new TicketDetails();
        ticketDetails.setNodeId(ticketId);
        ticketDetails.setName(name);
        ticketDetails.setLogo(logo);
        ticketDetails.setPhotos(pictures);
        ticketDetails.setInformation(information);
        ticketDetails.setCandidateNames(candidateNames);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception{
        readTicketEvent = new ReadTicketEvent(ticketId, ticketDetails);
        assertNotNull("constructor returns null", readTicketEvent);
    }
}