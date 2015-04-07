package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.rest.controller.TicketController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * @author Yikai Gong
 */

public class Ticket extends ResourceSupport{
    private Long ticketId=null;
    private String name=null;
    private String logo=null;
    private Set<String> pictures=null;
    private String information=null;
    private String colour=null;
    private Iterable<String> candidateNames=null;
    private Long electionId=null;
    private String code = null;
    private Long numberOfSupporters = null;

    private static Logger LOG = LoggerFactory.getLogger(Ticket.class);

    public Ticket() {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static Ticket fromTicketDetails(TicketDetails ticketDetails){
        Ticket ticket = new Ticket();
        String simpleName = Ticket.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        if (ticketDetails!=null)
        {	
	        ticket.setTicketId(ticketDetails.getNodeId());
	        ticket.setName(ticketDetails.getName());
	        ticket.setLogo(ticketDetails.getLogo());
	        ticket.setPictures(ticketDetails.getPictures());
	        ticket.setInformation(ticketDetails.getInformation());
	        ticket.setColour(ticketDetails.getColour());
	        ticket.setCandidateNames(ticketDetails.getCandidateNames());
	        ticket.setElectionId(ticketDetails.getElectionId());
	        ticket.setCode(ticketDetails.getChararcterCode());
            ticket.setNumberOfSupporters(ticketDetails.getNumberOfSupporters());
        }
        // {!begin selfRel}
        ticket.add(linkTo(TicketController.class).slash(name)
                .slash(ticket.ticketId).withSelfRel());
        // {!end selfRel}
        // {!begin readAll}
        ticket.add(linkTo(TicketController.class).slash(name + 's')
                .withRel(RestDomainConstants.READALL_LABEL));
        // {!end readAll}
        return ticket;
    }

    public TicketDetails toTicketDetails(){
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setNodeId(getTicketId());
        ticketDetails.setName(getName());
        ticketDetails.setLogo(getLogo());
        ticketDetails.setPictures(getPictures());
        ticketDetails.setInformation(getInformation());
        ticketDetails.setColour(getColour());
        ticketDetails.setElectionId(getElectionId());
        ticketDetails.setChararcterCode(getCode());
        return ticketDetails;
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

    public Set<String> getPictures() {
        return pictures;
    }

    public void setPictures(Set<String> pictures) {
        this.pictures = pictures;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Iterable<String> getCandidateNames() {
        return candidateNames;
    }

    public void setCandidateNames(Iterable<String> candidateNames) {
        this.candidateNames = candidateNames;
    }

    public Long getNumberOfSupporters() {
        return numberOfSupporters;
    }

    public void setNumberOfSupporters(Long numberOfSupporters) {
        this.numberOfSupporters = numberOfSupporters;
    }

    /**
	 * @return the colour
	 */
	public String getColour()
	{
		return colour;
	}

	/**
	 * @param colour the colour to set
	 */
	public void setColour(String colour)
	{
		this.colour = colour;
	}

	/**
	 * @return the electionId
	 */
	public Long getElectionId()
	{
		return electionId;
	}

	/**
	 * @param electionId the electionId to set
	 */
	public void setElectionId(Long electionId)
	{
		this.electionId = electionId;
	}

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString()
	{
		return "Ticket [ticketId=" + ticketId + ", name=" + name + ", logo="
				+ logo + ", pictures=" + pictures + ", information="
				+ information + ", candidateIds=" + candidateNames + ", electionId = "+electionId+"]";
	}

	public static Iterator<Ticket> toTicketsIterator(
			Iterator<? extends Details> iter)
	{
		if (null==iter) return null;
		ArrayList <Ticket> elections=new ArrayList<Ticket>();
		while(iter.hasNext())
		{
			TicketDetails dets=(TicketDetails)iter.next();
			Ticket thisTicket=Ticket.fromTicketDetails(dets);
			Link self = thisTicket.getLink("self");
			thisTicket.removeLinks();
			thisTicket.add(self);
			elections.add(thisTicket);		
		}
		return elections.iterator();
	}

}
