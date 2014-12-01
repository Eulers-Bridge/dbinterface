package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.HashSet;
import java.util.Iterator;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Ticket {
    @GraphId
    private Long ticketId;
    private String name;
    private String logo;
    private Iterable<String> pictures;
    private String information;
    private String candidatesEmails;
    private String candidatesNames;

    private static Logger LOG = LoggerFactory.getLogger(Ticket.class);

    public static Ticket fromTicketDetails(TicketDetails ticketDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromTicketDetails()");
        Ticket ticket = new Ticket();
        if (LOG.isTraceEnabled()) LOG.trace("ticketDetails "+ticketDetails);
        ticket.setTicketId(ticketDetails.getNodeId());
        ticket.setName(ticketDetails.getName());
        ticket.setLogo(ticketDetails.getLogo());
        ticket.setPictures(ticketDetails.getPictures());
        ticket.setInformation(ticketDetails.getInformation());
        ticket.setCandidatesEmails(ticketDetails.getCandidatesEmails());
        ticket.setCandidatesNames(ticketDetails.getCandidatesNames());

        if (LOG.isTraceEnabled()) LOG.trace("ticket "+ticket);
        return ticket;
    }

    public TicketDetails toTicketDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toTicketDetails()");
        TicketDetails ticketDetails = new TicketDetails();
        if (LOG.isTraceEnabled()) LOG.trace("ticket "+this);
        ticketDetails.setNodeId(getTicketId());
        ticketDetails.setName(getName());
        ticketDetails.setLogo(getLogo());
        ticketDetails.setInformation(getInformation());
        ticketDetails.setCandidatesEmails(getCandidatesEmails());
        ticketDetails.setCandidatesNames(getCandidatesNames());


        HashSet<String> pictures = new HashSet<String>();
        if (getPictures()!=null)
        {
            Iterator<String> iter = getPictures().iterator();
            while(iter.hasNext())
            {
                String url = iter.next();
                pictures.add(url);
            }
        }
        ticketDetails.setPictures(pictures);
        if (LOG.isTraceEnabled()) LOG.trace("ticketDetails; "+ ticketDetails);
        return ticketDetails;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getTicketId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", logo = ");
        buff.append(getLogo());
        buff.append(", pictures = ");
        buff.append(getPictures());
        buff.append(", information = ");
        buff.append(getInformation());
        buff.append(", candidatesEmails = ");
        buff.append(getCandidatesEmails());
        buff.append(", givenName = ");
        buff.append(getCandidatesNames());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Ticket() {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Iterable<String> getPictures() {
        return pictures;
    }

    public void setPictures(Iterable<String> pictures) {
        this.pictures = pictures;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCandidatesEmails() {
        return candidatesEmails;
    }

    public void setCandidatesEmails(String candidatesEmails) {
        this.candidatesEmails = candidatesEmails;
    }

    public String getCandidatesNames() {
        return candidatesNames;
    }

    public void setCandidatesNames(String candidatesNames) {
        this.candidatesNames = candidatesNames;
    }
}
